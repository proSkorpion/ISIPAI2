package com.example.carshowroom.Services;

import org.springframework.stereotype.Service;

@Service
public class PaymentFormValidationService
{
    public String validateAddress(String address)
    {
        if(address == null || address.length() == 0)
        {
            return "Musisz wpisac swoj adres";
        }
        if(address.length() > 100)
        {
            return "Podaleś za sługi adres zamieszkania";
        }
        else
        {
            return null;
        }
    }

    public String validatePESEL(String PESEL)
    {
        if(!PESEL.matches("^\\d{11}$"))
        {
            return "Blędna ilosc znakow w peselu";
        }
        else
        {
            return null;
        }
    }

    public String validateDescription(String description)
    {
        if(description == null && description.length() == 0)
        {
            return "Musisz wpisać tytuł transakcji";
        }
        if(description.length() > 200)
        {
            return "Zbyt długi tytuł transakcji";
        }
        else
        {
            return null;
        }
    }
}
