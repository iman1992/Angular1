
package facade;

import entity.Car;
import exception.CarNotFoundException;
import interfaces.IControlFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;


public class ControlFacade implements IControlFacade {
    
    private EntityManagerFactory emf;
    
    public ControlFacade(EntityManagerFactory e) {
        emf = e;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    @Override
    public void addCar(Car car) {
        EntityManager em = getEntityManager();
        
        try{
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } finally{
            em.close();
        }
    }

    @Override
    public List<Car> getCars() {
        EntityManager em = getEntityManager();
        try {
            List<Car> cars = em.createQuery("select c from Car c").getResultList();
            return cars;
        } finally {
            em.close();
        }
    }

    @Override
    public Car getCar(int id) throws CarNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Car c = em.find(Car.class, id);
            return c;
        } catch (NoResultException e) {
            throw new CarNotFoundException("No Car found with provided id");
        } finally {
            em.close();
        }
    }

    @Override
    public void editCar(Car car) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Car c WHERE c.id=:id").setParameter("id", car.getId());
            Car edited = (Car) query.getSingleResult();
            edited.setModel_year(car.getModel_year());
            edited.setRegistered(car.getRegistered());
            edited.setMake(car.getMake());
            edited.setModel(car.getModel());
            edited.setDescription(car.getDescription());
            edited.setPrice(car.getPrice());
            em.getTransaction().begin();
            em.persist(edited);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteCar(int id) {
        EntityManager em = getEntityManager();
        try {
            Car c = em.find(Car.class, id);
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
}
