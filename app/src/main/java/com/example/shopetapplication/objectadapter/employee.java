package com.example.shopetapplication.objectadapter;

public class employee {
    String emp_id;
    String emp_name;
    String emp_role;
    String emp_address;
    String emp_nophone;

    public employee(){

    }

    public employee(String emp_id, String emp_name, String emp_role, String emp_address, String emp_nophone) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_role = emp_role;
        this.emp_address = emp_address;
        this.emp_nophone = emp_nophone;
    }


    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_role() {
        return emp_role;
    }

    public void setEmp_role(String emp_role) {
        this.emp_role = emp_role;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public void setEmp_address(String emp_address) {
        this.emp_address = emp_address;
    }

    public String getEmp_nophone() {
        return emp_nophone;
    }

    public void setEmp_nophone(String emp_nophone) {
        this.emp_nophone = emp_nophone;
    }

}
