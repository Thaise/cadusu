import { environment } from './../../../environments/environment';
import { Filtro } from './filtro';
import { UsuarioBuscaDto } from './usuario-busca-dto';
import { BuscaDto } from './busca-dto';
import { UsuarioDto } from './usuario-dto';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { MatSnackBar } from '@angular/material/snack-bar'
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UsuarioService {

    private baseUrl: string = environment.API_URL;
    private headers: any = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    };


    constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    }

    mostraMsgRetorno(msg: string) {
        this.snackBar.open(msg, 'X', { horizontalPosition: 'right', verticalPosition: 'bottom', duration: 3000 })
    }

    insere(usuario: UsuarioDto): Observable<Response> {
        let json: string = JSON.stringify(usuario);
        return this.http.post<Response>(`${this.baseUrl}`, json, { headers: this.headers });
    }


    atualiza(id: number, usuario: UsuarioDto): Observable<any> {
        let json: string = JSON.stringify(usuario);
        return this.http.put<Response>(`${this.baseUrl}/` + id, json, { headers: this.headers });
    }


    remove(id: number): Observable<Response> {
        return this.http.delete<Response>(`${this.baseUrl}/` + id, { headers: this.headers });
    }

    getPeloId(id: number): Observable<UsuarioBuscaDto> {
        return this.http.get<UsuarioBuscaDto>(`${this.baseUrl}/` + id, { headers: this.headers });
    }

    getRegistros(pagina: number, max: number, filtro: Filtro): Observable<BuscaDto<UsuarioBuscaDto>> {
        let urlCompleta: string = `${this.baseUrl}?max=` + max + "&pagina=" + pagina;

        if (filtro) {
            if (filtro.nome) {
                urlCompleta += "&nome=" + filtro.nome;
            }

            if (filtro.email) {
                urlCompleta += "&email=" + filtro.email;
            }

            if (filtro.cpf) {
                urlCompleta += "&cpf=" + filtro.cpf;
            }
        }

        return this.http.get<BuscaDto<UsuarioBuscaDto>>(urlCompleta, { headers: this.headers });
    }

}
