package model;

public class Material extends Component {
    private double unit_cost;         // Coût unitaire
    private double quantity;          // Quantité
    private double transport_cost;    // Coût de transport
    private double coefficient_quality; // Coefficient de qualité

    // Constructeur
    public Material(int id, String name, double vat_rate, double unit_cost, double quantity, double transport_cost, double coefficient_quality, int project_id) {
        super(id, name, "Material", vat_rate, project_id);
        this.unit_cost = unit_cost;
        this.quantity = quantity;
        this.transport_cost = transport_cost;
        this.coefficient_quality = coefficient_quality;
    }


    // Getters et Setters
    public double getCoefficient_quality() {
        return coefficient_quality;
    }

    public void setCoefficient_quality(double coefficient_quality) {
        this.coefficient_quality = coefficient_quality;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTransport_cost() {
        return transport_cost;
    }

    public void setTransport_cost(double transport_cost) {
        this.transport_cost = transport_cost;
    }

    public double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

    // Méthode toString pour afficher les détails du matériau
    @Override
    public String toString() {
        return "Material {" +
                "  name='" + super.getName() + "'" +
                ", type_component='" + super.getType_component() + "'" +
                ", vat_rate=" + super.getVat_rate() + "'" +
                ", coefficient_quality=" + coefficient_quality + "'" +
                ", unit_cost=" + unit_cost + "'" +
                ", quantity=" + quantity + "'" +
                ", transport_cost=" + transport_cost + "'" +
                '}';
    }
}
