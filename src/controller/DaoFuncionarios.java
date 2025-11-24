
package controller;

import abstratas.Dados;
import abstratas.Dao;
import java.util.List;
import javax.persistence.Query;
import model.Funcionarios;

/**
 *
 * @author Administrador
 */
public class DaoFuncionarios extends Dao<Funcionarios>{
    public List<Funcionarios> read(){
        String JPQL = "select c from Funcionarios c order by c.nome";
        Query query = Dados.getEm().createQuery(JPQL);
        return query.getResultList();
    }
     public List<Funcionarios> readByNome(String filtro){
        String JPQL = "select c from Funcionarios c where c.nome like ?1 order by c.nome";
        filtro = "%" + filtro.toUpperCase() + "%";
        Query query = Dados.getEm().createQuery(JPQL);
        query.setParameter(1, filtro);
        return query.getResultList();
    }
    
}
