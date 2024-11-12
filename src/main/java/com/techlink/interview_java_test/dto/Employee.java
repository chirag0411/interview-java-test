package com.techlink.interview_java_test.dto;

public class Employee {
    private int id;
    private String name;
    private double salary;
    private int departmentId;
    private User user;

    public Employee(int id, String name, double salary, int departmentId, User user) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.departmentId = departmentId;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                ", user=" + user +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
