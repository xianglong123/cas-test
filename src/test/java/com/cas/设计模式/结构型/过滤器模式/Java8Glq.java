package com.cas.设计模式.结构型.过滤器模式;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/4/20 2:05 下午
 * @desc
 */
public class Java8Glq {

    class Person {

        private String name;
        private String gender;
        private String maritalStatus;

        public Person(String name,String gender,String maritalStatus){
            this.name = name;
            this.gender = gender;
            this.maritalStatus = maritalStatus;
        }

        public String getName() {
            return name;
        }
        public String getGender() {
            return gender;
        }
        public String getMaritalStatus() {
            return maritalStatus;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", maritalStatus='" + maritalStatus + '\'' +
                    '}';
        }
    }


    /**
     * java8 过滤器模式
     */
    @Test
    public void test() {
        List<Person> persons = new ArrayList<Person>();

        persons.add(new Person("Robert","Male", "Single"));
        persons.add(new Person("John","Male", "Married"));
        persons.add(new Person("Laura","Female", "Married"));
        persons.add(new Person("Diana","Female", "Single"));
        persons.add(new Person("Mike","Male", "Single"));
        persons.add(new Person("Bobby","Male", "Single"));


        List<Person> male = persons.stream().filter(person -> person.getGender().equals("Male")).collect(Collectors.toList());
        System.out.println("Male:");
        male.forEach(System.out::println);

        List<Person> female = persons.stream().filter(person -> person.getGender().equals("Female")).collect(Collectors.toList());
        System.out.println("Female:");
        female.forEach(System.out::println);

        List<Person> ForS = persons.stream().filter(person -> person.getGender().equals("Female") || person.getMaritalStatus().equals("Single")).collect(Collectors.toList());
        System.out.println("ForS:");
        ForS.forEach(System.out::println);
    }

}
