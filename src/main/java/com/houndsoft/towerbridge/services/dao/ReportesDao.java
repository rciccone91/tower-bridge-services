package com.houndsoft.towerbridge.services.dao;

import com.houndsoft.towerbridge.services.model.Clase;
import com.houndsoft.towerbridge.services.utils.DBConnection;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportesDao {

    public List<Map<String, Object>> getAlumnosConMalDesempenio(){
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            rs = st.executeQuery("select a.nombre_apellido, a.dni, escritura, escucha, fonetica, gramatica, lectura, vocabulario from desemp_alumno\n" +
                    "inner join alumnos as a on a.id = alumno_id\n" +
                    "where a.activo = true and LENGTH(REGEXP_REPLACE(CONCAT(escritura, escucha, fonetica, gramatica, lectura, vocabulario) ,'(MB|B\\+|MB|E)','','g')) >= 2;");

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("nombreApellido",rs.getString("nombre_apellido"));
                map.put("dni",rs.getInt("dni"));
                map.put("escritura",rs.getString("escritura"));
                map.put("escucha",rs.getString("escucha"));
                map.put("fonetica",rs.getString("fonetica"));
                map.put("gramatica",rs.getString("gramatica"));
                map.put("lectura",rs.getString("lectura"));
                map.put("vocabulario",rs.getString("vocabulario"));
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


    public List<Map<String, Object>> getPagosProveedoresDelMes() {
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            rs = st.executeQuery("select p.nombre, p.cuit, m.detalle,to_char(m.fecha,'dd/mm/yyyy') as fecha, m.monto, m.medio_de_pago from movimientos as m \n" +
                    "inner join proveedores as p on p.id = m.proveedor_id\n" +
                    "where tipo_movimiento = 'PAGO' and date_part('month',fecha) =  date_part('month',current_date) and date_part('year',fecha) =  date_part('year',current_date);");

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("proveedor",rs.getString("nombre"));
                map.put("cuit",rs.getString("cuit"));
                map.put("detalle",rs.getString("detalle"));
                map.put("fecha",rs.getString("fecha"));
                map.put("monto",rs.getInt("monto"));
                map.put("medioDePago",rs.getString("medio_de_pago"));
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

    public List<Map<String, Object>> getMovimientosManualesDelMes() {
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            rs = st.executeQuery("select m.detalle,to_char(m.fecha,'dd/mm/yyyy') as fecha, m.monto, m.medio_de_pago,\n" +
                    "case when m.tipo_movimiento = 'ENTRADA_MANUAL' then 'Entrada'\n" +
                    "when m.tipo_movimiento = 'SALIDA_MANUAL' then 'Salida' end as tipo \n" +
                    "from movimientos as m \n" +
                    "where tipo_movimiento in ('ENTRADA_MANUAL', 'SALIDA_MANUAL') and date_part('month',fecha) =  date_part('month',current_date) and date_part('year',fecha) =  date_part('year',current_date);");

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("detalle",rs.getString("detalle"));
                map.put("fecha",rs.getString("fecha"));
                map.put("monto",rs.getInt("monto"));
                map.put("medioDePago",rs.getString("medio_de_pago"));
                map.put("tipo",rs.getString("tipo"));
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

    public List<Map<String, Object>> getClasesDeCursosEspecificos() {
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            rs = st.executeQuery("select cl.nombre as clase, cl.dia || ' - ' || cl.horario_inicio || ' a '|| cl.horario_fin as dia, c.nombre as curso, pr.nombre_apellido as profesor \n" +
                    "from cursos as c \n" +
                    "inner join clases as cl on cl.curso_id = c.id\n" +
                    "inner join profesores as pr on pr.id = cl.profesor_id\n" +
                    "where cl.activo = true and c.tipo_de_curso = 'ESPECIFICOS';");

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("clase",rs.getString("clase"));
                map.put("dia",rs.getString("dia"));
                map.put("curso",rs.getString("curso"));
                map.put("profesor",rs.getString("profesor"));
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

    public List<Map<String, Object>> getClasesPorDiaYHorario(Clase.Dia dia, Integer horarioInicio, Integer horarioFin) {
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            final String query = "select c.nombre, c.dia, c.horario_inicio ||' - ' || c.horario_fin as horario, c.descripcion, cu.nombre as curso, pr.nombre_apellido as profesor from clases as c  \n"
                    + "inner join cursos as cu on cu.id = c.curso_id \n"
                    + "inner join profesores as pr on pr.id = c.profesor_id \n"
                    + "where c.dia = ? \n"
                    + "and (((c.horario_inicio > ? and c.horario_inicio < ?) or (c.horario_fin > ? and c.horario_fin < ?)) or c.horario_inicio = ? and c.horario_fin = ?) and c.activo = true;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, dia.toString());
            statement.setInt(2, horarioInicio);
            statement.setInt(3, horarioFin);
            statement.setInt(4, horarioInicio);
            statement.setInt(5, horarioFin);
            statement.setInt(6, horarioInicio);
            statement.setInt(7, horarioFin);

            rs = statement.executeQuery();

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("nombre",rs.getString("nombre"));
                map.put("dia",rs.getString("dia"));
                map.put("horario",rs.getString("horario"));
                map.put("descripcion",rs.getString("descripcion"));
                map.put("curso",rs.getString("curso"));
                map.put("profesor",rs.getString("profesor"));
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
