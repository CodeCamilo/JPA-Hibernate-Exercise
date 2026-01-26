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

}
