package com.capstone2.dnsos.common;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPS {
    private double latitude;
    private double longitude;

    public double calculateDistance(double latitude, double longitude) {
        final double R = 6371.0; // Đường kính trái đất ở đơn vị km

        // Chuyển đổi độ sang radian
        double lat1Rad = Math.toRadians(this.latitude);
        double lon1Rad = Math.toRadians(this.longitude);
        double lat2Rad = Math.toRadians(latitude);
        double lon2Rad = Math.toRadians(longitude);

        // Tính toán
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

        return R * c; // Khoảng cách giữa hai điểm trong đơn vị km
    }


    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        final double R = 6371.0; // Đường kính trái đất ở đơn vị km

        // Chuyển đổi độ sang radian
        double lat1Rad = Math.toRadians(latitude1);
        double lon1Rad = Math.toRadians(longitude1);
        double lat2Rad = Math.toRadians(latitude2);
        double lon2Rad = Math.toRadians(longitude2);

        // Tính toán
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

        return R * c; // Khoảng cách giữa hai điểm trong đơn vị km
    }

//
//    public double calculateDistance(double latitude, double longitude) {
//        final int R = 6371; // Đường kính trái đất ở đơn vị km
//        // chuyển về Radian
//        double lat1 = Math.toRadians(this.latitude);
//        double lon1 = Math.toRadians(this.longitude);
//        double lat2 = Math.toRadians(latitude);
//        double lon2 = Math.toRadians(longitude);
//
//        double dLat = lat2 - lat1;
//        double dLon = lon2 - lon1;
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(lat1) * Math.cos(lat2) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return R * c; // Khoảng cách giữa hai điểm trong đơn vị km
//    }


}
