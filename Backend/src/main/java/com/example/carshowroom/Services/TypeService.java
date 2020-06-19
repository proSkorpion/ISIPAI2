package com.example.carshowroom.Services;

import com.example.carshowroom.Data.TypeDTO;
import com.example.carshowroom.Database.CarMake;
import com.example.carshowroom.Database.Type;
import com.example.carshowroom.Repositories.TypeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TypeService
{
    private TypeRepo typeRepo;

    public TypeService(TypeRepo typeRepo)
    {
        this.typeRepo = typeRepo;
    }

    public List<TypeDTO> getAllCarMakes()
    {
        ModelMapper modelMapper = new ModelMapper();
        List<Type> carMakes = typeRepo.findAll();
        List<TypeDTO> carMakeDTOList = Arrays.asList(modelMapper.map(carMakes, TypeDTO[].class));
        return carMakeDTOList;
    }
}
