package co.com.sofka.hexagonal.infraestructure.inputadapter;

import co.com.sofka.hexagonal.domain.Order;
import co.com.sofka.hexagonal.infraestructure.inputport.OrderInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "order")
public class OrdersApi {

    @Autowired
    OrderInputPort orderInputPort;

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order create(@RequestParam String customerId, @RequestParam BigDecimal total){
        return orderInputPort.createOrder(customerId, total);
    }
}
