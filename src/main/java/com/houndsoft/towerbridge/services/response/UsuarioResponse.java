package com.houndsoft.towerbridge.services.response;

import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.model.Usuario.Perfil;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

  private String username;
  private Perfil perfil;
  private Long id;
  private Long usuarioId;

  public static UsuarioResponse buildFromUsuario(Usuario usuario, Long relatedId) {
    return UsuarioResponse.builder()
        .perfil(usuario.getPerfil())
        .username(usuario.getUsername())
        .id(relatedId)
        .usuarioId(usuario.getId())
        .build();
  }
}
