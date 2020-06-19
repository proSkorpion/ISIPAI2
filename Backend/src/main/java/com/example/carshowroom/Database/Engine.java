package com.example.carshowroom.Database;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Engine implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long engineId;

    @Column( length = 30)
    private String name;

    @Column( length = 10)
    private String capacity;

    @OneToMany(mappedBy = "engine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car> cars;

    public Engine()
    {
    }

    public Long getEngineId()
    {
        return engineId;
    }

    public void setEngineId(long engineId)
    {
        this.engineId = engineId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCapacity()
    {
        return capacity;
    }

    public void setCapacity(String capacity)
    {
        this.capacity = capacity;
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
