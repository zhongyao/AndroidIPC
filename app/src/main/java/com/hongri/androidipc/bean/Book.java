package com.hongri.androidipc.bean;

/**
 * Created by zhongyao on 2018/6/11.
 */

public class Book {

    private int _id;
    private String name;
    private int page;

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Book{" +
            "_id=" + _id +
            ", name='" + name + '\'' +
            ", page=" + page +
            '}';
    }

}
