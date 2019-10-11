package com.accubits.trafficticketing.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Violations {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("penalty")
    @Expose
    private Integer penalty;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }
}

