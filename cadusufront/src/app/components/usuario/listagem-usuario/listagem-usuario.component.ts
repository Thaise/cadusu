import { Filtro } from './../filtro';
import { UsuarioService } from './../usuario.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { UsuarioBuscaDto } from '../usuario-busca-dto';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-listagem-usuario',
  templateUrl: './listagem-usuario.component.html',
  styleUrls: ['./listagem-usuario.component.css']
})
export class ListagemUsuarioComponent implements OnInit {
  MAX_POR_PAGINA: number = 20
  usuarios: UsuarioBuscaDto[];
  filtro: Filtro = {
    nome: "",
    email: "",
    cpf: ""
  };
  pagina: number = 0;
  max: number = this.MAX_POR_PAGINA;
  total: number;

  displayedColumns: string[] = ['cpf', 'nome', 'email', 'dtNasc', 'dtAtualizacao', 'dtCadastro', 'editar', 'excluir'];
  @ViewChild(MatPaginator) paginacao: MatPaginator;

  constructor(private service: UsuarioService) { }

  remove(id: number) {
    this.service.remove(id).subscribe(() => {
      this.service.mostraMsgRetorno('Usuário removido com sucesso!');
      this.executaFiltro()
    },
      error => {
        this.service.mostraMsgRetorno('Erro ao remover usuário: ' + error.message);
      });
  }

  executaFiltro() {
    this.pagina = 0;
    this.max = this.MAX_POR_PAGINA;
    this.busca();
  }

  executaPaginacao(event?: PageEvent) {
    this.pagina = event?.pageIndex ? event?.pageIndex : 0;
    this.max = event?.pageSize ? event?.pageSize : this.max;
    this.busca();
  }

  public busca() {
    this.service.getRegistros(this.pagina, this.max, this.filtro).subscribe(
      data => {
        this.usuarios = data.items,
          this.total = data.total
      }, error => {
        if (error.status == 404) {
          this.usuarios = []
          this.service.mostraMsgRetorno('Nenhum usuário foi encontrado');
        } else {
          this.service.mostraMsgRetorno('Erro ao buscar usuários: ' + error.error);
        }
      });
  }


  ngOnInit(): void {
    this.busca()
  }

}
