package com.accubits.trafficticketing.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History implements Parcelable {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lp_num")
    @Expose
    private String lpNum;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("violation")
    @Expose
    private String violation;

    public History(){

    }

    private History(Parcel in){
        type = in.readInt();
        paid = in.readInt();
        amount = in.readInt();
        name = in.readString();
        lpNum = in.readString();
        violation = in.readString();
        timestamp = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(paid);
        dest.writeInt(amount);
        dest.writeString(name);
        dest.writeString(lpNum);
        dest.writeString(violation);
        dest.writeString(timestamp);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getLpNum() {
        return lpNum;
    }

    public void setLpNum(String lpNum) {
        this.lpNum = lpNum;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
