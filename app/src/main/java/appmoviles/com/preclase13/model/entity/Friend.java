package appmoviles.com.preclase13.model.entity;

public class Friend {

    private String uid;
    private String name;
    private String username;
    private String phone;
    private String email;


    public Friend() {
    }

    public Friend(String uid, String name, String username, String phone, String email) {
        this.uid = uid;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return username;
    }
}
