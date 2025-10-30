package model;

public class User {
    private int userID;
    private String username;
    private String password;
    private String display;
    private boolean active;
    private Employee employee;
    private Role role;
    private Department department;

    public User() {}

    public User(int userID, String username, String password, String display, boolean active, Employee employee, Role role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.display = display;
        this.active = active;
        this.employee = employee;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return username + " (" + role.getRoleName() + ")";
    }
}
