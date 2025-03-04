package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
    public void calculateFare(Ticket ticket, boolean isRecurrentUser){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:" +ticket.getOutTime().toString());
        }
        long timeDifference = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
        double duration = (timeDifference / 1000.0) / 3600.0;

        if(duration < 0.5) {
            duration = 0;
        }

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }

        if(isRecurrentUser) {
            double price = ticket.getPrice() - ticket.getPrice() * 0.05;
            ticket.setPrice(price);
        }
    }


}