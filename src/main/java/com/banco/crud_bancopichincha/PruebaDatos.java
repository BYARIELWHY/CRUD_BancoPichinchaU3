package com.banco.crud_bancopichincha;
import org.hibernate.Session;
public class PruebaDatos {
    public static void main(String[] args) {
        try (Session session =
                HibernateUtil.getSessionFactory().openSession()) {
            Long clientes = session.createQuery(
                    "SELECT COUNT(c) FROM Cliente c",
                    Long.class
            ).getSingleResult();

            Long cuentas = session.createQuery(
                    "SELECT COUNT(c) FROM Cuenta c",
                    Long.class
            ).getSingleResult();

            Long transacciones = session.createQuery(
                    "SELECT COUNT(t) FROM Transaccion t",
                    Long.class
            ).getSingleResult();

            System.out.println("CLIENTES: " + clientes);
            System.out.println("CUENTAS: " + cuentas);
            System.out.println("TRANSACCIONES: " + transacciones);
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
