package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Type implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long typeId;

    @Column( length = 20)
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car> cars;

    public Type()
    {
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(long id)
    {
        this.typeId = id;
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
