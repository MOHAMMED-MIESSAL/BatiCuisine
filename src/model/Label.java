package model;

public class Label extends Component {
    private double hourly_rate;
    private double hours_work;
    private double worker_productivity;

    public Label(int id, String name, int project_id, double vat_rate, double hourly_rate, double hours_work, double worker_productivity) {
        super(id, name, project_id, "Label", vat_rate);
        this.hourly_rate = hourly_rate;
        this.hours_work = hours_work;
        this.worker_productivity = worker_productivity;
    }

    // Getters and Setters

    public double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public double getHours_work() {
        return hours_work;
    }

    public void setHours_work(double hours_work) {
        this.hours_work = hours_work;
    }

    public double getWorker_productivity() {
        return worker_productivity;
    }

    public void setWorker_productivity(double worker_productivity) {
        this.worker_productivity = worker_productivity;
    }

    @Override
    public String toString() {
        return "Label{" +
                " Name ='" + super.getName() + "'" +
                ", Type Component='" + super.getType_component() + "'" +
                ", VAT Rate (TVA)='" + super.getVat_rate() + "'" +
                ", Hourly Rate='" + hourly_rate + "'" +
                ", Hours Work='" + hours_work + "'" +
                ", Worker Productivity='" + worker_productivity + "'" +
                '}';
    }
}
