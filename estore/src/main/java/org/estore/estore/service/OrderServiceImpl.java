package org.estore.estore.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.estore.estore.dto.request.CreateOrderRequest;
import org.estore.estore.dto.request.ItemRequest;
import org.estore.estore.dto.response.CreateOrderResponse;
import org.estore.estore.exception.ItemOutOfStockException;
import org.estore.estore.model.Item;
import org.estore.estore.model.Order;
import org.estore.estore.model.Product;
import org.estore.estore.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductService productService;
    private final ItemService itemService;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    public CreateOrderResponse create(CreateOrderRequest order) {
        order.getItems().forEach(this::checkAvailabilityOf);
        return buildCreateOrderResponseFrom(orderRepository.save(new Order(order)));
    }

    private void checkAvailabilityOf(ItemRequest item) {
        //1. check to see that the items are in-stock
        Item foundItem = itemService.getItemBy(item.getId());
        Product product = productService.getProductBy(foundItem.getProductId());
        if (item.getQuantity() > product.getQuantity())
            throw new ItemOutOfStockException("item out of stock");
    }

    private static CreateOrderResponse buildCreateOrderResponseFrom(Order customerOrder) {
        //if all items are in stock, create order
        var orderResponse =  new CreateOrderResponse();
        orderResponse.setOrderStatus("IN_PROGRESS");
        orderResponse.setOrderId(customerOrder.getId());
        orderResponse.setMessage("Order placed Successfully");
        return orderResponse;
    }

    @Override
    public String cancelOrder(String orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        if ("IN_PROGRESS".equals(order.getOrderStatus())){
            order.setOrderStatus("CANCELED");
            orderRepository.save(order);
            return "Order" + orderId + "has been cancelled";
        }
        else{
            return "Order" + orderId + "is unable to be cancelled" + order.getOrderStatus();
        }
    }

}
