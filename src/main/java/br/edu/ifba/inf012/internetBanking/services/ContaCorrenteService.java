package br.edu.ifba.inf012.internetBanking.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.models.ContaCorrente;
import br.edu.ifba.inf012.internetBanking.models.Usuario;
import br.edu.ifba.inf012.internetBanking.repositories.ContaCorrenteRepository;


@Service
public class ContaCorrenteService {
	
	private ContaCorrenteRepository ccRepository;

	public ContaCorrenteService(ContaCorrenteRepository ccRepository) {
		super();
		this.ccRepository = ccRepository;
	}
	
	public ContaCorrente criarNovaContaCorrente(Usuario usuario) {
		return this.ccRepository.save(new ContaCorrente(usuario));
	}
	
	public ContaDto buscaContaCorrentePorUsuario(Long id) throws Exception {
		Optional<ContaCorrente> conta = this.ccRepository.findById(id);
		if(conta.isEmpty())
			throw new Exception("Usuario não existe");
		return new ContaDto(conta.get());
	}

}
