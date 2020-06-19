package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Place implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeId;

    @Column(length = 40)
    private String address;

    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Invoice> invoices;


    public Place()
    {
    }

    public Long getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(long placeId)
    {
        this.placeId = placeId;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Set<Invoice> getInvoices()
    {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices)
    {
        this.invoices = invoices;
    }
}
