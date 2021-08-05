package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 1. вылет до текущего момента времени
 */

public class DepartureToCurrentTime implements Filter {

    @Override
    public void filter(List<Flight> flightList) {
        LocalDateTime timeNow = LocalDateTime.now();
        System.out.println("1. Вылет до текущего момента времени");

        flightList.forEach(flight -> flight.getSegments().stream()
                .filter(segment -> segment.getArrivalDate().isBefore(timeNow))
                .forEach(segment -> System.out.println(segment.toString())));
    }
}
