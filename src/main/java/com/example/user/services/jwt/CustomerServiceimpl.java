package com.example.user.services.jwt;

import com.example.user.Entities.Customer;
import com.example.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomerServiceimpl  implements UserDetailsService {

    private  final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceimpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow (()-> new UsernameNotFoundException("customer not found with email " + email));

        return  new User(customer.getEmail(), customer.getPassword(), Collections.emptyList());
    }

}
