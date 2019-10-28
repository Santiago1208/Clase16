package appmoviles.com.preclase13.model.entity;

public class User {

    private String uid;
    private String email;
    private String password;
    private String name;
    private String username;
    private String birth;
    private long birthTimestamp;


    public User(){}

    public User(String uid, String email, String password, String name, String username, String birth, long birthTimestamp) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.username = username;
        this.birth = birth;
        this.birthTimestamp = birthTimestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public long getBirthTimestamp() {
        return birthTimestamp;
    }

    public void setBirthTimestamp(long birthTimestamp) {
        this.birthTimestamp = birthTimestamp;
    }
}
