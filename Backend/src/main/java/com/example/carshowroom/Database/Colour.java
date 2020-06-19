package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Colour implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long colourId;

    @Column( length = 20)
    private String name;

    @OneToMany(mappedBy = "colour", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car> cars;

    public Colour()
    {
    }

    public Long getColourId()
    {
        return colourId;
    }

    public void setColourId(long colourId)
    {
        this.colourId = colourId;
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
