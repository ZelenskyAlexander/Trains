package ru.sfedu.train.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.train.constants.Result;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.models.*;
import ru.sfedu.train.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.sfedu.train.constants.Constants.*;
import static ru.sfedu.train.constants.Status.ERROR;
import static ru.sfedu.train.constants.Status.SUCCESS;
import static ru.sfedu.train.constants.WagonType.FREIGHT;
import static ru.sfedu.train.constants.WagonType.PASSENGER;
import static ru.sfedu.train.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderCSV implements IDataProvider {

    private static Logger log = LogManager.getLogger(DataProviderCSV.class);
    /**
     * @param bean
     * @return Result
     */
    @Override
    public Result appendPassenger(Passenger bean) {
        try {
            if (getPassengerById(bean.getId()) == null) {
                return saveRecord(bean, CSV_PASSENGER);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param bean
     * @return Result
     */
    @Override
    public Result appendFreight(Freight bean) {
        try {
            if (getFreightById(bean.getId()) == null) {
                return saveRecord(bean, CSV_FREIGHT);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param bean
     * @return Result
     */
    @Override
    public Result appendDriver(Driver bean) {
        try {
            if (getDriverById(bean.getId()) == null) {
                return saveRecord(bean, CSV_DRIVER);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param id
     * @return Passenger
     */
    @Override
    public Passenger getPassengerById(long id) {
        Passenger passenger = new Passenger();
        try {
            List<Passenger> passengers = getRecord(Passenger.class, CSV_PASSENGER);
            for (Passenger p: passengers) {
                if (p.getId() == id) {
                    passenger.setId(p.getId());
                    passenger.setProducer(p.getProducer());
                    passenger.setNumPassenger(p.getNumPassenger());
                    passenger.setType(p.getType());
                    return passenger;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @param id
     * @return Freight
     */
    @Override
    public Freight getFreightById(long id) {
        Freight freight = new Freight();
        try {
            List<Freight> freights = getRecord(Freight.class, CSV_FREIGHT);
            for (Freight p: freights) {
                if (p.getId() == id) {
                    freight.setId(p.getId());
                    freight.setProducer(p.getProducer());
                    freight.setMaxCapacity(p.getMaxCapacity());
                    freight.setType(p.getType());
                    return freight;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @param id
     * @return Driver
     */
    @Override
    public Driver getDriverById(long id) {
        Driver driver = new Driver();
        try {
            List<Driver> drivers = getRecord(Driver.class, CSV_DRIVER);
            for (Driver d: drivers) {
                if (d.getId() == id) {
                    driver.setId(d.getId());
                    driver.setFirstName(d.getFirstName());
                    driver.setLastName(d.getLastName());
                    return driver;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @param id
     * @return Result
     */
    @Override
    public Result delPassengerById(long id) {
        try {
            List<Passenger> passengers = getRecord(Passenger.class, CSV_PASSENGER);
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);

            trains.removeIf(train -> train.getType().equals(PASSENGER.toString())
                    && train.getWagon().getId() == id);
            passengers.removeIf(passenger -> passenger.getId() == id);

            if (delRecord(trains, CSV_TRAIN).getStatus() == SUCCESS) {
                return delRecord(passengers, CSV_PASSENGER);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param id
     * @return Result
     */
    @Override
    public Result delFreightById(long id) {
        try {
            List<Freight> freights = getRecord(Freight.class, CSV_FREIGHT);
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);

            trains.removeIf(train -> train.getType().equals(FREIGHT.toString())
                    && train.getWagon().getId() == id);
            freights.removeIf(freight -> freight.getId() == id);

            if (delRecord(trains, CSV_TRAIN).getStatus() == SUCCESS) {
                return delRecord(freights, CSV_FREIGHT);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param id
     * @return Result
     */
    @Override
    public Result delDriverById(long id) {
        try {
            List<Driver> drivers = getRecord(Driver.class, CSV_DRIVER);
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);

            trains.removeIf(train -> train.getDriver().getId() == id);
            drivers.removeIf(driver -> driver.getId() == id);

            if (delRecord(trains, CSV_TRAIN).getStatus() == SUCCESS) {
                return delRecord(drivers, CSV_DRIVER);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param type
     * @param id
     * @return Wagon
     */
    @Override
    public Wagon getWagonById(WagonType type, long id) {
        try {
            switch (type) {
                case FREIGHT:
                    return getFreightById(id);
                case PASSENGER:
                    return getPassengerById(id);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @param bean
     * @return Result
     */
    @Override
    public Result installTrain(Train bean) {
        try {
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);
            if (trains.stream().allMatch(train ->
                    train.getId() != bean.getId()
                    && train.getDriver().getId() != bean.getDriver().getId()
                    && (train.getWagon().getId() != bean.getWagon().getId()
                    || !train.getType().equals(bean.getType())))) {
                return saveRecord(bean, CSV_TRAIN);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param id
     * @return List<Train>
     */
    @Override
    public List<Train> getTrainById(long id) {
        try {
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);
            return trains.stream().filter(t->t.getId() == id).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    /**
     * @param id
     * @return Result
     */
    @Override
    public Result dissolveTrain(long id) {
        try {
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);
            trains.removeIf(train -> train.getId() == id);
            return delRecord(trains, CSV_TRAIN);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param trainId, wagonId
     * @return Result
     */
    @Override
    public Result hookWagon(long trainId, long wagonId) {
        Train train = new Train();
        try {
            List<Train> trains = getRecord(Train.class, CSV_TRAIN);
            if (!getTrainById(trainId).isEmpty()
                    && trains.stream().anyMatch(t -> t.getWagon().getId() != wagonId)) {
                getTrainById(trainId).stream().findFirst().ifPresent(p->{
                    train.setId(p.getId());
                    train.setType(p.getType());
                    train.setDriver(p.getDriver());
                    train.setWagon(getWagonById(WagonType.valueOf(p.getType()), wagonId));
                });
                return saveRecord(train, CSV_TRAIN);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param trainId, wagonId
     * @return Result
     */
    @Override
    public Result unhookWagon(long trainId, long wagonId) {
        try {
            List<Train> trains = getTrainById(trainId);
            trains.removeIf(train -> train.getWagon().getId() == wagonId);
            return delRecord(trains, CSV_TRAIN);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

//

    /**
     * @param bean, key
     * @return Result
     */
    private <T> Result saveRecord(T bean, String key) {
        List<T> list = new ArrayList<T>();
        try {
            String filePath = getConfigurationEntry(key);
            list.add(bean);
            FileWriter file_path = new FileWriter(filePath, true);
            CSVWriter writer = new CSVWriter(file_path);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(list);
            writer.close();
            return new Result(SUCCESS);
        } catch (Exception error) {
            log.error(error);
            return new Result(ERROR);
        }
    }

    private  <T> List<T> getRecord(Class clazz, String key) {
        try {
            String filePath = getConfigurationEntry(key);
            FileReader fileReader = new FileReader(filePath);
            CSVReader csvReader = new CSVReader(fileReader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withType(clazz).build();

            List<T> list = csvToBean.parse();

            return (List<T>) list;
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    private <T> Result delRecord(List<T> beans, String key) {
        try {
            String filePath = getConfigurationEntry(key);
            FileWriter file_path = new FileWriter(filePath, false);
            CSVWriter writer = new CSVWriter(file_path);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(beans);
            writer.close();
            return new Result(SUCCESS);
        } catch (Exception error) {
            log.error(error);
            return new Result(ERROR);
        }
    }
}
