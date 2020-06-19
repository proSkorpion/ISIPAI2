package com.example.carshowroom.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth2")
public class Oauth2Controller
{
    @GetMapping("/unauthorized")
    public ResponseEntity<?> unauthorizeConnection()
    {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
