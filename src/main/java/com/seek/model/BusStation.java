package com.seek.model;

public class BusStation {
    private String arsId;
    private double dist;
    private double gpsX;
    private double gpsY;
    private String stationId;
    private String stationNm;
    private int stationTp;

    // Constructors, getters, and setters
    public BusStation() {}

    public BusStation(String arsId, double dist, double gpsX, double gpsY, String stationId, String stationNm, int stationTp) {
        this.arsId = arsId;
        this.dist = dist;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.stationId = stationId;
        this.stationNm = stationNm;
        this.stationTp = stationTp;
    }

    public String getArsId() {
        return arsId;
    }

    public void setArsId(String arsId) {
        this.arsId = arsId;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getGpsX() {
        return gpsX;
    }

    public void setGpsX(double gpsX) {
        this.gpsX = gpsX;
    }

    public double getGpsY() {
        return gpsY;
    }

    public void setGpsY(double gpsY) {
        this.gpsY = gpsY;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationNm() {
        return stationNm;
    }

    public void setStationNm(String stationNm) {
        this.stationNm = stationNm;
    }

    public int getStationTp() {
        return stationTp;
    }

    public void setStationTp(int stationTp) {
        this.stationTp = stationTp;
    }

    @Override
    public String toString() {
        return "BusStation [arsId=" + arsId + ", dist=" + dist + ", gpsX=" + gpsX + ", gpsY=" + gpsY + ", stationId="
                + stationId + ", stationNm=" + stationNm + ", stationTp=" + stationTp + "]";
    }
}
