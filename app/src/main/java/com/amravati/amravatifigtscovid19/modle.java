package com.amravati.amravatifigtscovid19;

public class modle {
    String location,locationm;
    int confirmed,recovered,deceased;
    public modle(){

    }
    public modle(String location,String locationm,int confirmed,int recovered,int deceased){
        this.location=location;
        this.confirmed=confirmed;
        this.recovered=recovered;
        this.deceased=deceased;
        this.locationm=locationm;
    }
    @Override
    public String toString() {
        return "modle{" +
                "location='" + location + '\'' +
                ", locationm='" + locationm + '\'' +
                ", confirmed=" + confirmed +
                ", recovered=" + recovered +
                ", deceased=" + deceased +
                '}';
    }
    public String getLocationm() {
        return locationm;
    }

    public void setLocationm(String locationm) {
        this.locationm = locationm;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }
}
