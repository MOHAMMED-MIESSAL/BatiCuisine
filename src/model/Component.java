package model;

public class Component {
    private int id;
    private String name;
    private String type_component; // Material || manPower
    private double vat_rate;
    private int project_id;

    public Component(int id, String name, int project_id, String type_component, double vat_rate) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
        this.type_component = type_component;
        this.vat_rate = vat_rate;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getProject_id() {
        return project_id;
    }
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getType_component() {
        return type_component;
    }
    public void setType_component(String type_component) {
        this.type_component = type_component;
    }

    public double getVat_rate() {
        return vat_rate;
    }
    public void setVat_rate(double vat_rate) {
        this.vat_rate = vat_rate;
    }

    // toString
    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type_component='" + type_component + '\'' +
                ", vat_rate=" + vat_rate +
                ", project_id=" + project_id +
                '}';
    }
}
