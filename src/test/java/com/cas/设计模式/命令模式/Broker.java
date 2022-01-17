package com.cas.设计模式.命令模式;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/14 11:52 上午
 * @desc
 */
public class Broker {

    private List<Order> orderList = new ArrayList<>();

    public void takeOrder(Order order) {
        orderList.add(order);
    }

    public void placeOrders() {
        orderList.forEach(Order::exec);
        orderList.clear();
    }

}
