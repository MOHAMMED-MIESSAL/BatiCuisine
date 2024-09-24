package model;

import enums.ProjectStatus;
import service.ClientService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    private int id;
    private String name;
    private Double profit_margin; // Utilisation de Double pour gérer les valeurs null
    private Double total_cost;    // Utilisation de Double pour gérer les valeurs null
    private ProjectStatus state_project = ProjectStatus.PENDING; // Valeur par défaut
    private int client_id;
    // Map to store components with keys "material" and "labor"
    private Map<Class<?>, List<?>> components;

    public Project(int id, String name, Double profit_margin, ProjectStatus state_project, Double total_cost, int client_id) {
        this.id = id;
        this.name = name;
        this.profit_margin = (profit_margin != null) ? profit_margin : 0.0;
        this.state_project = (state_project != null) ? state_project : ProjectStatus.PENDING;
        this.total_cost = (total_cost != null) ? total_cost : 0.0;
        this.client_id = client_id;
        this.components = new HashMap<>();
        this.components.put(Material.class, new ArrayList<>()); // List for materials
        this.components.put(Labor.class, new ArrayList<>()); // List for labor
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

    public Double getProfit_margin() {
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

    public Double getTotal_cost() {
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


    // ========= Custom methods ============ //

    // Generic method to add either Material or Labor
    public <T> void addComponent(T component) {
        List<T> componentList = (List<T>) components.get(component.getClass());
        if (componentList != null) {
            componentList.add(component);
        } else {
            System.out.println("Invalid component type.");
        }
    }

    // Get all components
    public Map<Class<?>, List<?>> getComponents() {
        return components;
    }

    // Méthode pour obtenir le client à partir du client_id
    public Client getClient(ClientService clientService) {
        return clientService.getClientById(this.client_id);
    }


}
