package com.example.carshowroom.Services;

import com.example.carshowroom.Data.CarDTO;
import com.example.carshowroom.Database.*;
import com.example.carshowroom.Repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.InvalidParameterException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class CarServiceTest
{
    @Autowired
    private CarRepo carRepo;

    @Autowired
    private ColourRepo colourRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private CarMakeRepo carMakeRepo;

    @Autowired
    private EngineRepo engineRepo;

    @Autowired
    private TypeRepo typeRepo;

    @Autowired
    private PaymentFormRepo paymentFormRepo;

    @Autowired
    private PlaceRepo placeRepo;

    @Autowired
    private FuelRepo fuelRepo;

    @Autowired
    private GearboxRepo gearboxRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CarService carService;

    private Car carInDb;

    @BeforeEach
    private void setUp()
    {
        CarMake carMake = new CarMake();
        carMakeRepo.save(carMake);
        Type type = new Type();
        typeRepo.save(type);
        Colour colour = new Colour();
        colourRepo.save(colour);
        Gearbox gearbox = new Gearbox("manual");
        gearboxRepo.save(gearbox);
        Fuel fuel = new Fuel("diesel");
        fuelRepo.save(fuel);
        Engine engine = new Engine();
        engineRepo.save(engine);
        PaymentForm paymentForm = new PaymentForm();
        paymentFormRepo.save(paymentForm);
        Place place = new Place();
        placeRepo.save(place);
        Role role = new Role();
        roleRepo.save(role);
        User user = new User(role);
        userRepo.save(user);
        Invoice invoice = new Invoice(paymentForm, place, user);
        invoiceRepo.save(invoice);

        carInDb = new Car(carMake, type, colour, engine, invoice, "SDSA13231231", gearbox, fuel);
        Car car2 = new Car(carMake, type, colour, engine, invoice, "SDSA33656767", gearbox, fuel);
        Car car3 = new Car(carMake, type, colour, engine, invoice, "SDSA788984354", gearbox, fuel);

        carRepo.save(carInDb);
        carRepo.save(car2);
        carRepo.save(car3);
    }

    @AfterEach
    private void clear()
    {
        carMakeRepo.deleteAll();
        roleRepo.deleteAll();
        typeRepo.deleteAll();
        colourRepo.deleteAll();
        engineRepo.deleteAll();
        placeRepo.deleteAll();
        paymentFormRepo.deleteAll();
        userRepo.deleteAll();
        invoiceRepo.deleteAll();
        carRepo.deleteAll();
    }

    @Test
    void should_get_all_cars()
    {
        //given
        List<CarDTO> allCars = carService.getAllCars();
        //then
        Assertions.assertNotNull(allCars);
    }

    @Test
    void should_get_car()
    {
        //given
        CarDTO car = carService.getCar(carInDb.getCarId().toString());
        //then
        Assertions.assertNotNull(car);
    }

    @Test
    void should_not_get_car()
    {
        //given
        CarDTO car = carService.getCar("-1");
        //then
        Assertions.assertEquals(car, null);
    }

    @Test
    void should_get_ordered_cars()
    {
        //given
        String field = "price";
        String order = "desc";
        //when
        List<CarDTO> orderedCars = carService.getOrderedCars(field, order);
        //then
        Assertions.assertNotNull(orderedCars);
    }

    @Test
    void should_not_get_ordered_cars()
    {
        //given
        String field = "price";
        String order = "halo";
        //then
        Assertions.assertThrows(InvalidParameterException.class, () -> carService.getOrderedCars(field, order));
    }

}