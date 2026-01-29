package org.ieselgrao.hibernatepractica.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.ieselgrao.hibernatepractica.model.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetDAO {

    public void savePlanet(Planet planet, EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            em.persist(planet);

            transaction.commit();
            System.out.println("El planeta " + planet.getName()+ " ha sido guardado (persisted) con éxito.");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al guardar los planetas. Se realizó un rollback: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }


    public Planet loadPlanet(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Planet planet = null;

        try {
            planet = em.find(Planet.class, id);
        } catch (Exception e) {
            System.err.println("Error al cargar el planeta con id: " + id + ", " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return planet;
    }

    public List<Planet> loadAllPlanets(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        List<Planet> planets = new ArrayList<>();

        try {
            Query q = em.createQuery("SELECT p FROM Planet p", Planet.class);
            planets = q.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener el listado de planetas: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return planets;
    }


    public void updatePlanet(Planet planet, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(planet);
            transaction.commit();
            System.out.println("El planeta " + planet.getName() + " ha sido actualizado con éxito.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar el planeta. Se realizó un rollback: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deletePlanet(int id, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Planet planet = em.find(Planet.class, id);
            if (planet != null) {
                em.remove(planet);
                System.out.println("El planeta con id " + id + " ha sido eliminado con éxito.");
            } else {
                System.err.println("No se encontró el planeta con id: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar el planeta. Se realizó un rollback: " + e.getMessage());
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

}
