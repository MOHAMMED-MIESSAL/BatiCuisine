package model;

import java.sql.Date;

public class Estimate {
    private int id;
    private double amount_estimate;
    private Boolean is_accepted;
    private Date date_emission;
    private Date date_validity;
    private int project_id;

    public Estimate(int id,double amount_estimate, Date date_emission, Date date_validity, Boolean is_accepted, int project_id) {
        this.id = id;
        this.amount_estimate = amount_estimate;
        this.date_emission = date_emission;
        this.date_validity = date_validity;
        this.is_accepted = is_accepted;
        this.project_id = project_id;
    }

    // Getters and Setters

    public double getAmount_estimate() {
        return amount_estimate;
    }
    public void setAmount_estimate(double amount_estimate) {
        this.amount_estimate = amount_estimate;
    }

    public Date getDate_emission() {
        return date_emission;
    }
    public void setDate_emission(Date date_emission) {
        this.date_emission = date_emission;
    }

    public Date getDate_validity() {
        return date_validity;
    }
    public void setDate_validity(Date date_validity) {
        this.date_validity = date_validity;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIs_accepted() {
        return is_accepted;
    }
    public void setIs_accepted(Boolean is_accepted) {
        this.is_accepted = is_accepted;
    }

    public int getProject_id() {
        return project_id;
    }
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    // toString
    @Override
    public String toString() {
        return "Estimate{" +
                "id='" + id + "'" +
                ", amount_estimate='" + amount_estimate + "'" +
                ", is_accepted='" + is_accepted + "'" +
                ", date_emission='" + date_emission + "'" +
                ", date_validity='" + date_validity + "'" +
                ", project_id='" + project_id + "'" +
                '}';
    }
}
