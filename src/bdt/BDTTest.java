/*
 */
package bdt;

import entity.Persona;
import dao.PersonaDao;
import dao.DAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Marco
 */
public class BDTTest {
    
   
    public static void main(String[] args) {
        
        String PERSISTENCE_UNIT_NAME = "DEFAULT_PU";
        EntityManager em;
        em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
                .createEntityManager();
        
        Persona persona1 = new Persona();
        Persona persona2 = new Persona();
        persona1.setCognome("Rossi");
        persona1.setNome("Mario");
        persona2.setCognome("Verdi");
        persona2.setNome("Mauro");
        
        // scrittura sul DB
        em.getTransaction().begin();
        em.persist(persona1); 
        em.persist(persona2); 
            // -- workaround cache entity manager
        em.flush();
        em.clear();   
            // --
        em.getTransaction().commit();
            
        // lettura dal DB
        TypedQuery<Persona> typedQuery = em.createQuery("SELECT u FROM Persona u", Persona.class);
        List<Persona> personaList = typedQuery.setMaxResults(10).getResultList(); 
        for (Persona p : personaList) {
            System.out.println(p.getCognome() + " - " + p.getNome());
        }
    }
}
