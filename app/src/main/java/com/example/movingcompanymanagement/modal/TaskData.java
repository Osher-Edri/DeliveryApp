package com.example.movingcompanymanagement.modal;


import java.io.Serializable;

public class TaskData implements Serializable {

    private String task_id;
    private String order_date;
    private String task_date;
    private String address;//destination address
    private String contact_name;
    private String contact_phone;
    private String driver;
    private String submit_by_user;
    private String status;
    private String order_note;//order details
    private String driver_note;
    private String originAddress;
    private String area;
    private int taskDay;
    private int taskMonth;
    private int taskYear;

    public TaskData() {
    }

    public void setTaskDay(int taskDay) {
        this.taskDay = taskDay;
    }

    public void setTaskMonth(int taskMonth) {
        this.taskMonth = taskMonth;
    }

    public void setTaskYear(int taskYear) {
        this.taskYear = taskYear;
    }

    public int getTaskMonth() {
        return taskMonth;
    }

    public int getTaskYear() {
        return taskYear;
    }

    public int getTaskDay() {
        return taskDay;
    }

    public String getTask_id() {
        return task_id;
    }

//    public void setTask_id(String task_id) {
//        this.task_id = task_id;
//    }
//
//    public String getOrder_date() {
//        return order_date;
//    }
//
//    public void setOrder_date(String order_date) {
//        this.order_date = order_date;
//    }

    public String getTask_date() {
        return task_date;
    }

    public void setTask_date(String task_date) {
        this.task_date = task_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getRegion() {
//        return region;
//    }
//
//    public void setRegion(String region) {
//        this.region = region;
//    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String order_note) {
        this.order_note = order_note;
    }

    public String getDriver_note() {
        return driver_note;
    }

    public void setDriver_note(String driver_note) {
        this.driver_note = driver_note;
    }


    public String getSubmit_by_user() {  return submit_by_user;  }

    public void setSubmit_by_user(String submit_by_user) { this.submit_by_user = submit_by_user;  }


    @Override
    public String toString() {
        return "TaskData{" +
                "task_id='" + task_id + '\'' +
                ", order_date=" + order_date +
                ", task_date=" + task_date +
                ", address='" + address + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", driver='" + driver + '\'' +
                ", status='" + status + '\'' +
                ", order_note='" + order_note + '\'' +
                ", driver_note='" + driver_note + '\'' +
                '}';
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
