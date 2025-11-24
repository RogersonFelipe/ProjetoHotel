
package controller;

import abstratas.Dados;
import abstratas.Dao;
import java.util.List;
import javax.persistence.Query;
import model.Hospedes;

/**
 *
 * @author Administrador
 */
public class DaoHospedes extends Dao<Hospedes>{
     public List<Hospedes> read(){
        String JPQL = "select c from Hospedes c order by c.nome";
        Query query = Dados.getEm().createQuery(JPQL);
        return query.getResultList();
    }
     public List<Hospedes> readByNome(String filtro){
        String JPQL = "select c from Hospedes c where c.nome like ?1 order by c.nome";
        filtro = "%" + filtro.toUpperCase() + "%";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter(1, filtro);
        return query.getResultList();
    }
}
