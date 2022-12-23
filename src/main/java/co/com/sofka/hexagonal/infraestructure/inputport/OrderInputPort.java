package co.com.sofka.hexagonal.infraestructure.inputport;

import co.com.sofka.hexagonal.domain.Order;

import java.math.BigDecimal;

public interface OrderInputPort {
    public Order createOrder(String customerId, BigDecimal total);
}
