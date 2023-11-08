package btl.n01.quanlibangiay.model;

public class Users {
    private String id;
    private String name;
    private String email;
    private String pass;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public Users(String id, String name, String email, String pass, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.address = address;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
