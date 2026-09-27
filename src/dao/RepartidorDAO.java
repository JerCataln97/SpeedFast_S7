package dao;

import modelo.Repartidor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    //Metodo para guarda repartidor en la base de datos
    public boolean guardar(Repartidor repartidor) {

        String sql = """
                INSERT INTO repartidor (nombre)
                VALUES (?)
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, repartidor.getNombre());

            sentencia.executeUpdate();

            System.out.println("Repartidor guardado correctamente.");

            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar el repartidor.");

            e.printStackTrace();

            return false;
        }
    }

    //Metodo para obtener todos los repartidores desde MySQL
    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidor ORDER BY id";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");

                Repartidor repartidor = new Repartidor(id, nombre);

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los repartidores.");

            e.printStackTrace();
        }

        return repartidores;
    }

    //Metodo para eliminar repaartidor
    public boolean eliminar(int id) {

        String verificar = "SELECT COUNT(*) FROM entrega WHERE id_repartidor = ?";
        String eliminar = "DELETE FROM repartidor WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentenciaVerificar = conexion.prepareStatement(verificar)
        ) {

            sentenciaVerificar.setInt(1, id);

            ResultSet resultado = sentenciaVerificar.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {

                System.out.println("No se puede eliminar el repartidor porque tiene entregas.");

                return false;
            }

            try (PreparedStatement sentenciaEliminar = conexion.prepareStatement(eliminar)) {

                sentenciaEliminar.setInt(1, id);

                int filas = sentenciaEliminar.executeUpdate();

                return filas > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el repartidor.");

            e.printStackTrace();

            return false;
        }
    }
}