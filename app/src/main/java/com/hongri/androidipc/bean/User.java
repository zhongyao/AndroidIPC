package com.hongri.androidipc.bean;

import java.io.Serializable;

/**
 * @author zhongyao
 * @date 2018/6/1
 */

public class User implements Serializable {

    private int _id;

    private String name;
    private int age;
    private int sex;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
            "_id=" + _id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", sex=" + sex +
            '}';
    }
}
