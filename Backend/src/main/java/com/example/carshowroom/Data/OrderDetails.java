package com.example.carshowroom.Data;

import java.math.BigDecimal;

public class OrderDetails
{
    private String username;
    private String address;
    private String pesel;
    private BigDecimal total;
    private String description;
    private String carsId;

    public OrderDetails()
    {
    }

    public OrderDetails(BigDecimal total, String intent, String description)
    {
        this.total = total;
        this.description = description;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPesel()
    {
        return pesel;
    }

    public void setPesel(String pesel)
    {
        this.pesel = pesel;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getCarsId()
    {
        return carsId;
    }

    public void setCarsId(String carsId)
    {
        this.carsId = carsId;
    }
}
