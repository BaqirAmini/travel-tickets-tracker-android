package com.example.a303bus;

public class TicketsDataModel {
    private Double ticketPrice;
    private int ticketID, userID;
    private String  fromWhere, toWhere, depTime, depDate, ticketCompany;

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public void setToWhere(String toWhere) {
        this.toWhere = toWhere;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public void setTicketCompany(String ticketCompany) {
        this.ticketCompany = ticketCompany;
    }

    public int getUserID() {
        return userID;
    }
    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public String getToWhere() {
        return toWhere;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getDepDate() {
        return depDate;
    }

    public String getTicketCompany() {
        return ticketCompany;
    }
}