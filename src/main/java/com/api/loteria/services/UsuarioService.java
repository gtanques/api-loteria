package com.api.loteria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.loteria.dto.UsuarioDTO;
import com.api.loteria.entities.Aposta;
import com.api.loteria.entities.Usuario;
import com.api.loteria.repositories.ApostaRepository;
import com.api.loteria.repositories.UsuarioRepository;
import com.api.loteria.utilities.ApostaUtility;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ApostaRepository apostaRepository;
	
	@Autowired
	private ApostaUtility apostaUtility;

	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return usuarioRepository.buscarUsuariosComApostas();
	}

	@Transactional
	public UsuarioDTO insertUsuarioAposta(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario(null, usuarioDTO.getEmail());

		for (Aposta a : usuarioDTO.getApostas()) {
			Aposta aposta = apostaRepository.save(a);
			usuario.getApostas().add(aposta);
		}

		usuario = usuarioRepository.save(usuario);

		return new UsuarioDTO(usuario);
	}

	@Transactional
	public UsuarioDTO insertEmailAposta(String email) {	
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setEmail(email);
		Usuario usuario = new Usuario(null, usuarioDTO.getEmail());
		Aposta aposta = new Aposta(null, apostaUtility.gerarCombinacao());
		aposta = apostaRepository.save(aposta);
		usuario.getApostas().add(aposta);

		usuario = usuarioRepository.save(usuario);

		return new UsuarioDTO(usuario);
	}

}
