package com.hongri.androidipc.test;

import java.util.ArrayList;

/**
 * @author zhongyao
 * @date 2018/8/1
 */

public class TestClass {
    public void TestClas() {
        ArrayList<InterfaceA> list = new ArrayList<>();
        list.add(new ClassA());
        list.add(new ClassB());
    }
}
