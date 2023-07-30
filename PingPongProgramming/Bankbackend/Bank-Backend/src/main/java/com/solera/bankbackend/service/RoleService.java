package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.Privilege;
import com.solera.bankbackend.domain.model.Role;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IRoleRepository;
import com.solera.bankbackend.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService extends CommonService<Role, IRoleRepository>{
    @Transactional
    public Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = repository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            repository.save(role);
        }
        return role;
    }

    public Role findByName(String role) {
        return repository.findByName(role);
    }
}
