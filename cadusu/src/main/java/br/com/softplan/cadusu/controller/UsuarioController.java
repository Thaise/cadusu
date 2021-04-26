package br.com.softplan.cadusu.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.softplan.cadusu.controller.dto.BuscaDTO;
import br.com.softplan.cadusu.controller.dto.UsuarioBuscaDTO;
import br.com.softplan.cadusu.controller.dto.UsuarioDTO;
import br.com.softplan.cadusu.service.Filtro;
import br.com.softplan.cadusu.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;

@Validated
@RestController
@RequestMapping(value = "/usuarios")
@Api(value = "API para cadastro de usuários", authorizations = { @Authorization(value = "basicAuth") })
public class UsuarioController {

	private Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService service;

	@PostMapping
	@ApiOperation(value = "Cria um usuário com os dados definidos na requisição")
	public @ResponseBody ResponseEntity<String> cria(
			@ApiParam(name = "Usuário que será salvo na base de dados") @RequestBody @Valid UsuarioDTO usuario) {
		return executaCriacao(usuario);
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Altera o usuário conforme os dados definidos na requisição, caso o usuário não seja encontrado ele será criado.")
	public @ResponseBody ResponseEntity<String> atualiza(
			@ApiParam(name = "Id do usuário que deve ser alterado", example = "123") @PathVariable Long id,
			@ApiParam(name = "Usuário que será alterado ou salvo na base de dados") @RequestBody @Valid UsuarioDTO usuario) {
		try {
			service.atualiza(id, usuario);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotFoundException e) {
			return executaCriacao(usuario);
		}

	}

	private ResponseEntity<String> executaCriacao(UsuarioDTO usuario) {
		try {
			Long id = service.cria(usuario);
			URI href = new URI("/usuarios/".concat(id.toString()));
			return ResponseEntity.created(href).build();
		} catch (URISyntaxException e) {
			logger.error("Erro ao montar link do recurso", e);
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Remove o usuário, retorna 404 se ele não existir")
	public @ResponseBody ResponseEntity<String> remove(
			@ApiParam(name = "Id do usuário que deve ser removido", example = "123") @PathVariable Long id) {
		service.remove(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Busca todos os usuários presentes na base de dados", response = BuscaDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<BuscaDTO> buscaUsuarios(
			@ApiParam(required = false, name = "Número da página (primeira página é 0)", example = "0") @RequestParam @PositiveOrZero Integer pagina,
			@ApiParam(required = false, name = "Quantidade máxima de itens por página", example = "20") @RequestParam @Positive @Max(value = 100l) Integer max,
			@ApiParam(required = false, name = "Nome") @RequestParam(required = false) String nome,
			@ApiParam(required = false, name = "E-mail") @RequestParam(required = false) String email,
			@ApiParam(required = false, name = "CPF") @RequestParam(required = false) String cpf) {

		BuscaDTO usuarios = service.buscaUsuarios(pagina, max, new Filtro(nome, cpf, email));

		if (usuarios.getTotal() > 0) {
			return ResponseEntity.ok(usuarios);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Busca um usuário, retorna 404 se ele não existir", response = UsuarioBuscaDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioBuscaDTO> getPorId(
			@ApiParam(name = "Id do usuário que deve ser retornado", example = "123") @PathVariable Long id) {
		return ResponseEntity.ok(service.getUnico(id));
	}

}