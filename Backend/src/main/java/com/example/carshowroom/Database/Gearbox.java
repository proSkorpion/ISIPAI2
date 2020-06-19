package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Gearbox implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gearboxId;

    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "gearbox",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Car> cars;

    public Gearbox()
    {
    }

    public Gearbox(String name)
    {
        this.name = name;
    }

    public Long getGearboxId()
    {
        return gearboxId;
    }

    public void setGearboxId(Long gearboxId)
    {
        this.gearboxId = gearboxId;
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
