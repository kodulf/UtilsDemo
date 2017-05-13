package com.example.kodulf.utilsdemo.entity;


import java.util.ArrayList;

/**
 * Created by zhangyinshan on 2017/5/3.
 *
 * "city":"全国",
 "company":"中国联通",
 "companytype":"1",
 "name":"中国联通全国流量套餐",
 "type":"1",
 "flows":[
 {
 "id":"34",
 "p":"20M",
 "v":"20",
 "inprice":"2.880"
 },
 */
public class City {
    private String city;
    private String company;
    private int companyType;
    private String name;
    private int type;
    private ArrayList<Flow> flows;

    @Override
    public String toString() {
        return "City{" +
                "city='" + city + '\'' +
                ", company='" + company + '\'' +
                ", companyType=" + companyType +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", flows=" + flows +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Flow> getFlows() {
        return flows;
    }

    public void setFlows(ArrayList<Flow> flows) {
        this.flows = flows;
    }

    class Flow{

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
}
