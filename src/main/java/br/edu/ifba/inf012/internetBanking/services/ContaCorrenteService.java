package br.edu.ifba.inf012.internetBanking.services;

import org.springframework.stereotype.Service;

import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaDto;
import br.edu.ifba.inf012.internetBanking.dtos.contaCorrente.ContaForm;
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
	
	public ContaDto criarNovaContaCorrente(ContaForm contaCorrente, Usuario usuario) {
		ContaCorrente novaContaCorrente = this.ccRepository.save(new ContaCorrente(contaCorrente, usuario));
		return new ContaDto(novaContaCorrente);
	}
	
	public ContaDto buscaContaCorrentePorUsuario(Long id) {
		ContaCorrente conta = this.ccRepository.findByUsuarioId(id);
		if(conta == null)
			throw new RuntimeException("Usuario não existe");
		return new ContaDto(conta);
	}

}
