package model;


public class Client {
    private int id;
    private String name;
    private String address;
    private String phone;
    private boolean is_professional;


    public Client(int id, String name, String address, String phone, boolean is_professional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.is_professional = is_professional;
    }

    // Getters et setters
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_professional() {
        return is_professional;
    }
    public void setIs_professional(boolean is_professional) {
        this.is_professional = is_professional;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }



    // to string
    @Override
    public String toString() {
        return "Client {id=" + id + ", name='" + name + '\'' +
                ", address='" + address + '\'' + ", phone='" +
                phone + '\'' + ", is_professional=" +
                is_professional + '}';
    }
}

