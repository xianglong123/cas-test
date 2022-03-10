package com.cas.lb;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WeightRandom<K,V extends Number> {
    private TreeMap<Double, K> WEIGHTMAP = new TreeMap<Double, K>();

    public WeightRandom(List<Pair<K, V>> list) {
        Preconditions.checkNotNull(list, "list can NOT be null!");
        for (Pair<K, V> pair : list) {
            Preconditions.checkArgument(pair.getValue().doubleValue() > 0, String.format("非法权重值：pair=%s", pair));

            double lastWeight = this.WEIGHTMAP.size() == 0 ? 0 : this.WEIGHTMAP.lastKey();//统一转为double
            this.WEIGHTMAP.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());//权重累加
        }
    }

    public K random() {
        double randomWeight = this.WEIGHTMAP.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = this.WEIGHTMAP.tailMap(randomWeight, false);
        return this.WEIGHTMAP.get(tailMap.firstKey());
    }


    public static void main(String[] args) {
        List<Pair<String, Integer>> list = new ArrayList<>();
        list.add(new Pair<>("10.27.0.1" ,2));
        list.add(new Pair<>("10.27.0.2" ,3));
        list.add(new Pair<>("10.27.0.3" ,3));
        WeightRandom<String, Integer> weightRandom = new WeightRandom<>(list);
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000; i ++) {
            String random = weightRandom.random();
            map.put(random, map.get(random) == null ? 0 : map.get(random) + 1);
        }
        Set<String> x = map.keySet();
        for (String i : x) {
            System.out.println(i + " 次数：" + map.get(i));
        }
    }


}