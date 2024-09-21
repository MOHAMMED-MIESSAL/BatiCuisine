package model;

import enums.ProjectStatus;

public class Project {
    private int id;
    private String name;
    private double profit_margin;
    private double total_cost;
    private ProjectStatus state_project;
    private int client_id;

    public Project( int id, String name, double profit_margin, ProjectStatus state_project, double total_cost ,int client_id) {
        this.id = id;
        this.name = name;
        this.profit_margin = profit_margin;
        this.state_project = state_project;
        this.total_cost = total_cost;
        this.client_id = client_id;
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

    public double getProfit_margin() {
        return profit_margin;
    }
    public void setProfit_margin(double profit_margin) {
        this.profit_margin = profit_margin;
    }

    public ProjectStatus getState_project() {
        return state_project;
    }
    public void setState_project(ProjectStatus state_project) {
        this.state_project = state_project;
    }

    public double getTotal_cost() {
        return total_cost;
    }
    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public int getClient_id() {
        return client_id;
    }
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    // toString
    @Override
    public String toString() {
        return "Project {id=" + id + ", name='" + name + '\'' + ", profitMargin=" + profit_margin +
                ", totalCost=" + total_cost + ", stateProject='" + state_project + '\'' +
                ", clientId=" + client_id + '}';
    }
}
