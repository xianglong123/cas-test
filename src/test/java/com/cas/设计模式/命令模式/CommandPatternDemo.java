package com.cas.设计模式.命令模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/14 11:47 上午
 * @desc 命令模式：其实一早就知道要做什么，中间
 */
public class CommandPatternDemo {

    public static void main(String[] args) {
        Stock stock = new Stock();
        BuyOrder buyOrder = new BuyOrder(stock);
        SellOrder sellOrder = new SellOrder(stock);

        Broker broker = new Broker();
        broker.takeOrder(buyOrder);
        broker.takeOrder(sellOrder);

        broker.placeOrders();
    }

}
