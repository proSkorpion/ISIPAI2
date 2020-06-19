package com.example.carshowroom.Data;

public class PaymentErrors
{
    private String addressError;
    private String PESELError;
    private String descriptionError;

    public PaymentErrors()
    {
    }

    public String getAddressError()
    {
        return addressError;
    }

    public void setAddressError(String addressError)
    {
        this.addressError = addressError;
    }

    public String getPESELError()
    {
        return PESELError;
    }

    public void setPESELError(String PESELError)
    {
        this.PESELError = PESELError;
    }

    public String getDescriptionError()
    {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError)
    {
        this.descriptionError = descriptionError;
    }

    public boolean isErrorsEmpty()
    {
        if(getAddressError() == null &&
            getPESELError() == null &&
            getDescriptionError() == null)
            return  true;

        else
            return false;
    }
}
