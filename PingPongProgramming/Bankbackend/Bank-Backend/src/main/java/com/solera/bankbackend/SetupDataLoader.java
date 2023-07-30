package com.solera.bankbackend;

import com.solera.bankbackend.domain.model.Privilege;
import com.solera.bankbackend.domain.model.Role;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IPrivilegeRepository;
import com.solera.bankbackend.repository.IRoleRepository;
import com.solera.bankbackend.repository.IUserRepository;
import com.solera.bankbackend.service.PrivilegeService;
import com.solera.bankbackend.service.RoleService;
import com.solera.bankbackend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege bankAccountPrivilege
                = privilegeService.createPrivilegeIfNotFound("BANK_ACCOUNT_PRIVILEGE");
        Privilege transactionPrivilege
                = privilegeService.createPrivilegeIfNotFound("TRANSACTION_PRIVILEGE");
        Privilege premiumUserPrivilege
                = privilegeService.createPrivilegeIfNotFound("PREMIUM_USER_PRIVILEGE");
        Privilege adminPrivilege
                = privilegeService.createPrivilegeIfNotFound("ADMIN_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(adminPrivilege);
        List<Privilege> userPrivileges = Arrays.asList(bankAccountPrivilege, transactionPrivilege);
        List<Privilege> premiumUserPrivileges = Arrays.asList(premiumUserPrivilege);
        
        roleService.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        roleService.createRoleIfNotFound("ROLE_USER", userPrivileges);
        roleService.createRoleIfNotFound("ROLE_PREMIUM_USER", premiumUserPrivileges);

        Role adminRole = roleService.findByName("ROLE_ADMIN");
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setBirthDate("04/04/1998");
        user.setDocumentId("documentId");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setBalance(0);
        user.setEmail("admin@solera.com");
        user.setRoles(Arrays.asList(adminRole));
        userService.save(user);
        alreadySetup = true;
    }

}