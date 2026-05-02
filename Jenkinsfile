// Jenkins 声明式流水线（Declarative Pipeline）入口
// 整体目标：
// 1) 拉代码
// 2) Maven 打包（可选是否执行测试）
// 3) 可选构建 Docker 镜像
// 4) 可选推送镜像到仓库
// 5) 无论成功失败都归档构建产物与测试报告
pipeline {
  // agent any：表示这条流水线可在任意可用 Jenkins 节点执行
  // 前提：该节点需要具备项目所需环境（至少 Java + Maven Wrapper 可运行；构建镜像时还要有 Docker）
  agent any

  // options：流水线级别行为配置
  options {
    // 在控制台日志前加时间戳，方便排查某一步耗时和问题发生时刻
    timestamps()
    // 禁止同一个 Job 并发构建，避免多个构建同时操作同一 workspace 导致冲突
    disableConcurrentBuilds()
    // 构建历史保留策略：最多保留最近 20 次记录，避免 Jenkins 磁盘持续膨胀
    buildDiscarder(logRotator(numToKeepStr: '20'))
  }

  // parameters：构建参数（在 Jenkins UI 触发构建时可手动选择/填写）
  // 设计目的：同一份流水线支持“只打包”或“打包+镜像+推送”等不同流程
  parameters {
    // 是否执行单元测试
    // false：加快构建速度（当前默认）
    // true：提升质量门禁，构建失败可更早暴露问题
    booleanParam(name: 'RUN_TESTS', defaultValue: false, description: 'Run unit tests')
    // 是否执行 Docker 镜像构建
    // 若关闭，本次流水线仅进行 Maven 打包，不进入 Docker 阶段
    booleanParam(name: 'BUILD_DOCKER_IMAGE', defaultValue: true, description: 'Build Docker image')
    // 是否推送镜像到远端仓库
    // 仅在 BUILD_DOCKER_IMAGE=true 且本参数=true 时才会执行 push
    booleanParam(name: 'PUSH_DOCKER_IMAGE', defaultValue: false, description: 'Push Docker image')
    // 镜像仓库名（不带 tag）
    // 例如：carleasing-server
    string(name: 'DOCKER_IMAGE', defaultValue: 'carleasing-server', description: 'Image name without tag')
    // 镜像仓库前缀
    // 例如：docker.io/yourname 或 harbor.company.com/project
    // 留空时仅做本地镜像构建，不可推送
    string(name: 'DOCKER_REGISTRY', defaultValue: '', description: 'Registry prefix, for example docker.io/yourname. Leave empty for local build only')
    // Jenkins 凭据中心里 Docker 用户名/密码类型凭据的 ID
    // 推送镜像时用于 docker login
    string(name: 'DOCKER_CREDENTIALS_ID', defaultValue: 'dockerhub-credentials', description: 'Jenkins Docker credentials ID (username/password)')
  }

  // environment：流水线级环境变量
  // 这里把 Maven Wrapper 命令抽成变量，后续调用更统一
  environment {
    MVNW = './mvnw'
  }

  // stages：主执行阶段
  stages {
    // 第1阶段：拉取代码
    stage('Checkout') {
      steps {
        // checkout scm：从当前 Job 绑定的代码仓库和分支拉取源码
        checkout scm
      }
    }

    // 第2阶段：Maven 打包
    // 输出：target 目录下的 jar（以及测试报告，若执行了测试）
    stage('Build Package') {
      steps {
        // 确保 Maven Wrapper 可执行（在 Linux 节点常见需要）
        sh 'chmod +x ./mvnw'
        script {
          // 根据参数决定是否执行测试
          if (params.RUN_TESTS) {
            // 带测试打包：更严格，速度相对慢
            sh "${env.MVNW} -B clean package"
          } else {
            // 跳过测试打包：更快，适合快速验证打包和镜像流程
            sh "${env.MVNW} -B clean package -DskipTests"
          }
        }
      }
    }

    // 第3阶段：Docker 构建镜像
    // 仅当 BUILD_DOCKER_IMAGE=true 时执行
    stage('Docker Build') {
      when {
        // expression 返回 true 才进入该 stage
        expression { return params.BUILD_DOCKER_IMAGE }
      }
      steps {
        script {
          // 去除输入首尾空格，避免因为误输入空格导致仓库地址异常
          def registry = params.DOCKER_REGISTRY?.trim()
          // 组装镜像仓库名：
          // - 有 registry：registry/image
          // - 无 registry：仅 image（本地镜像）
          def repo = registry ? "${registry}/${params.DOCKER_IMAGE}" : params.DOCKER_IMAGE
          // 写入环境变量，供后续 shell 命令复用
          env.FULL_IMAGE = repo
          // 使用 Jenkins 内置 BUILD_NUMBER 作为版本 tag，便于追溯到具体构建
          env.IMAGE_TAG = "${env.BUILD_NUMBER}"
        }
        // 构建两个 tag：
        // 1) 具体构建号 tag（可追溯）
        // 2) latest tag（便于默认拉取）
        sh 'docker build -t ${FULL_IMAGE}:${IMAGE_TAG} -t ${FULL_IMAGE}:latest .'
      }
    }

    // 第4阶段：Docker 推送镜像
    // 仅在“已构建镜像 + 用户要求推送”时执行
    stage('Docker Push') {
      when {
        expression { return params.BUILD_DOCKER_IMAGE && params.PUSH_DOCKER_IMAGE }
      }
      steps {
        script {
          // 保护性校验：要求推送时必须配置仓库前缀
          // 否则无法形成可推送的完整远端镜像地址
          if (!params.DOCKER_REGISTRY?.trim()) {
            error('DOCKER_REGISTRY must not be empty when PUSH_DOCKER_IMAGE is enabled.')
          }
        }
        // 从 Jenkins 凭据安全注入 Docker 用户名和密码
        // 凭据类型：Username with password
        withCredentials([usernamePassword(
          credentialsId: params.DOCKER_CREDENTIALS_ID,
          usernameVariable: 'DOCKER_USER',
          passwordVariable: 'DOCKER_PASS'
        )]) {
          // 使用标准输入传递密码，避免在命令行参数中暴露敏感信息
          sh 'echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin'
          // 推送“构建号 tag”
          sh 'docker push ${FULL_IMAGE}:${IMAGE_TAG}'
          // 推送“latest tag”
          sh 'docker push ${FULL_IMAGE}:latest'
          // 退出登录；即使失败也不影响最终结果（避免清理动作导致流水线失败）
          sh 'docker logout || true'
        }
      }
    }
  }

  // post：构建后处理（无论成功/失败都会执行）
  post {
    always {
      // 归档 jar 产物，便于在 Jenkins 页面下载
      // allowEmptyArchive=true：即便没有产物也不因此报错
      archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
      // 收集 surefire 测试报告（如果本次未跑测试，允许为空）
      junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
    }
  }
}
