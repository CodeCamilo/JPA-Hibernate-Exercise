package org.ieselgrao.hibernatepractica.controller;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ieselgrao.hibernatepractica.DAO.PlanetDAO;
import org.ieselgrao.hibernatepractica.DAO.SolarSystemDAO;
import org.ieselgrao.hibernatepractica.model.Planet;
import org.ieselgrao.hibernatepractica.model.SolarSystem;
/**
 * Controller class for the UniGraoVerse application
 */
public class UniGraoVerseController {

    private static UniGraoVerseController instance;
    private LinkedList<SolarSystem> solarSystems;
    private EntityManagerFactory emf;
    private PlanetDAO planetDAO;
    private SolarSystemDAO solarSystemDAO;
    private String persistenceUnitName = "unidad_planetas"; // Por defecto

    private UniGraoVerseController() {
        // Inicializar el EntityManagerFactory y los DAOs
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        planetDAO = new PlanetDAO();
        solarSystemDAO = new SolarSystemDAO();

        solarSystems = loadSolarSystems();
    }


    public static UniGraoVerseController getInstance() {
        if (instance == null) {
            instance = new UniGraoVerseController();
        }
        return instance;
    }


    public static void resetInstance() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }


    public static void setPersistenceUnitName(String unitName) {
        // Reiniciar la instancia si ya existe
        resetInstance();
        // Crear nueva instancia con la unidad de persistencia especificada
        instance = new UniGraoVerseController();
        instance.persistenceUnitName = unitName;
        instance.emf = Persistence.createEntityManagerFactory(unitName);
        instance.solarSystems = instance.loadSolarSystems();
    }


    private LinkedList<SolarSystem> loadSolarSystems() {
        List<SolarSystem> loadedSystems = solarSystemDAO.loadAllSolarSystems(emf);

        // Si no hay sistemas solares, crear algunos de ejemplo
        if (loadedSystems.isEmpty()) {
            System.out.println("No se encontraron sistemas solares. Creando datos de ejemplo...");

            loadedSystems = solarSystemDAO.loadAllSolarSystems(emf);
        }

        return new LinkedList<>(loadedSystems);
    }

/*
    private void initializeDefaultData() {
        // Crear sistema solar
        SolarSystem solarSystem = new SolarSystem("Sistema Solar", "Sol", 0.0, 30.0);
        solarSystemDAO.saveSolarSystem(solarSystem, emf);

        // Recargar el sistema solar para obtener su ID
        List<SolarSystem> systems = solarSystemDAO.loadAllSolarSystems(emf);
        if (!systems.isEmpty()) {
            SolarSystem savedSystem = systems.get(0);

            // Crear planetas
            LinkedList<Planet> solarPlanets = new LinkedList<>();
            solarPlanets.add(new Planet("Mercurio", 0, 3.3011e23, 2439.7, 3.7, LocalDate.of(2023, 1, 15), false));
            solarPlanets.add(new Planet("Venus", 0, 4.8675e24, 6051.8, 8.87, LocalDate.of(2023, 2, 20), false));
            solarPlanets.add(new Planet("Tierra", 1, 5.972e24, 6371.0, 9.80, LocalDate.of(2024, 1, 1), false));
            solarPlanets.add(new Planet("Marte", 2, 6.4171e23, 3389.5, 3.71, LocalDate.of(2023, 5, 5), false));

            // Asignar los planetas al sistema solar y persistirlos
            for (Planet planet : solarPlanets) {
                planet.setSolarSystem(savedSystem);
                planetDAO.savePlanet(planet, emf);
            }
        }

        // Crear sistema Lich
        SolarSystem lichSystem = new SolarSystem("Sistema Lich", "Lich", 2283.0, 10.0);
        solarSystemDAO.saveSolarSystem(lichSystem, emf);

        systems = solarSystemDAO.loadAllSolarSystems(emf);
        if (systems.size() > 1) {
            SolarSystem savedLich = systems.get(1);

            LinkedList<Planet> lichPlanets = new LinkedList<>();
            lichPlanets.add(new Planet("Draugr", 0, 1.194e23, 1400.0, 0.45, LocalDate.of(2012, 1, 21), false));
            lichPlanets.add(new Planet("Poltergeist", 1, 2.568e25, 9400.0, 12.8, LocalDate.of(2013, 1, 21), false));

            for (Planet planet : lichPlanets) {
                planet.setSolarSystem(savedLich);
                planetDAO.savePlanet(planet, emf);
            }
        }

        // Crear sistema inventado
        SolarSystem inventedSystem = new SolarSystem("Sistema Inventado", "Estrella Ficticia", 500.0, 15.0);
        solarSystemDAO.saveSolarSystem(inventedSystem, emf);

        systems = solarSystemDAO.loadAllSolarSystems(emf);
        if (systems.size() > 2) {
            SolarSystem savedInvented = systems.get(2);

            LinkedList<Planet> inventedPlanets = new LinkedList<>();
            inventedPlanets.add(new Planet("Tatooine", 0, 3.3011e23, 2439.7, 3.7, LocalDate.of(2023, 1, 15), false));
            inventedPlanets.add(new Planet("Arrakis", 0, 4.8675e24, 6051.8, 8.87, LocalDate.of(2023, 2, 20), false));

            for (Planet planet : inventedPlanets) {
                planet.setSolarSystem(savedInvented);
                planetDAO.savePlanet(planet, emf);
            }
        }
    }
*/
    /**
     * Añado un sistema solar y lo persisto en la base de datos
     */
    public void addSolarSystem(String name, String starName, double starDistance, double radius) {
        SolarSystem newSystem = new SolarSystem(name, starName, starDistance, radius);
        solarSystemDAO.saveSolarSystem(newSystem, emf);

        // Recargar los sistemas solares
        solarSystems = loadSolarSystems();
    }

    /**
     * Actualiza un planeta existente y lo persiste en la base de datos
     * @return true si es exitoso, false si falla
     */
    public boolean updatePlanet(int planetID, String name, double mass, double radius, double gravity, LocalDate date) {
        try {
            Planet planet = planetDAO.loadPlanet(planetID, emf);

            if (planet != null) {
                planet.setName(name);
                planet.setMass(mass);
                planet.setRadius(radius);
                planet.setGravity(gravity);
                planet.setLastAlbedoMeasurement(date);

                planetDAO.updatePlanet(planet, emf);

                // Recargar los sistemas solares
                solarSystems = loadSolarSystems();
                return true;
            } else {
                System.err.println("No se encontró el planeta con ID: " + planetID);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el planeta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Añade un planeta al sistema solar especificado y lo persiste
     */
    public void addPlanet(int solarSystemId, String name, double mass, double radius, double gravity, LocalDate lastAlbedoMeasurement) {
        try {
            SolarSystem solarSystem = solarSystemDAO.loadSolarSystem(solarSystemId, emf);

            if (solarSystem != null) {
                Planet newPlanet = new Planet(name, mass, radius, gravity, lastAlbedoMeasurement);
                newPlanet.setSolarSystem(solarSystem);

                planetDAO.savePlanet(newPlanet, emf);

                // Recargar los sistemas solares
                solarSystems = loadSolarSystems();

                System.out.println("Planeta añadido al Sistema Solar " + solarSystemId);
            } else {
                System.err.println("No se encontró el sistema solar con id: " + solarSystemId);
            }
        } catch (Exception e) {
            System.err.println("Error al añadir planeta: " + e.getMessage());
        }
    }

    /**
     * Elimina un planeta de la base de datos
     */
    public void removePlanet(int planetId) {
        try {
            planetDAO.deletePlanet(planetId, emf);

            // Recargar los sistemas solares
            solarSystems = loadSolarSystems();
        } catch (Exception e) {
            System.err.println("Error al eliminar planeta: " + e.getMessage());
        }
    }

    /**
     * Elimina un sistema solar de la base de datos (y sus planetas por cascade)
     */
    public void removeSolarSystem(int solarSystemId) {
        try {
            solarSystemDAO.deleteSolarSystem(solarSystemId, emf);

            // Recargar los sistemas solares
            solarSystems = loadSolarSystems();
        } catch (Exception e) {
            System.err.println("Error al eliminar sistema solar: " + e.getMessage());
        }
    }

    /**
     * Get the list of solar systems
     * @return List of solar system in JSON format with keys name, star and radius
     */
    public List<String> getSolarSystemsData() {
        List<String> solarSystemsData = new ArrayList<>();
        Gson gson = new Gson();

        for (SolarSystem s : solarSystems) {
            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(s.getId()));
            data.put("name", s.getName());
            data.put("star", s.getStarName());
            data.put("distance", String.valueOf(s.getStarDistance()));
            data.put("radius", String.valueOf(s.getRadius()));
            solarSystemsData.add(gson.toJson(data));
        }

        return solarSystemsData;
    }

    /**
     * Get the list of planets for a given solar system
     * @return A list with all planets data, in json format with keys 'name', 'mass' and 'radius'
     */
    public List<String> getPlanetsData(int solarSystemId) {
        List<String> planetsData = new ArrayList<>();
        Gson gson = new Gson();

        for (SolarSystem s : solarSystems) {
            if (s.getId() != solarSystemId) {
                continue;
            }

            for (Planet p : s.getPlanets()) {
                Map<String, String> data = new HashMap<>();
                data.put("id", String.valueOf(p.getId()));
                data.put("name", p.getName());
                data.put("mass", String.valueOf(p.getMass()));
                data.put("radius", String.valueOf(p.getRadius()));
                data.put("gravity", String.valueOf(p.getGravity()));
                data.put("date", String.valueOf(p.getLastAlbedoMeasurement()));
                planetsData.add(gson.toJson(data));
            }
        }

        return planetsData;
    }

    /**
     * Cierra el EntityManagerFactory cuando ya no se necesita
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}