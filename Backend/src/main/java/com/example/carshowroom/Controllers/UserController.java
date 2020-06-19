package com.example.carshowroom.Controllers;

import com.example.carshowroom.Data.*;
import com.example.carshowroom.Database.User;
import com.example.carshowroom.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController
{
    UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ValidationErrors userRegister(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest authenticationRequest)
    {
        return userService.logInUser(authenticationRequest);
    }

    @PostMapping("/confirm/email")
    public void confirmEmail(@RequestBody User user)
    {
        try
        {
            userService.sendToken(user);
        } catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping("/token")
    public String confirmAccount(@RequestParam String value, HttpServletResponse httpServletResponse)
    {
        //todo pass good url
        userService.confirmAccount(value);
        httpServletResponse.setHeader("Location", "http://localhost:4200/login");
        httpServletResponse.setStatus(302);
        return "redirect:localhost:4200/login";
    }

    @PostMapping("/send/feedback")
    public void sendFeedback(@RequestBody FeedbackData feedbackData)
    {
        userService.sendFeedback(feedbackData);
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest httpServletRequest)
    {
        userService.logoutUser(httpServletRequest);
        return "true";
    }

    @PostMapping("/testDrive")
    public void arrangeTestDrive(@RequestBody MeetingForm meetingForm)
    {
        userService.arrangeTestDrive(meetingForm);
    }


}
