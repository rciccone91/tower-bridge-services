package com.houndsoft.towerbridge.services.dao;

import com.houndsoft.towerbridge.services.model.Usuario;
import com.houndsoft.towerbridge.services.utils.DBConnection;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsuarioDao {

    public List<Map<String, Object>> getUnassinedUsuariosByPerfil(Usuario.Perfil perfil){
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            String table = perfil.equals(Usuario.Perfil.ALUMNO) ? "alumnos" : "profesores";
            String query = "select id,username FROM usuarios WHERE  id not IN  ( SELECT usuario_id  FROM " + table + " WHERE usuario_id is not null ) and perfil = ? and activo=true";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, perfil.toString());

            rs = statement.executeQuery();

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("id",rs.getLong("id"));
                map.put("username",rs.getString("username"));
                result.add(map);
            }

        } catch (SQLException s) {
            System.out.println("Error: ");
            s.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
        }finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return result;
    }
}
