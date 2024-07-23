package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.exception.RoleAlreadyExistException;
import com.lydiaplullc.CarLeasing.model.Role;
import com.lydiaplullc.CarLeasing.model.User;
import com.lydiaplullc.CarLeasing.repository.RoleRepository;
import com.lydiaplullc.CarLeasing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);

        if(roleRepository.existsByName(roleName)) {
            throw new RoleAlreadyExistException(theRole.getName() + " role already exists");
        }

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        this.removeAllUsersFromRole(id);
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if(role.isPresent() && role.get().getUsers().contains(user.get())) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }

        throw new RuntimeException("User not found in role");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if(user.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new RoleAlreadyExistException(
                    user.get().getFirstName() + " is already assigned to the " + role.get().getName() + " role");
        }

        if(role.isPresent()) {
            role.get().assignRoleToUsers(user.get());
            roleRepository.save(role.get());
        }

        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepository.save(role.get());
    }
}
