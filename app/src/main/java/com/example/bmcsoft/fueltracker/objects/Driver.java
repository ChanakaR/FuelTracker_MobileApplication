package com.example.bmcsoft.fueltracker.objects;

/**
 * Created by bmCSoft on 2016-05-24.
 */
public class Driver {
    private int id;
    private String f_name;
    private String l_name;
    private String address;
    private String contact_no;
    private String gender;
    private String nic;
    private String driver_id;
    private String driving_licence_no;

    public String getDriving_licence_no() {
        return driving_licence_no;
    }

    public void setDriving_licence_no(String driving_licence_no) {
        this.driving_licence_no = driving_licence_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

}
