package br.com.softplan.cadusu.controller.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.softplan.cadusu.domain.Sexo;
import br.com.softplan.cadusu.validator.CPF;
import br.com.softplan.cadusu.validator.DataNascimento;
import io.swagger.annotations.ApiModelProperty;

public class UsuarioDTO {

	@ApiModelProperty(value = "CPF", example = "999.999.999-99")
	@NotBlank(message = "CPF é obrigatório")
	@CPF
	private String cpf;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	private Sexo sexo;

	@ApiModelProperty(value = "E-mail", example = "email@servicodeemail.com")
	@Email(message = "E-mail inválido", regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;

	@ApiModelProperty(value = "Data de nascimento", example = "01/10/1980")
	@NotNull(message = "Data de nascimento é obrigatória")
	@DataNascimento
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dtNasc;

	@ApiModelProperty(value = "O país de origem ou o qual pertence")
	private String nacionalidade;

	@ApiModelProperty(value = "Estado / município de origem")
	private String naturalidade;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email != null ? email.toLowerCase() : null;
	}

	public LocalDate getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(LocalDate dtNasc) {
		this.dtNasc = dtNasc;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
