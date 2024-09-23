package model;

public class Labor extends Component {
    private double hourly_rate;
    private double hours_work;
    private double worker_productivity;

    public Labor(int id, String name, double vat_rate, int project_id, double hourly_rate, double hours_work, double worker_productivity) {
        super(id, name, "Labor", vat_rate, project_id);
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


    // toString
    @Override
    public String toString() {
        return "Label {" +
                "  name ='" + super.getName() + "'" +
                ", type component='" + super.getType_component() + "'" +
                ", vat rate (TVA)='" + super.getVat_rate() + "'" +
                ", hourly rate='" + hourly_rate + "'" +
                ", hours work='" + hours_work + "'" +
                ", worker productivity='" + worker_productivity + "'" +
                '}';
    }
}
