package com.example.user.Controllers;


import com.example.user.dto.loginRequest;
import com.example.user.dto.loginResponse;
import com.example.user.services.jwt.CustomerServiceimpl;
import com.example.user.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")

public class LoginController {

  private  final AuthenticationManager authenticationManager;


  private  final CustomerServiceimpl customerServiceimpl;
  private  final JwtUtil jwtUtil;

  @Autowired
    public LoginController(AuthenticationManager authenticationManager, CustomerServiceimpl customerServiceimpl, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerServiceimpl = customerServiceimpl;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest loginRequest){

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch ( AuthenticationException e) {

            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails;
        try {

            userDetails= customerServiceimpl.loadUserByUsername(loginRequest.getEmail());

        } catch ( UsernameNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return  ResponseEntity.ok(new loginResponse(jwt));



    }
}
