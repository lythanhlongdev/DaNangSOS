package com.capstone2.dnsos.common;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.RescueStation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
public class CalculateDistance {

    public static List<ResultKM> calculateDistance(GPS user, List<RescueStation> rescueStations) throws NotFoundException {
        if (rescueStations.isEmpty()) {
            throw new NotFoundException("Cannot find GPS in list RescueStations: " + rescueStations);
        }

        List<ResultKM> listKm = new ArrayList<>(rescueStations.size());

        for (RescueStation item : rescueStations) {
            double km = user.calculateDistance(item.getLatitude(), item.getLongitude());
            km = BigDecimal.valueOf(km).setScale(2, RoundingMode.HALF_UP).doubleValue();
            listKm.add(new ResultKM(item.getRescueStationsId(), item.getName(), km));
        }

        return listKm;
    }

}
