package com.xuhuawei.linkedadapter;

/*
 * User: xuhuawei
 * Date: 2023/2/16
 * Desc:
 */
public class LinkedBean {
    public int position;
    private int[] testArray;
    private static final int arraySize = 1;

    public LinkedBean(int position) {
        this.position = position;
        testArray = new int[arraySize];
    }
}
