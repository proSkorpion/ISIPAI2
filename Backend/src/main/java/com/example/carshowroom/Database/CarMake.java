package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class CarMake implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long carMakeId;

    @Column( length = 20)
    private String name;

    @OneToMany(mappedBy = "carMake", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car> cars;

    public CarMake()
    {
    }

    public Long getCarMakeId()
    {
        return carMakeId;
    }

    public void setCarMakeId(long id)
    {
        this.carMakeId = id;
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
