package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "duoc131@";

    //Metodo para crear la conexion con MySQL
    public static Connection conectar() {

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    PASSWORD
            );

            System.out.println("Conexión exitosa a la base de datos.");

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");

            e.printStackTrace();
        }
        return conexion;
    }
}