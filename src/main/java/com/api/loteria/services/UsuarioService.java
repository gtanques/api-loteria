package com.api.loteria.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.loteria.dto.UsuarioDTO;
import com.api.loteria.entities.Aposta;
import com.api.loteria.entities.Usuario;
import com.api.loteria.repositories.ApostaRepository;
import com.api.loteria.repositories.UsuarioRepository;
import com.api.loteria.services.exceptions.ExceptionPersonalizada;
import com.api.loteria.services.exceptions.RecursoNaoEncontradoException;
import com.api.loteria.services.utilities.ApostaUtility;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ApostaRepository apostaRepository;

	@Autowired
	private ApostaUtility apostaUtility;

	@Transactional(readOnly = true)
	public List<UsuarioDTO> findAll() {
		return usuarioRepository.buscarUsuariosComApostas();
	}

	@Transactional(readOnly = true)
	public UsuarioDTO findEmail(String email) {
		Optional<UsuarioDTO> usuario = usuarioRepository.buscarApostasPorEmail(email);
		return usuario.orElseThrow(() -> new RecursoNaoEncontradoException(email));
	}
	
	@Transactional
	public UsuarioDTO insertViaEmail(String email) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail(email);
		Usuario usuario = new Usuario(null, usuarioDTO.getEmail());
		Aposta aposta = new Aposta(null, apostaUtility.gerarCombinacao());
		aposta = apostaRepository.save(aposta);
		usuario.getApostas().add(aposta);

		usuario = usuarioRepository.save(usuario);

		return new UsuarioDTO(usuario);
	}

	@Transactional
	public UsuarioDTO insertViaEmailMuitasApostas(String emailUsuario, Integer quantidadeApostas) {
		
		if ((quantidadeApostas > 10) || quantidadeApostas < 1) {
			throw new ExceptionPersonalizada("Quantidade de apostas inválida. o número de apostas deve ser de 1 a 10 por usuario");
		}
		
		int contador = 0;
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail(emailUsuario);
		
		List<Aposta> listaApostas = new ArrayList<Aposta>();
		Usuario usuario = new Usuario(null, usuarioDTO.getEmail());
				
		while ((contador != quantidadeApostas) && contador < 10) { 
			Aposta aposta = new Aposta(null, apostaUtility.gerarCombinacao());
			listaApostas.add(aposta);
			contador++;
		}

		for (Aposta a : listaApostas) {
			Aposta aposta = apostaRepository.save(a);
			usuario.getApostas().add(aposta);
		}

		usuario = usuarioRepository.save(usuario);

		return new UsuarioDTO(usuario);
	}

}
