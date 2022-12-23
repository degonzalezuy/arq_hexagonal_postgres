package co.com.sofka.hexagonal.application;

import co.com.sofka.hexagonal.domain.Customer;
import co.com.sofka.hexagonal.infraestructure.inputport.CustomerInputPort;
import co.com.sofka.hexagonal.infraestructure.outputport.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class CustomerUseCase implements CustomerInputPort {

    @Autowired
    EntityRepository entityRepository;

    @Override
    public Customer createCustomer(String name, String country){
        Customer customer = Customer.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .country(country)
                .build();
        return entityRepository.save(customer);
    }

    @Override
    public Customer getById(String customerId){
        return entityRepository.getById(customerId, Customer.class);
    }

    @Override
    public List<Customer> getAll(){
        return entityRepository.getAll(Customer.class);
    }

}
