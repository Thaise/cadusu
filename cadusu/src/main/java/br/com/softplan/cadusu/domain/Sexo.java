package br.com.softplan.cadusu.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Sexo {

	NAO_INFORMADO(""), FEMININO("Feminino"), MASCULINO("Masculino"), OUTRO("Outro");

	private String desc;

	private Sexo(String desc) {
		this.desc = desc;
	}

	@JsonValue
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
