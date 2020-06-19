package com.example.carshowroom.Database;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class PaymentForm implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentFormId;

    @Column(length = 20)
    private String name;

    @OneToMany(mappedBy = "paymentForm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Invoice> invoices;


    public PaymentForm()
    {
    }

    public Long getPaymentFormId()
    {
        return paymentFormId;
    }

    public void setPaymentFormId(long paymentFormId)
    {
        this.paymentFormId = paymentFormId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
