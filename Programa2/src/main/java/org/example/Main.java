package org.example;

public class Main {
    public static void main(String[] args) {

        // Ejecutar GUI en hilo de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {

            LoginFrame login = new LoginFrame();
            login.setVisible(true);

        });
    }
}

