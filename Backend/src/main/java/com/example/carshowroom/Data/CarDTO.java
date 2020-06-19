package com.example.carshowroom.Data;

public class CarDTO
{
    private Long carId;
    private String carMake;
    private String model;
    private float price;
    private String imageUrl;
    private String engineCapacity;
    private String type;
    private String fuel;
    private String gearbox;
    private int year;
    private String colour;
    private String emissionsC02;

    public CarDTO()
    {
    }

    public Long getCarId()
    {
        return carId;
    }

    public void setCarId(Long carId)
    {
        this.carId = carId;
    }

    public String getCarMake()
    {
        return carMake;
    }

    public void setCarMake(String carMake)
    {
        this.carMake = carMake;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getColour()
    {
        return colour;
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getEngineCapacity()
    {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity)
    {
        this.engineCapacity = engineCapacity;
    }

    public String getFuel()
    {
        return fuel;
    }

    public void setFuel(String fuel)
    {
        this.fuel = fuel;
    }

    public String getGearbox()
    {
        return gearbox;
    }

    public void setGearbox(String gearbox)
    {
        this.gearbox = gearbox;
    }

    public String getEmissionsC02()
    {
        return emissionsC02;
    }

    public void setEmissionsC02(String emissionsC02)
    {
        this.emissionsC02 = emissionsC02;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }
}
