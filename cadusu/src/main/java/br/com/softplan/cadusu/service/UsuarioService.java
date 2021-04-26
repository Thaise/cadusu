package br.com.softplan.cadusu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.softplan.cadusu.controller.dto.BuscaDTO;
import br.com.softplan.cadusu.controller.dto.UsuarioBuscaDTO;
import br.com.softplan.cadusu.controller.dto.UsuarioDTO;
import br.com.softplan.cadusu.domain.Usuario;
import br.com.softplan.cadusu.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Long cria(UsuarioDTO usuario) {
		Usuario usuarioEntity = new Usuario();
		BeanUtils.copyProperties(usuario, usuarioEntity);
		Date dtAtual = new Date();
		usuarioEntity.setDtCadastro(dtAtual);
		usuarioEntity.setDtAtualizacao(dtAtual);
		return salva(usuarioEntity);
	}

	public void atualiza(Long id, UsuarioDTO usuario) throws ObjectNotFoundException {
		Usuario usuarioEntity = buscaPor(id);
		BeanUtils.copyProperties(usuario, usuarioEntity);
		usuarioEntity.setDtAtualizacao(new Date());
		salva(usuarioEntity);
	}
	
	public Long salva(Usuario usuario) {
		try {
			return repository.save(usuario).getId();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("CPF já foi cadastrado");
		}
	}

	public void remove(Long id) throws ObjectNotFoundException {
		repository.delete(buscaPor(id));
	}

	public BuscaDTO<UsuarioBuscaDTO> buscaUsuarios(Integer pagina, Integer max, Filtro filtro) {
		BuscaDTO<UsuarioBuscaDTO> busca = new BuscaDTO<UsuarioBuscaDTO>(repository.getTotal(filtro));
		busca.setMaxPorPagina(max);
		busca.setPagina(pagina);
		
		List<UsuarioBuscaDTO> usuariosDto = new ArrayList<UsuarioBuscaDTO>();

		usuariosDto.addAll(repository.busca(filtro, pagina, max).stream().map(u -> {
			UsuarioBuscaDTO dto = new UsuarioBuscaDTO();
			BeanUtils.copyProperties(u, dto);
			return dto;
		}).collect(Collectors.toList()));

		busca.setItems(usuariosDto);
		
		return busca;
	}

	public UsuarioBuscaDTO getUnico(Long id) throws ObjectNotFoundException {
		UsuarioBuscaDTO usuario = new UsuarioBuscaDTO();
		BeanUtils.copyProperties(buscaPor(id), usuario);
		return usuario;
	}

	public Usuario buscaPor(Long id) throws ObjectNotFoundException {
		Usuario usuarioEntity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(id, "Usuário não encontrado"));
		return usuarioEntity;
	}
}
