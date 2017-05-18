public class Client {
    private String username;
    private String password;
    private String address;
    private String postcode;

    public Client(String username, String password, String address, String postcode) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.postcode = postcode;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }
}
