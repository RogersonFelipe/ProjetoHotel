
package controller;

import abstratas.Dados;
import abstratas.Dao;
import java.util.List;
import javax.persistence.Query;
import model.Reservas;

public class DaoReservas extends Dao<Reservas>{
    public List<Reservas> read(){
        String JPQL = "select c from Reservas c order by c.hospedes.nome";
        Query query = Dados.getEm().createQuery(JPQL);
        return query.getResultList();
    }
    public List<Reservas> reaByNomeHospedes(String filtro) {
        String JPQL = "select r from Reservas r "
                    + "join r.hospedes h "
                    + "where upper(h.nome) like ?1 "
                    + "order by r.nome_reserva";

        filtro = "%" + filtro.toUpperCase() + "%";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter(1, filtro);
        return query.getResultList();
    }
    public List<Reservas> readComQuartosAlugados() {
        String JPQL = "SELECT r FROM Reservas r JOIN r.quartos q WHERE q.status = :status";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter("status", enums.Status.A);
        return query.getResultList();
    }

}
