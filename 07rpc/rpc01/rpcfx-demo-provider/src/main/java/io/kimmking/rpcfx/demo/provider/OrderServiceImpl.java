package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;

import java.lang.reflect.InvocationTargetException;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        if(id == 0 ) {
            throw new RuntimeException("xxx");
        }
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
