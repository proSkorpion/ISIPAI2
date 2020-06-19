package com.example.carshowroom.Services;

import com.example.carshowroom.Data.OrderDetails;
import com.example.carshowroom.Data.PaymentErrors;
import com.example.carshowroom.Database.*;
import com.example.carshowroom.Repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService
{
    private UserRepo userRepo;
    private PaymentFormRepo paymentFormRepo;
    private PlaceRepo placeRepo;
    private CarRepo carRepo;
    private InvoiceRepo invoiceRepo;
    private PaymentFormValidationService paymentFormValidationService;

    public InvoiceService(UserRepo userRepo, PaymentFormRepo paymentFormRepo, PlaceRepo placeRepo,
                          CarRepo carRepo, InvoiceRepo invoiceRepo, PaymentFormValidationService paymentFormValidationService)
    {
        this.userRepo = userRepo;
        this.paymentFormRepo = paymentFormRepo;
        this.placeRepo = placeRepo;
        this.carRepo = carRepo;
        this.invoiceRepo = invoiceRepo;
        this.paymentFormValidationService = paymentFormValidationService;
    }


    public PaymentErrors validateFormData(OrderDetails orderDetails)
    {
        PaymentErrors paymentErrors = new PaymentErrors();
        paymentErrors.setAddressError(paymentFormValidationService.validateAddress(orderDetails.getAddress()));
        paymentErrors.setPESELError(paymentFormValidationService.validatePESEL(orderDetails.getPesel()));
        paymentErrors.setDescriptionError(paymentFormValidationService.validateDescription(orderDetails.getDescription()));
        return paymentErrors;
    }

    public Long createInvoice(OrderDetails orderDetails) throws Exception
    {
        Optional<User> user = userRepo.findByUsername(orderDetails.getUsername());
        if (user.isPresent())
        {
            updateUser(orderDetails, user.get());
            Invoice invoice = new Invoice();
            invoice.setDate(LocalDate.now());
            invoice.setUser(user.get());
            invoice.setPrice(Float.parseFloat(orderDetails.getTotal().toString()));

            Optional<PaymentForm> paymentForm = paymentFormRepo.findByName("paypal");
            Optional<Place> place = placeRepo.findById(Long.parseLong("1"));
            if (paymentForm.isPresent() && place.isPresent())
            {
                invoice.setPaymentForm(paymentForm.get());
                invoice.setPlace(place.get());
                Set<Car> cars = prepareCarsToBuy(orderDetails.getCarsId(), invoice);
                invoice.setCars(cars);
                invoiceRepo.save(invoice);
                return invoice.getInvoiceId();
            } else
            {
                throw new Exception("Blad w bazie danych");
            }
        } else
        {
            throw new Exception("Nie ma takiego użytkownika w bazie danych");
        }
    }


    private Set<Car> prepareCarsToBuy(String carsId, Invoice invoice) throws Exception
    {
        Set<Car> cars = new HashSet<>();
        Optional<Car> car = carRepo.findById(Long.parseLong(carsId));
        if (car.isPresent())
        {
            carRepo.save(car.get());
            cars.add(car.get());
        } else
        {
            throw new Exception("Blad w zamownieniu. Nie ma takiego auta");
        }
        return cars;
}


    @Transactional
    public void cancelInvoice(Long invoiceId)
    {
        Optional<Set<Car>> cars = carRepo.findByInvoice_InvoiceId(invoiceId);
        for (Car car : cars.get())
        {
            car.setInvoice(null);
            carRepo.save(car);
        }
        invoiceRepo.deleteById(invoiceId);
    }

    private void updateUser(OrderDetails orderDetails, User user)
    {
        user.setAddress(orderDetails.getAddress());
        user.setPESEL(Long.parseLong(orderDetails.getPesel()));
        userRepo.save(user);
    }
}
