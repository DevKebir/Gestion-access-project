package com.episen.membership;

import com.episen.membership.model.Role;
import com.episen.membership.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MembershipApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(MembershipApplication.class, args);

		RoleRepository roleRepository = context.getBean(RoleRepository.class);

		Role role = new Role("ADMIN");
		roleRepository.addAccess(role.getName());

		role = new Role("USER");
		roleRepository.addAccess(role.getName());

		role = new Role("MANAGER");
		roleRepository.addAccess(role.getName());

	}
}
