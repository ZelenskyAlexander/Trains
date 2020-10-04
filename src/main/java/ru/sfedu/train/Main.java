package ru.sfedu.train;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.train.api.DataProviderCSV;
import ru.sfedu.train.api.DataProviderJDBC;
import ru.sfedu.train.api.DataProviderXML;
import ru.sfedu.train.api.IDataProvider;
import ru.sfedu.train.constants.FreightType;
import ru.sfedu.train.constants.PassengerType;
import ru.sfedu.train.constants.Status;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.models.Driver;
import ru.sfedu.train.models.Freight;
import ru.sfedu.train.models.Passenger;
import ru.sfedu.train.models.Train;

import java.util.Scanner;

import static ru.sfedu.train.constants.Constants.*;
import static ru.sfedu.train.constants.Status.ERROR;

public class Main {

    public static String PATH = System.getProperty(CONFIG_KEY, CONFIG_JAR);
    private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String[] parse = scanner.nextLine().split(CLI_PARSE_S, 3);
            IDataProvider dataProvider = initDataSource(parse[0]);
            String[] bo = parse[1].split(CLI_PARSE_M, 2);
            log.info(beans(dataProvider, bo[0], bo[1], parse[2]));
        } catch (Exception e) {
            log.error(e);
        }
//        csv driver-append 1, Ivan, Ivanov
//        csv freight-append 1, UVZ, 250, CLOSED
//        csv train-install 1, 1, 1, FREIGHT
    }

    public static IDataProvider initDataSource(String param) {
        switch (param.toUpperCase()) {
            case CLI_CSV: return new DataProviderCSV();
            case CLI_XML: return new DataProviderXML();
            case CLI_DB: return new DataProviderJDBC();
            default: return null;
        }
    }

    public static String beans(IDataProvider provider, String bean, String operation,  String param) {
        switch (bean.toUpperCase()) {
            case CLI_TRAIN: return train(provider, param, operation);
            case CLI_DRIVER: return driver(provider, param, operation);
            case CLI_FREIGHT: return freight(provider, param, operation);
            case CLI_PASSENGER: return passenger(provider, param, operation);
            default: return null;
        }
    }

    private static String passenger(IDataProvider provider, String param, String operation) {
        try {
            switch (operation.toUpperCase()) {
                case CLI_DEL_BY_ID:
                    return provider.delPassengerById(Long.parseLong(param)).getStatus().toString();
                case CLI_GET_BY_ID:
                    return provider.getPassengerById(Long.parseLong(param)).toString();
                case CLI_APPEND:
                    String[] a = parser(param, 4);
                    assert a != null;
                    Passenger passenger = new Passenger();
                    passenger.setId(Long.parseLong(a[0]));
                    passenger.setProducer(a[1]);
                    passenger.setNumPassenger(Integer.parseInt(a[2]));
                    passenger.setType(PassengerType.valueOf(a[3].toUpperCase()).toString());
                    return provider.appendPassenger(passenger).getStatus().toString();
            }
        } catch (Exception e) {
            log.error(e);
        }
        return ERROR.toString();
    }

    private static String freight(IDataProvider provider, String param, String operation) {
        try {
            switch (operation.toUpperCase()) {
                case CLI_DEL_BY_ID:
                    return provider.delFreightById(Long.parseLong(param)).getStatus().toString();
                case CLI_GET_BY_ID:
                    return provider.getFreightById(Long.parseLong(param)).toString();
                case CLI_APPEND:
                    String[] a = parser(param, 4);
                    assert a != null;
                    Freight freight = new Freight();
                    freight.setId(Long.parseLong(a[0]));
                    freight.setProducer(a[1]);
                    freight.setMaxCapacity(Integer.parseInt(a[2]));
                    freight.setType(FreightType.valueOf(a[3].toUpperCase()).toString());
                    return provider.appendFreight(freight).getStatus().toString();
            }
        } catch (Exception e) {
            log.error(e);
        }
        return ERROR.toString();
    }

    private static String driver(IDataProvider provider, String param, String operation) {
        try {
            switch (operation.toUpperCase()) {
                case CLI_DEL_BY_ID:
                    return provider.delDriverById(Long.parseLong(param)).getStatus().toString();
                case CLI_GET_BY_ID:
                    return provider.getDriverById(Long.parseLong(param)).toString();
                case CLI_APPEND:
                    String[] a = parser(param, 3);
                    assert a != null;
                    Driver driver = new Driver();
                    driver.setId(Long.parseLong(a[0]));
                    driver.setFirstName(a[1]);
                    driver.setLastName(a[2]);
                    return provider.appendDriver(driver).getStatus().toString();
            }
        } catch (Exception e) {
            log.error(e);
        }
        return ERROR.toString();
    }

    public static String train(IDataProvider provider, String param, String operation) {
        try {
            String[] a;
            switch (operation.toUpperCase()) {
                case CLI_TRAIN_DISSOLVE:
                    return provider.dissolveTrain(Long.parseLong(param)).getStatus().toString();
                case CLI_GET_BY_ID:
                    return provider.getTrainById(Long.parseLong(param)).toString();
                case CLI_TRAIN_HOOK:
                    a = parser(param, 2);
                    assert a != null;
                    return provider.hookWagon(Long.parseLong(a[0]), Long.parseLong(a[1])).getStatus().toString();
                case CLI_TRAIN_UNHOOK:
                    a = parser(param, 2);
                    assert a != null;
                    return provider.unhookWagon(Long.parseLong(a[0]), Long.parseLong(a[1])).getStatus().toString();
                case CLI_TRAIN_INSTALL:
                    a = parser(param, 4);
                    assert a != null;
                    Train train = new Train();
                    train.setId(Long.parseLong(a[0]));
                    train.setWagon(provider.getWagonById(WagonType.valueOf(a[3].toUpperCase()), Long.parseLong(a[1])));
                    train.setDriver(provider.getDriverById(Long.parseLong(a[2])));
                    train.setType(WagonType.valueOf(a[3].toUpperCase()).toString());
                    return provider.installTrain(train).getStatus().toString();
            }
        } catch (Exception e) {
            log.error(e);
        }
        return ERROR.toString();
    }

    private static String[] parser(String values, int count) {
        String[] value = values.split(CLI_PARSE_P);
        if (value.length != count) return null;
        return value;
    }

}
