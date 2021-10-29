package com.houndsoft.towerbridge.services.request;

import com.houndsoft.towerbridge.services.model.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDTO {
    private String username;
    private String perfil;
    private String password;

    public Usuario buildUsuario() {
        Usuario.Perfil perfil = Usuario.Perfil.valueOf(this.perfil);
        return Usuario.builder().perfil(perfil).password(this.password).username(this.username).build();
    }
}
