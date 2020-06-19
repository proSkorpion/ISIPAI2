package com.example.carshowroom.Data;


public class ValidationErrors
{
    private String nameError;
    private String surnameError;
    private String emailError;
    private String usernameError;
    private String passwordError;


    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getNameError()
    {
        return nameError;
    }

    public void setNameError(String nameError)
    {
        this.nameError = nameError;
    }

    public String getSurnameError()
    {
        return surnameError;
    }

    public void setSurnameError(String surnameError)
    {
        this.surnameError = surnameError;
    }
}
