package org.ieselgrao.hibernatepractica.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.ieselgrao.hibernatepractica.model.Planet;

import java.util.ArrayList;
import java.util.List;

public class SolarSystemDAO {


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
