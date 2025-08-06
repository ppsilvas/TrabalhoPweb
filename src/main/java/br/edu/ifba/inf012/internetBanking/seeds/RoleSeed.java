package br.edu.ifba.inf012.internetBanking.seeds;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.ifba.inf012.internetBanking.models.Role;
import br.edu.ifba.inf012.internetBanking.repositories.RoleRepository;

@Component
@Order(1)
public class RoleSeed implements CommandLineRunner{

	private final RoleRepository repository;
	
	public RoleSeed(RoleRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {
		if(repository.count() == 0) {
			repository.saveAll(List.of(
				new Role(null, "ROLE_OWNER")
			));
			System.out.print("Funçoes inseridas com sucesso.");
		}
	}

}