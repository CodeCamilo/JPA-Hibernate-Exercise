package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class SolarSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @Column(name = "name",  nullable = false)
    private String name;

    @Column(name = "star_name")
    private String starName;

    // Main star distance to the Sun, in parsecs
    @Column(name = "star_distance")
    private double starDistance;

    // Distance to most far away planet, in UA
    @Column(name = "radius")
    private double Radius;


    @OneToMany(mappedBy = "solarSystem", cascade = CascadeType.ALL)
    //Aqui cambie el LinkedList
    private List<Planet> planets = new LinkedList<>();

    // Constructor vacío requerido por JPA
    public SolarSystem() {
    }

    public SolarSystem(String name, String starName, double starDistance, double Radius) {
        this.name = name;
        this.starName = starName;
        this.starDistance = starDistance;
        this.Radius = Radius;
    }

    // Setters and getters
    public int getId(){
        return id;
    }

    /*
    public void setId(int id){  // Should be removed in the future, since ID should not be assigned this way
        this.id = id;
    }

     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new UniverseException(UniverseException.INVALID_NAME);
        }
        this.name = name;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        if (name == null || name.trim().isEmpty()) {
            throw new UniverseException(UniverseException.INVALID_NAME);
        }
        this.starName = starName;
    }

    public double getStarDistance() {
        return starDistance;
    }

    public void setStarDistance(double starDistance) {
        if (starDistance < 0) {
            throw new UniverseException(UniverseException.INVALID_DISTANCE);
        }
        this.starDistance = starDistance;
    }

    public double getRadius() {
        return Radius;
    }

    public void setRadius(double Radius) {
        // Perhaps we could add a minimum solar system radius
        this.Radius = Radius;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(LinkedList<Planet> planets) {
        if (planets == null || planets.isEmpty())
        {
            throw new UniverseException(UniverseException.INVALID_PLANET_LIST);
        }

        for(Planet p : planets){
            p.setSolarSystem(this);
        }
        this.planets = planets;

    }
}