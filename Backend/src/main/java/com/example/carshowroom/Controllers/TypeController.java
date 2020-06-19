package com.example.carshowroom.Controllers;

import com.example.carshowroom.Data.TypeDTO;
import com.example.carshowroom.Services.TypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/type")
public class TypeController
{

    private TypeService typeService;

    public TypeController(TypeService typeService)
    {
        this.typeService = typeService;
    }

    @GetMapping("get/all")
    public List<TypeDTO> getAllCarMarks()
    {
        return typeService.getAllCarMakes();
    }
}
