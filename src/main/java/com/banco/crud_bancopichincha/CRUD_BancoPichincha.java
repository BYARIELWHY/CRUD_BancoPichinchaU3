package com.banco.crud_bancopichincha;
import javax.swing.SwingUtilities;
public class CRUD_BancoPichincha {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaClientes ventana = new VentanaClientes();
            ventana.setVisible(true);
            javax.swing.JMenuBar barra = new javax.swing.JMenuBar();
            javax.swing.JMenu menu = new javax.swing.JMenu("Consultas");
            javax.swing.JMenuItem opcion = new javax.swing.JMenuItem("Consultas JPQL");
            opcion.addActionListener(e -> {
                VentanaConsultas consultas = new VentanaConsultas();
                consultas.setVisible(true);
            });
            menu.add(opcion);
            barra.add(menu);
            ventana.setJMenuBar(barra);
        });
    }
}



