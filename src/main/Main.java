package main;

import dao.ConexionBD;
import vista.VentanaPrincipal;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        ConexionBD.conectar();

        SwingUtilities.invokeLater(() -> {

            VentanaPrincipal ventana = new VentanaPrincipal();

            ventana.setVisible(true);
        });
    }
}