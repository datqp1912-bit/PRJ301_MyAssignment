    package model;

    public class User {
        private int userID; 
        private String username;
        private String password;
        private String name;
        private String email;
        private String phone;
        private int depID;
        private int roleID;
        private boolean active;
        private String depName;
        private String roleName;

        public User(int userID, String username, String password, String name, String email, String phone, int depID, int roleID, boolean active) {
            this.userID = userID;
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.depID = depID;
            this.roleID = roleID;
            this.active = active;
        }



         public User(int userID,  String name, String email, String phone, int depID, int roleID, boolean active) {
            this.userID = userID;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.depID = depID;
            this.roleID = roleID;
            this.active = active;
        }

        public User(int userID, String username, String name, String email, String phone, boolean active, String depName, String roleName) {
            this.userID = userID;
            this.username = username;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.active = active;
            this.depName = depName;
            this.roleName = roleName;
        }

        public User() {
        }

        public String getDepName() {
            return depName;
        }

        public void setDepName(String depName) {
            this.depName = depName;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getDepID() {
            return depID;
        }

        public void setDepID(int depID) {
            this.depID = depID;
        }

        public int getRoleID() {
            return roleID;
        }

        public void setRoleID(int roleID) {
            this.roleID = roleID;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        public String toString() {
            return "User{" + "userID=" + userID + ", username=" + username + ", password=" + password + ", name=" + name + ", email=" + email + ", phone=" + phone + ", depID=" + depID + ", roleID=" + roleID + ", active=" + active + '}';
        }






    }