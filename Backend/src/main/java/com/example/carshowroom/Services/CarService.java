package com.example.carshowroom.Services;

import com.example.carshowroom.Data.CarDTO;
import com.example.carshowroom.Database.Car;
import com.example.carshowroom.Repositories.CarRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CarService
{
    private CarRepo carRepo;

    public CarService(CarRepo carRepo)
    {
        this.carRepo = carRepo;
    }

    public List<CarDTO> getAllCars()
    {
        List<Car> allCars = carRepo.findAll();
        ModelMapper modelMapper = getModelMapper();
        List<CarDTO> allCarsDTO = Arrays.asList(modelMapper.map(allCars, CarDTO[].class));
        return allCarsDTO;
    }


    private ModelMapper getModelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Car, CarDTO>() {

            @Override
            protected void configure() {
                map().setCarMake(source.getCarMake().getName());
                map().setModel(source.getModel());
                map().setColour(source.getColour().getName());
                map().setFuel(source.getFuel().getName());
                map().setGearbox(source.getGearbox().getName());
                map().setType(source.getType().getName());
                map().setEngineCapacity(source.getEngine().getCapacity());
            }
        });
        return modelMapper;
    }


    public CarDTO getCar(String id)
    {
        ModelMapper modelMapper = getModelMapper();
        Optional<Car> car = carRepo.findById(Long.parseLong(id));
        if(car.isPresent())
        {
            CarDTO carDTO = modelMapper.map(car.get(), CarDTO.class);
            return carDTO;
        }
        return null;
    }

    public List<CarDTO> getOrderedCars(String field, String order)
    {
        ModelMapper modelMapper = getModelMapper();
        List<Car> cars = getCarsFromDatabase(field, order);
        List<CarDTO> carDTOList = Arrays.asList(modelMapper.map(cars, CarDTO[].class));
        return carDTOList;
    }

    private List<Car> getCarsFromDatabase(String field, String order)
    {
        switch(order)
        {
            case "asc":
            {
                return carRepo.findAll(Sort.by(Sort.Direction.ASC, field));
            }
            case  "desc":
            {
                return carRepo.findAll(Sort.by(Sort.Direction.DESC, field));
            }
        }
        throw new InvalidParameterException("Nieprawidlowa nazwa sortowania");
    }
}
