import { UsuarioDto } from './usuario-dto';
export interface UsuarioBuscaDto extends UsuarioDto{
    id: number;
    dtAtualizacao: string;
    dtCadastro: string;
}