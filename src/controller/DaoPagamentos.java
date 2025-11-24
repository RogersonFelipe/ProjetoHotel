
package controller;

import abstratas.Dados;
import abstratas.Dao;
import java.util.List;
import javax.persistence.Query;
import model.Pagamentos;

public class DaoPagamentos extends Dao<Pagamentos>{
    public List<Pagamentos> read() {
        String JPQL = "select p from Pagamentos p order by p.reservas.hospedes.nome";
        Query query = Dados.getEm().createQuery(JPQL);
        return query.getResultList();
    }

    public List<Pagamentos> readByNomeHospede(String filtro) {
        String JPQL = "select p from Pagamentos p "
                    + "join p.reservas r "
                    + "join r.hospedes h "
                    + "where upper(h.nome) like ?1 "
                    + "order by h.nome";

        filtro = "%" + filtro.toUpperCase() + "%";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter(1, filtro);
        return query.getResultList();
    }
}

