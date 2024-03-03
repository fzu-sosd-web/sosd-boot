package org.sosd.client.util;

import org.springframework.stereotype.Component;

@Component
public class GeoUtil {

    // 使用 Haversine 公式计算两点之间的距离
    public static boolean isWithinRadius(double eventLat, double eventLon, double userLat, double userLon, double radius) {
        double earthRadius = 6371000; // 地球半径，单位为米
        double dLat = Math.toRadians(userLat - eventLat);
        double dLon = Math.toRadians(userLon - eventLon);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(eventLat)) * Math.cos(Math.toRadians(userLat)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;
        return distance <= radius;
    }
}