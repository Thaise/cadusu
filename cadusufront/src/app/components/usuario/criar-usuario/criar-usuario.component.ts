import { UsuarioService } from './../usuario.service';
import { UsuarioDto } from '../usuario-dto';
import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-criar-usuario',
  templateUrl: './criar-usuario.component.html',
  styleUrls: ['./criar-usuario.component.css']
})
export class CriarUsuarioComponent implements OnInit {

  usuario: UsuarioDto = {
    nome: "",
    cpf: "",
    email: "",
    dtNasc: "",
    nacionalidade: "",
    naturalidade: "",
    sexo: ""
  };
  id: number;
  edicao: boolean;
  dtNascTmp: string;
  @ViewChild('usuarioForm', { static: false }) form: ElementRef;
  renderer: Renderer2;


  constructor(private service: UsuarioService, private router: Router, private routerParams: ActivatedRoute) { }

  ngOnInit(): void {
    this.routerParams.params.subscribe(params => {
      if (params['id']) {
        this.id = +params['id'];
        this.buscaUsuario()
      }
    });

  }

  buscaUsuario() {
    this.service.getPeloId(this.id).subscribe(
      data => {
        this.usuario = data
      }, error => {
        this.service.mostraMsgRetorno('Erro ao buscar usuário: ' + error.error);
      });
  }

  salva() {
    if (this.form.nativeElement.classList.contains("ng-invalid")) {
      this.service.mostraMsgRetorno('Prencha corretamente o formulário');
      return;
    }

    if (this.id) {
      this.service.atualiza(this.id, this.usuario).subscribe(() => {
          this.executaAcaoSucesso()
      },
        error => {
          this.service.mostraMsgRetorno('Erro ao atualizar usuário: ' + error.error);
        });
    } else {
      this.service.insere(this.usuario).subscribe(() => {
          this.executaAcaoSucesso()
      },
        error => {
          this.service.mostraMsgRetorno('Erro ao criar usuário: ' + error.error);
        });
    }
  }

  executaAcaoSucesso(){
    this.service.mostraMsgRetorno('Usuário salvo com sucesso!');
    this.router.navigate(['/'])
  }

}
