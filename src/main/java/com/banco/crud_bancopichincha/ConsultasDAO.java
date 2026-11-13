package com.banco.crud_bancopichincha;
import org.hibernate.Session;
import java.util.List;
public class ConsultasDAO {
    // 1. Buscar clientes por nombre
    public List<Cliente> buscarClientes(String nombre, int pagina, int tamanio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT c FROM Cliente c "
                    + "WHERE LOWER(c.nombre) LIKE LOWER(:nombre) "
                    + "ORDER BY c.nombre ASC",
                    Cliente.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .setFirstResult((pagina - 1) * tamanio)
                    .setMaxResults(tamanio)
                    .getResultList();
        }
    }
    // 2. Buscar cuentas por saldo minimo
    public List<Cuenta> buscarCuentas(Double saldoMinimo, int pagina, int tamanio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT c FROM Cuenta c "
                    + "WHERE c.saldo >= :saldo "
                    + "ORDER BY c.saldo DESC",
                    Cuenta.class)
                    .setParameter("saldo", saldoMinimo)
                    .setFirstResult((pagina - 1) * tamanio)
                    .setMaxResults(tamanio)
                    .getResultList();
        }
    }
    // 3. Buscar transacciones por tipo
    public List<Transaccion> buscarTransacciones(String tipo, int pagina, int tamanio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT t FROM Transaccion t "
                    + "WHERE LOWER(t.tipo) = LOWER(:tipo) "
                    + "ORDER BY t.fecha DESC",
                    Transaccion.class)
                    .setParameter("tipo", tipo)
                    .setFirstResult((pagina - 1) * tamanio)
                    .setMaxResults(tamanio)
                    .getResultList();
        }
    }
}
