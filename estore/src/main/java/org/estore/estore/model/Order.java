package org.estore.estore.model;

import jakarta.persistence.*;
import lombok.*;
import org.estore.estore.dto.request.CreateOrderRequest;

@Entity
@Getter
@Setter
@Table(name = "orders")
@ToString
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private String cartId;

    public Order(CreateOrderRequest orderRequest) {
        this.cartId = orderRequest.getCartId();
    }
}
