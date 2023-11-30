package com.example.user.services;


import com.example.user.Entities.Customer;
import com.example.user.dto.SignUpRequest;
import com.example.user.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSericeImpl implements  AuthService {

   private  final CustomerRepository customerRepository;

   private  final PasswordEncoder passwordEncoder;

   @Autowired
    public AuthSericeImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean createCustomer(SignUpRequest signUpRequest) {

       if(customerRepository.existsByEmail(signUpRequest.getEmail())){

           return  false;

       }
        Customer customer = new Customer();

        BeanUtils.copyProperties(signUpRequest, customer);

       String HashPassword= passwordEncoder.encode(signUpRequest.getPassword());
       customer.setPassword(HashPassword);

       customerRepository.save(customer);
       return  true;
    }
}
