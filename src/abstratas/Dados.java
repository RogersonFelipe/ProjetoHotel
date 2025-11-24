
package abstratas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

abstract public class Dados {
    private static EntityManager em = null;
    //

    public static EntityManager getEm() {
        if(em == null){
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjetoHotelPU");
            em = factory.createEntityManager();
        }
        return em;
    }
    
    
}
