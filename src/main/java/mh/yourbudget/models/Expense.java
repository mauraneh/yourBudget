package mh.yourbudget.models;

import java.time.LocalDate;

public class Expense {
    private LocalDate date;
    private float amount;
    private float houseRent;
    private float entertainment;
    private float food;
    private float transport;
    private float travel;
    private float taxes;
    private float others;

    public Expense(LocalDate date, float houseRent, float entertainment, float food, float transport, float travel, float taxes, float others) {
        this.date = date != null ? date : LocalDate.now();
        this.houseRent = houseRent;
        this.entertainment = entertainment;
        this.food = food;
        this.transport = transport;
        this.travel = travel;
        this.taxes = taxes;
        this.others = others;
        calculateTotal();
    }

    public Expense() {
        this(LocalDate.now(), 0, 0, 0, 0, 0, 0, 0);
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
        calculateTotal();
    }
    public float getHouseRent() {
        return houseRent;
    }
    public void setHouseRent(float houseRent) { this.houseRent = houseRent; }
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

    public void calculateTotal() {
        this.amount = houseRent + food + entertainment + transport + travel + taxes + others;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", total=" + amount +
                ", housing=" + houseRent +
                ", food=" + food +
                ", goingOut=" + entertainment +
                ", transportation=" + transport +
                ", travel=" + travel +
                ", tax=" + taxes +
                ", other=" + others +
                '}';
    }
}
