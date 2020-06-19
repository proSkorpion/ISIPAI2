package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Car implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "car_make_id", nullable = false)
    private CarMake carMake;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "colour_id", nullable = false)
    private Colour colour;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "gearbox_id", nullable = false)
    private Gearbox gearbox;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fuel_id", nullable = false)
    private Fuel fuel;

    @Column(length = 40)
    private String emissionsC02;

    @Column(length = 30)
    private String imageUrl;

    private float price;

    private int year;

    @Column(length = 20)
    private String model;

    @Column(length = 30, unique = true, nullable = false)
    private String VIN;

    public Car()
    {
    }

    public Car(CarMake carMake, Type type, Colour colour, Engine engine, Invoice invoice, String VIN, Gearbox gearbox, Fuel fuel)
    {
        this.carMake = carMake;
        this.type = type;
        this.colour = colour;
        this.engine = engine;
        this.invoice = invoice;
        this.VIN = VIN;
        this.gearbox = gearbox;
        this.fuel = fuel;
    }

    public Long getCarId()
    {
        return carId;
    }

    public void setCarId(Long carId)
    {
        this.carId = carId;
    }

    public CarMake getCarMake()
    {
        return carMake;
    }

    public void setCarMake(CarMake carMake)
    {
        this.carMake = carMake;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Colour getColour()
    {
        return colour;
    }

    public void setColour(Colour colour)
    {
        this.colour = colour;
    }

    public Engine getEngine()
    {
        return engine;
    }

    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }

    public Invoice getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
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

    public String getVIN()
    {
        return VIN;
    }

    public void setVIN(String VIN)
    {
        this.VIN = VIN;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public Gearbox getGearbox()
    {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox)
    {
        this.gearbox = gearbox;
    }

    public Fuel getFuel()
    {
        return fuel;
    }

    public void setFuel(Fuel fuel)
    {
        this.fuel = fuel;
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
