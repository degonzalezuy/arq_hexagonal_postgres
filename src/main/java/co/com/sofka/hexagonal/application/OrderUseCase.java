package co.com.sofka.hexagonal.application;

import co.com.sofka.hexagonal.domain.Order;
import co.com.sofka.hexagonal.infraestructure.inputport.OrderInputPort;
import co.com.sofka.hexagonal.infraestructure.outputport.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;


@Component
public class OrderUseCase implements OrderInputPort {
    @Autowired
    EntityRepository entityRepository;

    @Override
    public Order createOrder(String customerId, BigDecimal total){
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .customerId(customerId)
                .total(total)
                .build();

        return entityRepository.save(order);
    }
}
