package com.hongri.androidipc.bean;

import java.io.Serializable;

/**
 * @author zhongyao
 * @date 2018/6/1
 */

public class User implements Serializable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
