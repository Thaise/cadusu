package br.com.softplan.cadusu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/source")
public class SourceControler {

	@ApiOperation(value = "Retorna link do repositório no GitHub com o projeto dessa API")
	@GetMapping
	public ResponseEntity<String> getRepoLink() {
		return ResponseEntity.ok("https://github.com/Thaise/cadusu");
	}
}
