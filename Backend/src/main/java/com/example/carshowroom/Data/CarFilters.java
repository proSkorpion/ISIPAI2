package com.example.carshowroom.Data;

import java.math.BigDecimal;

public class CarFilters
{
    private String colour;
    private String engineName;
    private String engineCapacity;
    private String carType;
    private BigDecimal price;
    private int year;


    public CarFilters()
    {
    }

    public String getColour()
    {
        return colour;
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public String getEngineName()
    {
        return engineName;
    }

    public void setEngineName(String engineName)
    {
        this.engineName = engineName;
    }

    public String getEngineCapacity()
    {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity)
    {
        this.engineCapacity = engineCapacity;
    }

    public String getCarType()
    {
        return carType;
    }

    public void setCarType(String carType)
    {
        this.carType = carType;
    }
}
