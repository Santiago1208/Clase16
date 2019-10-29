package appmoviles.com.preclase13.model.entity;

public class User {

    private String uid;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String birthday;
    private long birthdayTimestamp;
    private String password;


    public User() {
    }

    public User(String uid, String name, String email, String username, String phone, String birthday, long birthdayTimestamp, String password) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.birthday = birthday;
        this.birthdayTimestamp = birthdayTimestamp;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public long getBirthdayTimestamp() {
        return birthdayTimestamp;
    }

    public void setBirthdayTimestamp(long birthdayTimestamp) {
        this.birthdayTimestamp = birthdayTimestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
