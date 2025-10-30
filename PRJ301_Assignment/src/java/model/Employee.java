package model;

import java.sql.Date;

public class Employee {
    private int empID;
    private String empName;
    private Date dob;
    private String phone;
    private String email;
    private Department department;
    private Employee manager; // Quản lý trực tiếp

    public Employee() {}

    public Employee(int empID, String empName, Date dob, String phone, String email, Department department, Employee manager) {
        this.empID = empID;
        this.empName = empName;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.manager = manager;
    }

    public int getEmpID() {
        return empID;
    }
    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getManager() {
        return manager;
    }
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return empName;
    }
}
