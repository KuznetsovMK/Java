package com.gridnine.testing;

import java.util.List;

public class TotalTimeSpentOnGroundMoreThanTwoHours implements Filter {

    private int timeSpent;

    public TotalTimeSpentOnGroundMoreThanTwoHours(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public void filter(List<Flight> flightList) {
        System.out.printf("3. Общее время, проведённое на земле превышает %d часа \n", timeSpent);

        flightList.forEach(flight -> flight.getSegments().stream()
                .filter(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate().minusHours(timeSpent)))
                .forEach(segment -> System.out.println(segment.toString())));
    }
}
