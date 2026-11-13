package com.banco.crud_bancopichincha;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;
public class CargarDatosPrueba {
    public static void main(String[] args) {
        try (Session session =
                HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Long cantidad = session.createQuery(
                    "SELECT COUNT(c) FROM Cuenta c",
                    Long.class
            ).getSingleResult();
            if (cantidad > 0) {
                System.out.println("Ya existen cuentas registradas.");
                tx.rollback();
                return;
            }
            List<Cliente> clientes = session.createQuery(
                    "FROM Cliente ORDER BY id",
                    Cliente.class
            ).setMaxResults(2).getResultList();
            if (clientes.size() < 2) {
                System.out.println("Necesitas al menos 2 clientes.");
                tx.rollback();
                return;
            }
            // Crear tipos de cuenta
            TipoCuenta ahorro = new TipoCuenta();
            ahorro.setNombre("Ahorros");
            ahorro.setDescripcion("Cuenta de ahorros");
            TipoCuenta corriente = new TipoCuenta();
            corriente.setNombre("Corriente");
            corriente.setDescripcion("Cuenta corriente");
            session.persist(ahorro);
            session.persist(corriente);

            // Crear cuentas
            Cuenta cuenta1 = new Cuenta();
            cuenta1.setNumeroCuenta("100001");
            cuenta1.setSaldo(500.0);
            cuenta1.setEstado("Activa");
            cuenta1.setCliente(clientes.get(0));
            cuenta1.setTipoCuenta(ahorro);
            Cuenta cuenta2 = new Cuenta();
            cuenta2.setNumeroCuenta("100002");
            cuenta2.setSaldo(1200.0);
            cuenta2.setEstado("Activa");
            cuenta2.setCliente(clientes.get(1));
            cuenta2.setTipoCuenta(corriente);
            session.persist(cuenta1);
            session.persist(cuenta2);

            // Crear transacciones
            Transaccion t1 = new Transaccion();
            t1.setTipo("Deposito");
            t1.setMonto(200.0);
            t1.setFecha(LocalDate.now());
            t1.setCuenta(cuenta1);
            Transaccion t2 = new Transaccion();
            t2.setTipo("Retiro");
            t2.setMonto(50.0);
            t2.setFecha(LocalDate.now());
            t2.setCuenta(cuenta1);
            Transaccion t3 = new Transaccion();
            t3.setTipo("Deposito");
            t3.setMonto(300.0);
            t3.setFecha(LocalDate.now());
            t3.setCuenta(cuenta2);
            Transaccion t4 = new Transaccion();
            t4.setTipo("Retiro");
            t4.setMonto(100.0);
            t4.setFecha(LocalDate.now());
            t4.setCuenta(cuenta2);
            session.persist(t1);
            session.persist(t2);
            session.persist(t3);
            session.persist(t4);
            tx.commit();
            System.out.println("Datos registrados correctamente.");
            System.out.println("Cuentas creadas: 2");
            System.out.println("Transacciones creadas: 4");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
