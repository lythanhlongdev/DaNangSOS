package com.capstone2.dnsos.common;

public class GPS {
    private double latitude;
    private double longitude;


    public GPS() {
    }

    public GPS(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Tính khoảng cách giữa hai tọa độ sử dụng công thức haversine
    public static double calculateDistance(GPS user,GPS RescueStation) {
        final int R = 6371; // Đường kính trái đất ở đơn vị km
        // chuyển về Radian
        double lat1 = Math.toRadians(user.latitude);
        double lon1 = Math.toRadians(user.longitude);
        double lat2 = Math.toRadians(RescueStation.getLatitude());
        double lon2 = Math.toRadians(RescueStation.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Khoảng cách giữa hai điểm trong đơn vị km
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GPS{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public static void main(String[] args) {
        //16.065716,108.186180
        GPS a = new GPS(16.059883,108.209734);
        //16.066054, 108.205014
        GPS b = new GPS(16.066054,108.205014);
        System.out.println(a.calculateDistance(b) +" km");
    }

}
