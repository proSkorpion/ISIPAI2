package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Fuel implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fuelId;

    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "fuel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Car> cars;

    public Fuel()
    {
    }

    public Fuel(String name)
    {
        this.name = name;
    }

    public Long getFuelId()
    {
        return fuelId;
    }

    public void setFuelId(Long fuelId)
    {
        this.fuelId = fuelId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Car> getCars()
    {
        return cars;
    }

    public void setCars(Set<Car> cars)
    {
        this.cars = cars;
    }
}
