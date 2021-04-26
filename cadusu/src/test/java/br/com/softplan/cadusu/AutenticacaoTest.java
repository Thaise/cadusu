package br.com.softplan.cadusu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.softplan.cadusu.controller.UsuarioControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AutenticacaoTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void getSemAutenticacaoTest() throws Exception {
		mvc.perform(get("/source")).andExpect(status().isOk());
	}

	@Test
	public void getNaoAutorizadoUsuarioESenhaErradosTest() throws Exception {
		mvc.perform(get(UsuarioControllerTest.URL_API).with(httpBasic("usuarioqualquer", "umasenhaqualquer")))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void getAutorizadoSemConteudoTest() throws Exception {
		mvc.perform(get(UsuarioControllerTest.URL_API.concat("?pagina=0&max=1"))
				.with(httpBasic("usuariopf", "usu%45$RA")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void postNaoAutorizadoUsuarioESenhaErradosTest() throws Exception {
		mvc.perform(
				post(UsuarioControllerTest.URL_API).content("{}").with(httpBasic("usuarioqualquer", "umasenhaqualquer"))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void postAutorizadoSemConteudoTest() throws Exception {
		mvc.perform(post(UsuarioControllerTest.URL_API).content("{}").with(httpBasic("usuariopf", "usu%45$RA"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void putNaoAutorizadoUsuarioESenhaErradosTest() throws Exception {
		mvc.perform(put(UsuarioControllerTest.URL_API.concat("/1")).content("{}")
				.with(httpBasic("usuarioqualquer", "umasenhaqualquer"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	public void putAutorizadoSemConteudoTest() throws Exception {
		mvc.perform(put(UsuarioControllerTest.URL_API.concat("/1")).content("{}")
				.with(httpBasic("usuariopf", "usu%45$RA")).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deleteNaoAutorizadoUsuarioESenhaErradosTest() throws Exception {
		mvc.perform(delete(UsuarioControllerTest.URL_API.concat("/1"))
				.with(httpBasic("usuarioqualquer", "umasenhaqualquer"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}

	@Test
	public void deleteAutorizadoSemConteudoTest() throws Exception {
		mvc.perform(delete(UsuarioControllerTest.URL_API.concat("/1")).with(httpBasic("usuariopf", "usu%45$RA"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void getUnicoNaoAutorizadoUsuarioESenhaErradosTest() throws Exception {
		mvc.perform(
				get(UsuarioControllerTest.URL_API.concat("/1")).with(httpBasic("usuarioqualquer", "umasenhaqualquer"))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void getUnicoAutorizadoSemConteudoTest() throws Exception {
		mvc.perform(get(UsuarioControllerTest.URL_API.concat("/1")).with(httpBasic("usuariopf", "usu%45$RA"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
}