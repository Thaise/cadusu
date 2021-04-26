package br.com.softplan.cadusu.repository;

import java.util.List;

import br.com.softplan.cadusu.domain.Usuario;
import br.com.softplan.cadusu.service.Filtro;

public interface UsuarioRepositoryCustom {
	Long getTotal(Filtro filtro);
	List<Usuario> busca(Filtro filtro, Integer pagina, Integer max);
}
