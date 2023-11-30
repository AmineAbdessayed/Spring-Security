package com.example.user.Controllers;


import com.example.user.dto.SignUpRequest;
import com.example.user.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")

public class SignUpController {

    private  final AuthService authService;

@Autowired
    public SignUpController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")

    public ResponseEntity<String> SignUpCustomer(@RequestBody SignUpRequest signUpRequest){

        boolean isUserCreated = authService.createCustomer(signUpRequest);

        if(isUserCreated){
            return  ResponseEntity.status(HttpStatus.CREATED).body("Custommer Created Succefuly");
        }
        else {

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to created Custommer");
        }
    }


}
