package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Invoice implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "payment_form_id", nullable = false)
    private PaymentForm paymentForm;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car> cars;

    private float price;

    private LocalDate date;

    public Invoice()
    {
    }

    public Invoice(PaymentForm paymentForm, Place place, User user)
    {
        this.paymentForm = paymentForm;
        this.place = place;
        this.user = user;
    }

    public Long getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public PaymentForm getPaymentForm()
    {
        return paymentForm;
    }

    public void setPaymentForm(PaymentForm paymentForm)
    {
        this.paymentForm = paymentForm;
    }

    public Place getPlace()
    {
        return place;
    }

    public void setPlace(Place place)
    {
        this.place = place;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
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
