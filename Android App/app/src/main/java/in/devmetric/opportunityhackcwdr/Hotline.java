package in.devmetric.opportunityhackcwdr;

/**
 * Created by Satyam on 12/11/2016.
 */

public class Hotline {
    String name;
    String number;
    String location;

    public Hotline(String name, String number, String location) {
        this.name = name;
        this.number = number;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
