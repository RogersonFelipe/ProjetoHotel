
package controller;

import abstratas.Dados;
import abstratas.Dao;
import java.util.List;
import javax.persistence.Query;
import model.AvaliacaoQuarto;

public class DaoAvaliacaoQuarto extends Dao<AvaliacaoQuarto>{
      public List<AvaliacaoQuarto> read() {
        String JPQL = "select a from AvaliacaoQuarto a order by a.avaliacao";
        Query query = Dados.getEm().createQuery(JPQL);
        return query.getResultList();
    }

    public List<AvaliacaoQuarto> readByNomeHospede(String filtro) {
        String JPQL = "select a from AvaliacaoQuarto  a "
                    + "join a.reservas r "
                    + "join r.hospedes h "
                    + "where upper(h.nome) like ?1 "
                    + "order by h.nome";

        filtro = "%" + filtro.toUpperCase() + "%";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter(1, filtro);
        return query.getResultList();
    }
}
