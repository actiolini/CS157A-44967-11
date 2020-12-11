package moviebuddy.model;

public class Theatre {
    private int id;
    private String theatreName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;

    public Theatre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
