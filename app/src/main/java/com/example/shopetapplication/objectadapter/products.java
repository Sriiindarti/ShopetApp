package com.example.shopetapplication.objectadapter;

public class products {
    String prod_id;
    String prod_name;
    String prod_price;
    String prod_type;
    String prod_stock;

    //Constructor Kosong
    public products(){

    }

    //Contructor
    public products(String prod_id, String prod_name, String prod_price, String prod_type, String prod_stock) {
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_type = prod_type;
        this.prod_stock = prod_stock;
    }

    //Getter Setter, untuk mendapatkan dan menggambil data yang telah diinput
    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_type() {
        return prod_type;
    }

    public void setProd_type(String prod_type) {
        this.prod_type = prod_type;
    }

    public String getProd_stock() {
        return prod_stock;
    }

    public void setProd_stock(String prod_stock) {
        this.prod_stock = prod_stock;
    }


}
