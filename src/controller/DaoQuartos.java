
package controller;

import abstratas.Dados;
import abstratas.Dao;
import java.util.List;
import javax.persistence.Query;
import model.Quartos;


public class DaoQuartos extends Dao<Quartos>{
    public List<Quartos> read(){
        String JPQL = "select c from Quartos c order by c.numero_quarto";
        Query query = Dados.getEm().createQuery(JPQL);
        return query.getResultList();
    }
     public List<Quartos> readByNumeroQuartos(String filtro){
        String JPQL = "select c from Quartos c where c.numero_quarto like ?1 order by c.numero_quarto";
        filtro = "%" + filtro.toUpperCase() + "%";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter(1, filtro);
        return query.getResultList();
    }
    public List<Quartos> readLivres() {
        String jpql = "SELECT q FROM Quartos q WHERE q.status = :status ORDER BY q.numero_quarto";
        Query query = Dados.getEm().createQuery(jpql);
        query.setParameter("status", enums.Status.L);
        return query.getResultList();
    }
    public List<Quartos> readReservados() {
        String jpql = "SELECT q FROM Quartos q WHERE q.status = :status ORDER BY q.numero_quarto";
        Query query = Dados.getEm().createQuery(jpql);
        query.setParameter("status", enums.Status.R);
        return query.getResultList();
    }
}
