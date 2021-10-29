package com.houndsoft.towerbridge.services.dao;

import com.houndsoft.towerbridge.services.utils.DBConnection;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportesDao {


    public List<Map<String, Object>> getMesActualAdeudado(){
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            rs = st.executeQuery("select a.nombre_apellido, a.dni, c.nombre as curso, cl.nombre as clase, c.valor_arancel, case " +
                    "when date_part('day',current_date) <= 15 then 0\n" +
                    "when date_part('day',current_date) > 15 then  c.valor_arancel * 0.10\n" +
                    "when date_part('day',current_date) > 20  then  c.valor_arancel * 0.20\n" +
                    "end as recargo from alumnos as a \n" +
                    "inner join alumnos_x_clase as axc \n" +
                    "on a.id = axc.alumno_id\n" +
                    "inner join clases as cl on axc.clase_id = cl.id\n" +
                    "inner join cursos as c on cl.curso_id = c.id\n" +
                    "where alumno_id not in (select alumno_id from movimientos where tipo_movimiento = 'COBRO' and curso_id = c.id and date_part('month',mes_abonado) =  date_part('month',current_date) and date_part('year',mes_abonado) =  date_part('year',current_date));");

            while (rs.next()) {
                final Map<String, Object> map = new HashMap<>();
                map.put("nombreApellido",rs.getString("nombre_apellido"));
                map.put("dni",rs.getInt("dni"));
                map.put("curso",rs.getString("curso"));
                map.put("clase",rs.getString("clase"));
                map.put("arancel",rs.getInt("valor_arancel"));
                map.put("recargo",rs.getInt("recargo"));
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


    public List<Map<String, Object>> getAlumnosConMalDesempenio(){
        Connection connection = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            rs = st.executeQuery("select a.nombre_apellido, a.dni, escritura, escucha, fonetica, gramatica, lectura, vocabulario from desemp_alumno\n" +
                    "inner join alumnos as a on a.id = alumno_id\n" +
                    "where LENGTH(REGEXP_REPLACE(CONCAT(escritura, escucha, fonetica, gramatica, lectura, vocabulario) ,'(MB|B\\+|MB|E)','','g')) >= 2;");

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
                    "where c.tipo_de_curso = 'ESPECIFICOS';");

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
}
