package ru.sfedu.train.api;

import ru.sfedu.train.models.Driver;
import ru.sfedu.train.models.Freight;
import ru.sfedu.train.models.Passenger;

import static ru.sfedu.train.constants.FreightType.CLOSED;
import static ru.sfedu.train.constants.FreightType.OPENED;
import static ru.sfedu.train.constants.PassengerType.COUPE;
import static ru.sfedu.train.constants.PassengerType.RESERVEDSEAT;

public class Fill {

    public Driver driver1() {
        return new Driver(1, "Ivan", "Ivanov");
    }
    public Driver driver2() {
        return new Driver(2, "Petr", "Petrov");
    }
    public Passenger passenger1() {return new Passenger(1, "УВЗ", 54, COUPE.toString());}
    public Passenger passenger2() {return new Passenger(2, "УВЗ", 54, RESERVEDSEAT.toString());}
    public Freight freight1() {return new Freight(1, "УВЗ", 200, CLOSED.toString());}
    public Freight freight2() {return new Freight(2, "УВЗ", 200, OPENED.toString());}

}
