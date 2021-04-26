package br.com.softplan.cadusu.controller.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BuscaDTO<T> {

	@ApiModelProperty(value = "Página atual")
	private Integer pagina;
	@ApiModelProperty(value = "Quantidade máxima por página")
	private Integer maxPorPagina;
	@ApiModelProperty(value = "Total de itens")
	private Long total;
	private List<T> items;
	

	public BuscaDTO(Long total) {
		this.total = total;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getMaxPorPagina() {
		return maxPorPagina;
	}

	public void setMaxPorPagina(Integer maxPorPagina) {
		this.maxPorPagina = maxPorPagina;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
