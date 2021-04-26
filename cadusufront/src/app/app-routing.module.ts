import { ListagemUsuarioComponent } from './components/usuario/listagem-usuario/listagem-usuario.component';
import { CriarUsuarioComponent } from './components/usuario/criar-usuario/criar-usuario.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {
    path: "criar-usuario",
    component: CriarUsuarioComponent
  },
  {
    path: "criar-usuario/:id",
    component: CriarUsuarioComponent
  },
  {
    path: "",
    component: ListagemUsuarioComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
