package com.cas.设计模式.命令模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/1/14 11:35 上午
 * @desc
 */
public class BuyOrder implements Order{

    private Stock stock;

    BuyOrder(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void exec() {
        stock.buy();
    }
}
