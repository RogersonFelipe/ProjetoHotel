
package abstratas;

abstract public class Dao<T> {
        public void create(T object){
        Dados.getEm().getTransaction().begin();
        Dados.getEm().persist(object);
        Dados.getEm().getTransaction().commit();
    }
    public void update(T object){
        Dados.getEm().getTransaction().begin();
        Dados.getEm().merge(object);
        Dados.getEm().getTransaction().commit();
    }
    public void delete(T object){
        Dados.getEm().getTransaction().begin();
        Dados.getEm().remove(object);
        Dados.getEm().getTransaction().commit();
    }
    public T read(Class<T> tipo, int id){
        return(Dados.getEm().find(tipo, id));
    }
}
