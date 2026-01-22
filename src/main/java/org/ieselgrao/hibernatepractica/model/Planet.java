package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Planet {

    // Constants for minimum values
    private static final double MIN_MASS = 1e18;
    private static final double MIN_RADIUS = 400;

    /*
        Probablemente quieras guardar una referencia al sistema solar para las tablas SQL,
        deberías considerar la posibilidad de añadir un campo:
        private SolarSystem solarSystem
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_moons")
    private int numberOfMoons;

    @Column(name = "mass")
    private double mass;

    @Column(name = "radius")
    private double radius;

    @Column (name = "gravity")
    private double gravity;

    @Column (name = "last_albedo_measurement")
    private LocalDate lastAlbedoMeasurement;

    @Column (name = "has_rings")
    private boolean hasRings;

    @ManyToOne
    @JoinColumn(name = "id_solar_system")
    private SolarSystem solarSystem;


    // Constructor with no "numerOfMoons" and no "hasRings" to simplify things
    public Planet(String name, double mass, double radius, double gravity, LocalDate lastAlbedoMeasurement) {
        this(name, 0, mass, radius, gravity, lastAlbedoMeasurement, false);
    }
    public Planet(String name, int numberOfMoons, double mass, double radius, double gravity, LocalDate lastAlbedoMeasurement, boolean hasRings) {
        setName(name);
        setNumberOfMoons(numberOfMoons);
        setMass(mass);
        setRadius(radius);
        setGravity(gravity);
        setLastAlbedoMeasurement(lastAlbedoMeasurement);
        setHasRings(hasRings);
    }

    // Getters and setters
    public int getId(){
        return id;
    }
    public void setId(int id){  // Should be removed in the future, since ID should not be assigned this way
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new UniverseException(UniverseException.INVALID_NAME);
        }
        this.name = name;
    }

    public int getNumberOfMoons() {
        return numberOfMoons;
    }
    public void setNumberOfMoons(int numberOfMoons) {
        if (numberOfMoons < 0) {
            throw new UniverseException(UniverseException.INVALID_NUMBER_OF_MOONS);
        }
        this.numberOfMoons = numberOfMoons;
    }

    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        if (mass < MIN_MASS) {
            throw new UniverseException(UniverseException.INVALID_MASS);
        }
        this.mass = mass;
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        if (radius < MIN_RADIUS) {
            throw new UniverseException(UniverseException.INVALID_RADIUS);
        }
        this.radius = radius;
    }
    public double getGravity() {
        return gravity;
    }
    public void setGravity(double gravity) {
        if (gravity <= 0) {
            throw new UniverseException(UniverseException.INVALID_GRAVITY);
        }
        this.gravity = gravity;
    }
    public LocalDate getLastAlbedoMeasurement() {
        return lastAlbedoMeasurement;
    }
    public void setLastAlbedoMeasurement(LocalDate lastAlbedoMeasurement) {
        // last albedo measurement is allowed to be today (LocalDate.now())
        if (lastAlbedoMeasurement == null || lastAlbedoMeasurement.isAfter(LocalDate.now())) {
            throw new UniverseException(UniverseException.INVALID_LAST_ALBEDO_MEASUREMENT);
        }
        this.lastAlbedoMeasurement = lastAlbedoMeasurement;
    }
    public boolean hasRings() {
        return hasRings;
    }
    public void setHasRings(boolean hasRings) {
        this.hasRings = hasRings;
    }
}
