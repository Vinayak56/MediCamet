package com.example.android.medicamet;

public class medicine {

    private String drug;
    private String quantity;

    public medicine(String drug, String quantity) {
        this.drug = drug;
        this.quantity = quantity;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
