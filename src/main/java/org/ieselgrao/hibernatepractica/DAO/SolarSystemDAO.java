package org.ieselgrao.hibernatepractica.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.ieselgrao.hibernatepractica.model.Planet;
import org.ieselgrao.hibernatepractica.model.SolarSystem;

import java.util.ArrayList;
import java.util.List;

public class SolarSystemDAO {

    public void saveSolarSystem(SolarSystem solarSystem, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(solarSystem);
            transaction.commit();
            System.out.println("El sistema solar " + solarSystem.getName() + " ha sido guardado con éxito.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al guardar el sistema solar. Se realizó un rollback: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public SolarSystem loadSolarSystem(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        SolarSystem solarSystem = null;

        try {
            solarSystem = em.find(SolarSystem.class, id);
        } catch (Exception e) {
            System.err.println("Error al cargar el sistema solar con id: " + id + ", " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return solarSystem;
    }

    public List<SolarSystem> loadAllSolarSystems(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        List<SolarSystem> solarSystems = new ArrayList<>();

        try {
            Query q = em.createQuery("SELECT s FROM SolarSystem s", SolarSystem.class);
            solarSystems = q.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener el listado de sistemas solares: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return solarSystems;
    }

    public void deleteSolarSystem(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            SolarSystem solarSystem = em.find(SolarSystem.class, id);
            if (solarSystem != null) {
                em.remove(solarSystem);
                System.out.println("El sistema solar con id " + id + " ha sido eliminado con éxito.");
            } else {
                System.err.println("No se encontró el sistema solar con id: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar el sistema solar. Se realizó un rollback: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }



    public Planet loadPlanet(int id, EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();

        Planet planet = null;

        try{
            planet = em.find(Planet.class, id);
        } catch (Exception e) {
            System.err.println("Error al importar el planeta con id: " + id + ", " +e.getMessage());

        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }


        return planet;
    }

    public List<Planet> loadAllPlanets(EntityManagerFactory emf){

        EntityManager em = emf.createEntityManager();

        List<Planet> planets = new ArrayList<>();

        try{

            Query q = em.createQuery("SELECT p FROM Planets p", Planet.class);

            planets = q.getResultList();
        }catch (Exception e){

            System.err.println("Error intentando obtener el listado de planetas , " + e.getMessage());

        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }

        }

        return planets;

    }


}
