package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.Privilege;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IPrivilegeRepository;
import com.solera.bankbackend.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService extends CommonService<Privilege, IPrivilegeRepository>{
    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = repository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            repository.save(privilege);
        }
        return privilege;
    }
}
