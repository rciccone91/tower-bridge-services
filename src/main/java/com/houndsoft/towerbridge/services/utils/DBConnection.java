package com.houndsoft.towerbridge.services.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private final static String host = "jdbc:postgresql://localhost:5432/";//conexion;
    private final static String dbName = "towerbridge";//nombre de la base
    private final static String user = "towerbridge";//usuario de la base
    private final static String pass = "ThisIsMyVeryDifficultAndLongPassword";//contraseña de la base
    private static Connection connection = null;

    private DBConnection(){
        connection = initConexion();
    }

    private static Connection initConexion(){
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(host + dbName, user , pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return con;
    }

    public static Connection getConnection(){
        return initConexion();
    }
}
