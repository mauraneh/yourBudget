package models;

import java.time.LocalDate;

public class Expense {
    private LocalDate date;
    private float amount;
    private float homeRent;
    private float entertainment;
    private float food;
    private float transport;
    private float travel;
    private float taxes;
    private float others;

    public Expense(LocalDate date, float amount, float homeRent, float food, float transport, float travel, float taxes, float others) {
        this.date = date;
        this.amount = amount;
        this.homeRent = homeRent;
        this.food = food;
        this.transport = transport;
        this.travel = travel;
        this.taxes = taxes;
        this.others = others;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public float getHomeRent() {
        return homeRent;
    }
    public void setHomeRent(float homeRent) {
        this.homeRent = homeRent;
    }
    public float getEntertainment() {
        return entertainment;
    }
    public void setEntertainment(float entertainment) {
        this.entertainment = entertainment;
    }
    public float getFood() {
        return food;
    }
    public void setFood(float food) {
        this.food = food;
    }
    public float getTransport() {
        return transport;
    }
    public void setTransport(float transport) {
        this.transport = transport;
    }
    public float getTravel() {
        return travel;
    }
    public void setTravel(float travel) {
        this.travel = travel;
    }
    public float getTaxes() {
        return taxes;
    }
    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }
    public float getOthers() {
        return others;
    }
    public void setOthers(float others) {
        this.others = others;
    }
}
