package com.example.kodulf.utilsdemo.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Kodulf on 2017/4/30.
 */

public class Song extends DataSupport {


    public Song(){

    }
    public Song(String name, int length) {
        this.name = name;
        this.length = length;
    }

    @Column(nullable = false)
    private String name;

    private int length;

    @Column(ignore = true)
    private String userField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUserField() {
        return userField;
    }

    public void setUserField(String userField) {
        this.userField = userField;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
