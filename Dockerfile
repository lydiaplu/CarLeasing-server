# ---------- Build stage ----------
# 第1阶段：构建阶段（在这里编译并打包 Spring Boot）
FROM maven:3.9-eclipse-temurin-17 AS build
# 容器内工作目录，后续命令都在 /app 下执行
WORKDIR /app

# 先只复制 pom.xml，用于下载依赖并尽量复用 Docker 缓存层
COPY pom.xml ./
# 提前拉取依赖到本地仓库，减少后续构建等待时间
RUN mvn -B -q -DskipTests dependency:go-offline


# 复制源码并执行打包
COPY src ./src
# 构建参数：默认跳过测试（可在 build 时通过 --build-arg 覆盖）
ARG SKIP_TESTS=true
# 清理并打包，产物通常输出到 /app/target
RUN mvn -B clean package -DskipTests=${SKIP_TESTS}



# ---------- Runtime stage ----------
# 第2阶段：运行阶段（只保留运行所需环境，镜像更小更安全）
FROM eclipse-temurin:17-jre-jammy
# 运行阶段工作目录
WORKDIR /app

# 安装 curl（用于 HEALTHCHECK），并安装证书，最后清理 apt 缓存减少镜像体积
# 注意：此处需要 root 权限，所以必须在切换 USER app 之前执行
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# 安全加固：创建低权限用户，避免以 root 身份运行应用
RUN groupadd -r app && useradd -r -g app -d /app -s /usr/sbin/nologin app

# 从 build 阶段复制打包产物到运行镜像中
# 这里匹配 *SNAPSHOT.jar，并统一重命名为 /app/app.jar
COPY --from=build /app/target/*SNAPSHOT.jar /app/app.jar
# 设置目录归属，确保 app 用户有读取/执行权限
RUN chown -R app:app /app
# 切换为低权限用户运行
USER app

# Java 运行参数（可通过 docker run / compose 环境变量覆盖）
ENV JAVA_OPTS=""
# 默认 Spring profile（可被外部环境变量覆盖，例如 dev）
ENV SPRING_PROFILES_ACTIVE=prod

# 声明应用监听端口（容器内）
EXPOSE 8080
# 健康检查：访问 actuator 健康端点，失败则返回非0
HEALTHCHECK --interval=30s --timeout=3s --retries=3 CMD curl -fsS http://localhost:8080/actuator/health || exit 1

# 容器启动命令：
# - 注入 JAVA_OPTS
# - 指定 spring profile
# - 启动 app.jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar /app/app.jar"]
