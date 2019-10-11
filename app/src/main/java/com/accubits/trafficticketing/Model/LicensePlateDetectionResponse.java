package com.accubits.trafficticketing.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LicensePlateDetectionResponse {

    @SerializedName("history")
    @Expose
    private ArrayList<History> history = null;
    @SerializedName("license plate")
    @Expose
    private String licensePlate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("violation")
    @Expose
    private ArrayList<Violations> violation = null;

    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Violations> getViolation() {
        return violation;
    }

    public void setViolation(ArrayList<Violations> violation) {
        this.violation = violation;
    }

}


