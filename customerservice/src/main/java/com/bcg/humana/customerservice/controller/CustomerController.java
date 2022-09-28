package com.bcg.humana.customerservice.controller;

import com.bcg.humana.customerservice.model.Customer;
import com.bcg.humana.customerservice.model.CustomerResponse;
import com.bcg.humana.customerservice.respository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerRepo customerRepository;

  public CustomerController(CustomerRepo customerRepository) {
    this.customerRepository = customerRepository;
  }
  @RequestMapping("/healthCheck")
  public ResponseEntity<Object> helloWorld() {
    return new ResponseEntity<>("UP", HttpStatus.OK);
  }
  @RequestMapping(path="/{customerId}", method= RequestMethod.GET)
  public ResponseEntity<Object> getCustomer(@PathVariable long customerId) {
    try {
        return new ResponseEntity<>(customerRepository.findById(customerId).orElseThrow(Exception::new), HttpStatus.OK);
    } catch(Exception e){
      return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
    }
  }
}
