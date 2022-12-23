package co.com.sofka.hexagonal.infraestructure.inputport;

import co.com.sofka.hexagonal.domain.Customer;

import java.util.List;

public interface CustomerInputPort {
    public Customer createCustomer(String name, String country);
    public Customer getById(String customerId);
    public List<Customer> getAll();
}
