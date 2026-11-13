package com.banco.crud_bancopichincha;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class VentanaConsultas extends JFrame {
    private final ConsultasDAO dao = new ConsultasDAO();
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtSaldo = new JTextField(15);
    private final JTextField txtTipo = new JTextField(15);
    private final DefaultTableModel modeloClientes =
            new DefaultTableModel(
                    new String[]{"ID", "Nombre", "Cedula", "Correo"}, 0);
    private final DefaultTableModel modeloCuentas =
            new DefaultTableModel(
                    new String[]{"ID", "Numero", "Saldo", "Estado"}, 0);
    private final DefaultTableModel modeloTransacciones =
            new DefaultTableModel(
                    new String[]{"ID", "Tipo", "Monto", "Fecha"}, 0);
    private int paginaClientes = 1;
    private int paginaCuentas = 1;
    private int paginaTransacciones = 1;
    private final int tamanio = 5;
    public VentanaConsultas() {
        setTitle("Banco Pichincha - Consultas JPQL");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTabbedPane pestañas = new JTabbedPane();

        // Pestaña de clientes
        JPanel panelClientes = new JPanel(new BorderLayout());
        JButton btnBuscarClientes = new JButton("Buscar");
        JButton btnAnteriorClientes = new JButton("Anterior");
        JButton btnSiguienteClientes = new JButton("Siguiente");
        JPanel controlesClientes = new JPanel(new FlowLayout());
        controlesClientes.add(new JLabel("Nombre:"));
        controlesClientes.add(txtNombre);
        controlesClientes.add(btnBuscarClientes);
        controlesClientes.add(btnAnteriorClientes);
        controlesClientes.add(btnSiguienteClientes);
        JTable tablaClientes = new JTable(modeloClientes);
        panelClientes.add(controlesClientes, BorderLayout.NORTH);
        panelClientes.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        // Pestaña de cuentas
        JPanel panelCuentas = new JPanel(new BorderLayout());
        JButton btnBuscarCuentas = new JButton("Buscar");
        JButton btnAnteriorCuentas = new JButton("Anterior");
        JButton btnSiguienteCuentas = new JButton("Siguiente");
        JPanel controlesCuentas = new JPanel(new FlowLayout());
        controlesCuentas.add(new JLabel("Saldo minimo:"));
        controlesCuentas.add(txtSaldo);
        controlesCuentas.add(btnBuscarCuentas);
        controlesCuentas.add(btnAnteriorCuentas);
        controlesCuentas.add(btnSiguienteCuentas);
        JTable tablaCuentas = new JTable(modeloCuentas);
        panelCuentas.add(controlesCuentas, BorderLayout.NORTH);
        panelCuentas.add(new JScrollPane(tablaCuentas), BorderLayout.CENTER);

        // Pestaña de transacciones
        JPanel panelTransacciones = new JPanel(new BorderLayout());
        JButton btnBuscarTransacciones = new JButton("Buscar");
        JButton btnAnteriorTransacciones = new JButton("Anterior");
        JButton btnSiguienteTransacciones = new JButton("Siguiente");
        JPanel controlesTransacciones = new JPanel(new FlowLayout());
        controlesTransacciones.add(new JLabel("Tipo:"));
        controlesTransacciones.add(txtTipo);
        controlesTransacciones.add(btnBuscarTransacciones);
        controlesTransacciones.add(btnAnteriorTransacciones);
        controlesTransacciones.add(btnSiguienteTransacciones);
        JTable tablaTransacciones = new JTable(modeloTransacciones);
        panelTransacciones.add(controlesTransacciones, BorderLayout.NORTH);
        panelTransacciones.add(new JScrollPane(tablaTransacciones), BorderLayout.CENTER);
        pestañas.addTab("Clientes", panelClientes);
        pestañas.addTab("Cuentas", panelCuentas);
        pestañas.addTab("Transacciones", panelTransacciones);
        add(pestañas);

        // Botones de clientes
        btnBuscarClientes.addActionListener(e -> {
            paginaClientes = 1;
            listarClientes();
        });
        btnAnteriorClientes.addActionListener(e -> {
            if (paginaClientes > 1) {
                paginaClientes--;
                listarClientes();
            }
        });
        btnSiguienteClientes.addActionListener(e -> {
            paginaClientes++;
            listarClientes();
        });

        // Botones de cuentas
        btnBuscarCuentas.addActionListener(e -> {
            paginaCuentas = 1;
            listarCuentas();
        });
        btnAnteriorCuentas.addActionListener(e -> {
            if (paginaCuentas > 1) {
                paginaCuentas--;
                listarCuentas();
            }
        });
        btnSiguienteCuentas.addActionListener(e -> {
            paginaCuentas++;
            listarCuentas();
        });

        // Botones de transacciones
        btnBuscarTransacciones.addActionListener(e -> {
            paginaTransacciones = 1;
            listarTransacciones();
        });
        btnAnteriorTransacciones.addActionListener(e -> {
            if (paginaTransacciones > 1) {
                paginaTransacciones--;
                listarTransacciones();
            }
        });
        btnSiguienteTransacciones.addActionListener(e -> {
            paginaTransacciones++;
            listarTransacciones();
        });
    }
    private void listarClientes() {
        modeloClientes.setRowCount(0);
        List<Cliente> lista = dao.buscarClientes(
                txtNombre.getText().trim(),
                paginaClientes,
                tamanio
        );
        for (Cliente c : lista) {
            modeloClientes.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getCedula(),
                c.getCorreo()
            });
        }
    }
    private void listarCuentas() {
        try {
            double saldo = Double.parseDouble(txtSaldo.getText().trim());
            modeloCuentas.setRowCount(0);
            List<Cuenta> lista = dao.buscarCuentas(
                    saldo,
                    paginaCuentas,
                    tamanio
            );
            for (Cuenta c : lista) {
                modeloCuentas.addRow(new Object[]{
                    c.getId(),
                    c.getNumeroCuenta(),
                    c.getSaldo(),
                    c.getEstado()
                });
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingresa un saldo valido.");
        }
    }
    private void listarTransacciones() {
        modeloTransacciones.setRowCount(0);
        List<Transaccion> lista = dao.buscarTransacciones(
                txtTipo.getText().trim(),
                paginaTransacciones,
                tamanio
        );
        for (Transaccion t : lista) {
            modeloTransacciones.addRow(new Object[]{
                t.getId(),
                t.getTipo(),
                t.getMonto(),
                t.getFecha()
            });
        }
    }
}

