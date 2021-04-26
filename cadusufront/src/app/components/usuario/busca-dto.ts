export interface BuscaDto<T>{
    items : T[]; 
    total : number;
    pagina: number;
    max: number;
}