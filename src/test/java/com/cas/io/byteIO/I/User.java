package com.cas.io.byteIO.I;

import java.io.Serializable;

public class User implements Serializable {

    private String name;

    private String age;

    private String b;
    private String ci;
    private String dpp;
    private String eoo;

    public User() {}

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDpp() {
        return dpp;
    }

    public void setDpp(String dpp) {
        this.dpp = dpp;
    }

    public String getEoo() {
        return eoo;
    }

    public void setEoo(String eoo) {
        this.eoo = eoo;
    }

    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static User getUser() {
        return new User("xl", "24");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final User user = (User) obj;
        if (this == user) {
            return true;
        } else {
            return this.age.equals(user.getAge()) && this.name.equals(user.getName());
        }
    }

    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (name == null ? 0 : name.hashCode());
        return hashno;
    }
}
