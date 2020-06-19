package com.example.carshowroom.Controllers;

import com.example.carshowroom.Data.CarDTO;
import com.example.carshowroom.Data.CarFilters;
import com.example.carshowroom.Services.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/car")
public class CarController
{
    CarService carService;

    public CarController(CarService carService)
    {
        this.carService = carService;
    }

    @GetMapping("/get/all")
    public List<CarDTO> getAllCars()
    {
        return carService.getAllCars();
    }

    @GetMapping("/get/{id}")
    public CarDTO getSpecificCar(@PathVariable String id)
    {
        return carService.getCar(id);
    }


    @GetMapping("/get/cars")
    public List<CarDTO> getOrderedCars(@RequestParam String field, @RequestParam String order)
    {
        return carService.getOrderedCars(field, order);
    }
}
