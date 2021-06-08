package com.example.fellowtraveller2;

import android.location.Location;

import com.google.firebase.firestore.Exclude;

public class Orders {
    /*private double driversCurrPointLat;
    private double driversCurrPointLon;
    private double driversEndPointLat;
    private double driversEndPointLon;*/
    private Location CurrPos;
    private Location DestPos;
    private String driverId;

    /*private float userCurrPointLat;
    private float userCurrPointLon;
    private float userEndPointLat;
    private float userEndPointLon;
    private String userId;*/
    private String status;
    private String orderID;

    public Orders(){

    }

    public Orders(Location currPos, Location destPos, String driverId, String status, String orderID) {
        this.CurrPos = currPos;
        this.DestPos = destPos;
        this.orderID = orderID;
    }


    @Exclude
    public Location getCurrPos() {
        return CurrPos;
    }

    public void setCurrPos(Location currPos) {
        CurrPos = currPos;
    }

    public Location getDestPos() {
        return DestPos;
    }

    public void setDestPos(Location destPos) {
        DestPos = destPos;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
