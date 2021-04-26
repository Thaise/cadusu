package br.com.softplan.cadusu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.softplan.cadusu.controller.dto.UsuarioDTO;
import br.com.softplan.cadusu.domain.Usuario;
import br.com.softplan.cadusu.repository.UsuarioRepository;
import br.com.softplan.cadusu.service.UsuarioService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository repository;

	@Autowired
	private UsuarioService service;

	@Test(expected = DataIntegrityViolationException.class)
	public void cpfDuplicadoTest() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCpf("999.999.999-99");
		usuario.setNome("Nome");
		usuario.setDtNasc(LocalDate.now().minusYears(10));

		doThrow(DataIntegrityViolationException.class).when(repository).save(any(Usuario.class));

		service.cria(usuario);
	}

	@Test
	public void retornoIdCriacaoTest() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCpf("999.999.999-99");
		usuario.setNome("Nome");
		usuario.setDtNasc(LocalDate.now().minusYears(10));

		Usuario entidade = new Usuario();
		entidade.setId(10l);

		when(repository.save(any(Usuario.class))).thenReturn(entidade);

		assertEquals(10l, service.cria(usuario));
	}

}
