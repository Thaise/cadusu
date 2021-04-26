package br.com.softplan.cadusu.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.softplan.cadusu.controller.dto.BuscaDTO;
import br.com.softplan.cadusu.controller.dto.UsuarioBuscaDTO;
import br.com.softplan.cadusu.controller.dto.UsuarioDTO;
import br.com.softplan.cadusu.domain.Sexo;
import br.com.softplan.cadusu.handler.CadUsuExceptionHandler;
import br.com.softplan.cadusu.service.Filtro;
import br.com.softplan.cadusu.service.UsuarioService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { UsuarioController.class })
@SpringBootTest
public class UsuarioControllerTest {

	public static final String URL_API = "/usuarios";

	@Autowired
	private UsuarioController restController;

	private MockMvc mockMvc;

	@MockBean
	private UsuarioService service;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(restController).setControllerAdvice(new CadUsuExceptionHandler())
				.build();
	}

	@Test
	public void criaUsuarioTest() throws Exception {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCpf("999.999.999-99");
		usuario.setNome("Nome");
		usuario.setDtNasc(LocalDate.now().minusYears(10));

		when(service.cria(any(UsuarioDTO.class))).thenReturn(1l);

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(usuario))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(header().string("Location", URL_API.concat("/1")));

	}

	@Test
	public void atualizaUsuarioNaoExistenteTest() throws Exception {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCpf("999.999.999-99");
		usuario.setNome("Nome");
		usuario.setDtNasc(LocalDate.now().minusYears(10));

		doThrow(new ObjectNotFoundException(2l, "Usuário não encontrado")).when(service).atualiza(any(),
				any(UsuarioDTO.class));
		when(service.cria(any(UsuarioDTO.class))).thenReturn(2l);

		mockMvc.perform(put(URL_API.concat("/2")).content(new ObjectMapper().writeValueAsString(usuario))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(header().string("Location", URL_API.concat("/2")));

	}

	@Test
	public void atualizaUsuarioTest() throws Exception {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCpf("999.999.999-99");
		usuario.setNome("Nome");
		usuario.setDtNasc(LocalDate.now().minusYears(10));

		mockMvc.perform(put(URL_API.concat("/1")).content(new ObjectMapper().writeValueAsString(usuario))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void removeUsuarioNaoExistenteTest() throws Exception {
		doThrow(new ObjectNotFoundException(2l, "Usuário não encontrado")).when(service).remove(2l);

		mockMvc.perform(delete(URL_API.concat("/2")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void removeUsuarioTest() throws Exception {
		mockMvc.perform(delete(URL_API.concat("/1")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void cpfInvalidoTest() throws Exception {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setCpf("999.99999");
		dto.setNome("Nome");
		dto.setDtNasc(LocalDate.now().minusYears(10));

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	public void cpfVazioTest() throws Exception {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setNome("Nome");
		dto.setDtNasc(LocalDate.now().minusYears(10));

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}

	@Test
	public void cpfEmailInvalidoTest() throws Exception {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setCpf("999.999.999-99");
		dto.setNome("Nome");
		dto.setDtNasc(LocalDate.now().minusYears(10));
		dto.setEmail("teste@aaa");

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		dto.setEmail("@gmail.com");

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}

	@Test
	public void dtNascVazioTest() throws Exception {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setCpf("999.999.999-99");
		dto.setNome("Nome");

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	public void nomeVazioTest() throws Exception {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setCpf("999.999.999-99");
		dto.setDtNasc(LocalDate.now().minusYears(10));

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andReturn();

	}


	@Test
	public void dtNascPosteriorADataAtualInvalidaTest() throws Exception {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setCpf("999.999.999-99");
		dto.setNome("Nome");
		dto.setDtNasc(LocalDate.now().plusDays(5));

		mockMvc.perform(post(URL_API).content(new ObjectMapper().writeValueAsString(dto))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void buscaUsuarioTest() throws Exception {
		UsuarioBuscaDTO usuario = getUsuarioDTOCompleto();

		when(service.getUnico(1l)).thenReturn(usuario);

		mockMvc.perform(get(URL_API.concat("/1")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(new ObjectMapper().writeValueAsString(usuario)));

	}

	@Test
	public void buscaUsuariosTest() throws Exception {
		BuscaDTO dto = new BuscaDTO(1l);
		UsuarioBuscaDTO usuario = getUsuarioDTOCompleto();
		dto.setItems(new ArrayList<UsuarioBuscaDTO>(Arrays.asList(usuario)));
		dto.setMaxPorPagina(20);
		dto.setPagina(0);

		when(service.buscaUsuarios(any(), any(), any(Filtro.class))).thenReturn(dto);

		mockMvc.perform(
				get(URL_API.concat("?pagina=0&max=20")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));
	}

	private UsuarioBuscaDTO getUsuarioDTOCompleto() {
		UsuarioBuscaDTO usuario = new UsuarioBuscaDTO();
		usuario.setCpf("999.999.999-99");
		usuario.setNome("Nome");
		usuario.setNacionalidade("Brasileira");
		usuario.setNaturalidade("Santa Catarina");
		usuario.setEmail("empresa@gmail.com");
		usuario.setSexo(Sexo.FEMININO);
		usuario.setDtNasc(LocalDate.of(1991, 10, 29));
		Calendar calendar = Calendar.getInstance();
		calendar.set(2021, 1, 1, 10, 30, 0);
		usuario.setDtCadastro(calendar.getTime());
		calendar.set(2021, 1, 5, 12, 50, 0);
		usuario.setDtAtualizacao(calendar.getTime());
		return usuario;
	}

}
