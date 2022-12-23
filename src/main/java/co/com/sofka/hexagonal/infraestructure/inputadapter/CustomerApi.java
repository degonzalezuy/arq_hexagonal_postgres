package co.com.sofka.hexagonal.infraestructure.inputadapter;

import co.com.sofka.hexagonal.domain.Customer;
import co.com.sofka.hexagonal.infraestructure.inputport.CustomerInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping(value = "customer")

public class CustomerApi {

    @Autowired
    CustomerInputPort customerInputPort;

    /*@PostMapping(value="create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestParam String name, @RequestParam String country){
        return customerInputPort.createCustomer(name, country);
    }

    @PostMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer get(@RequestParam String customerId){
        return customerInputPort.getById(customerId);
    }

    @PostMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAll(){
        return customerInputPort.getAll();
    }*/

    @GetMapping
    public ResponseEntity<List<Customer>>findAll(){
        return ResponseEntity.ok().body(customerInputPort.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") String id){
        return ResponseEntity.ok().body(customerInputPort.getById(id));
    }

    @PostMapping(value="create")
    public ResponseEntity<Customer> create(@RequestParam String name, @RequestParam String country){
        return ResponseEntity.ok().body(customerInputPort.createCustomer(name, country));
    }



}
