package co.com.sofka.hexagonal.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Builder
@Setter
@Getter
public class Order {
    private String id;
    private String customerId;
    private BigDecimal total;
}
