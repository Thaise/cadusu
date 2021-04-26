package br.com.softplan.cadusu.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.softplan.cadusu.domain.Usuario;
import br.com.softplan.cadusu.service.Filtro;

public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Long getTotal(Filtro filtro) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Usuario> usuario = query.from(Usuario.class);

		montaQuery(filtro, usuario, cb, query);

		query.select(cb.count(usuario));

		return em.createQuery(query).getSingleResult();
	}

	@Override
	public List<Usuario> busca(Filtro filtro, Integer pagina, Integer max) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> usuario = query.from(Usuario.class);
		
		montaQuery(filtro, usuario, cb, query);
		
		query.orderBy(cb.asc(usuario.get("nome")));

		TypedQuery<Usuario> typedQuery = em.createQuery(query);
		typedQuery.setFirstResult(pagina > 0 ? pagina * max : 0);
		typedQuery.setMaxResults(max);

		return typedQuery.getResultList();
	}

	private <T> void montaQuery(Filtro filtro, Root<Usuario> usuario, CriteriaBuilder cb,
			CriteriaQuery<T> query) {
		List<Predicate> predicates = new ArrayList<>();

		if (possuiValor(filtro.getNome())) {
			filtro.setNome("%".concat(filtro.getNome()).concat("%"));
			predicates.add(cb.like(usuario.get("nome"), filtro.getNome()));
		}
		if (possuiValor(filtro.getEmail())) {
			filtro.setEmail("%".concat(filtro.getEmail()).concat("%"));
			predicates.add(cb.like(usuario.get("email"), filtro.getEmail()));
		}
		if (possuiValor(filtro.getCpf())) {
			predicates.add(cb.equal(usuario.get("cpf"), filtro.getCpf()));
		}

		query.where(predicates.toArray(new Predicate[0]));
	}

	private boolean possuiValor(String valor) {
		return valor != null && !valor.trim().isEmpty();
	}

}
