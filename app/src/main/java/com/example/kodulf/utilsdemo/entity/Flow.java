package com.example.kodulf.utilsdemo.entity;

import java.io.Serializable;

/**
 * Created by Kodulf on 2017/5/15.
 */

public class Flow implements Serializable {

    private int id;
    private String p;
    private String v;
    private double inprice;

    @Override
    public String toString() {
        return "Flow{" +
                "id=" + id +
                ", p='" + p + '\'' +
                ", v='" + v + '\'' +
                ", inprice=" + inprice +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public double getInprice() {
        return inprice;
    }

    public void setInprice(double inprice) {
        this.inprice = inprice;
    }
}
