package ru.sfedu.train.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.train.constants.Result;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.constants.WrapXML;
import ru.sfedu.train.models.*;
import ru.sfedu.train.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.sfedu.train.constants.Constants.*;
import static ru.sfedu.train.constants.Constants.CSV_TRAIN;
import static ru.sfedu.train.constants.Status.ERROR;
import static ru.sfedu.train.constants.Status.SUCCESS;
import static ru.sfedu.train.constants.WagonType.FREIGHT;
import static ru.sfedu.train.constants.WagonType.PASSENGER;
import static ru.sfedu.train.utils.ConfigurationUtil.*;
import static ru.sfedu.train.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderXML implements IDataProvider {

    private static Logger log = LogManager.getLogger(DataProviderXML.class);

    /**
     * @param bean
     * @return Result
     */
    @Override
    public Result appendPassenger(Passenger bean) {
        try {
            if (getPassengerById(bean.getId()) == null) {
                return saveRecord(bean, XML_PASSENGER);
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
                return saveRecord(bean, XML_FREIGHT);
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
                return saveRecord(bean, XML_DRIVER);
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
            List<Passenger> passengers = getRecord(XML_PASSENGER);
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
            List<Freight> freights = getRecord(XML_FREIGHT);
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
            List<Driver> drivers = getRecord(XML_DRIVER);
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
            List<Passenger> passengers = getRecord(XML_PASSENGER);
            List<Train> trains = getRecord(XML_TRAIN);

            trains.removeIf(train -> train.getType().equals(PASSENGER.toString())
                    && train.getWagon().getId() == id);
            passengers.removeIf(passenger -> passenger.getId() == id);

            if (delRecord(trains, XML_TRAIN).getStatus() == SUCCESS) {
                return delRecord(passengers, XML_PASSENGER);
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
            List<Freight> freights = getRecord(XML_FREIGHT);
            List<Train> trains = getRecord(XML_TRAIN);

            trains.removeIf(train -> train.getType().equals(FREIGHT.toString())
                    && train.getWagon().getId() == id);
            freights.removeIf(freight -> freight.getId() == id);

            if (delRecord(trains, XML_TRAIN).getStatus() == SUCCESS) {
                return delRecord(freights, XML_FREIGHT);
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
            List<Driver> drivers = getRecord(XML_DRIVER);
            List<Train> trains = getRecord(XML_TRAIN);

            trains.removeIf(train -> train.getDriver().getId() == id);
            drivers.removeIf(driver -> driver.getId() == id);

            if (delRecord(trains, XML_TRAIN).getStatus() == SUCCESS) {
                return delRecord(drivers, XML_DRIVER);
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
            List<Train> trains = getRecord(XML_TRAIN);
            if (trains.stream().allMatch(train ->
                    train.getId() != bean.getId()
                            && train.getDriver().getId() != bean.getDriver().getId()
                            && (train.getWagon().getId() != bean.getWagon().getId()
                            || !train.getType().equals(bean.getType())))) {
                return saveRecord(bean, XML_TRAIN);
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
            List<Train> trains = getRecord(XML_TRAIN);
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
            List<Train> trains = getRecord(XML_TRAIN);
            trains.removeIf(train -> train.getId() == id);
            return delRecord(trains, XML_TRAIN);
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
            List<Train> trains = getRecord(XML_TRAIN);
            if (!getTrainById(trainId).isEmpty()
                    && trains.stream().anyMatch(t -> t.getWagon().getId() != wagonId)) {
                getTrainById(trainId).stream().findFirst().ifPresent(p->{
                    train.setId(p.getId());
                    train.setType(p.getType());
                    train.setDriver(p.getDriver());
                    train.setWagon(getWagonById(WagonType.valueOf(p.getType()), wagonId));
                });
                return saveRecord(train, XML_TRAIN);
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
            return delRecord(trains, XML_TRAIN);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

//

    private <T> Result saveRecord(T bean, String key) {
        ArrayList<T> arrayList = new ArrayList<>();
        try {
            String filePath = getConfigurationEntry(key);
            List<T> list = new ArrayList<>();
            list.add(bean);

            arrayList.addAll(getRecord(key));
            arrayList.addAll(list);

            FileWriter writer = new FileWriter(filePath);
            Serializer serializer = new Persister();

            WrapXML<T> xml = new WrapXML<T>(arrayList);

            serializer.write(xml, writer);
            return new Result(SUCCESS);
        } catch (Exception e) {
            log.error(e);
            return new Result(ERROR);
        }
    }

    public <T> List<T> getRecord(String key) {
        try {
            String filePath = getConfigurationEntry(key);
            Reader reader = new FileReader(filePath);
            Serializer serializer = new Persister();

            WrapXML xml = serializer.read(WrapXML.class, reader);

            if (xml.getList() == null) xml.setList(Collections.emptyList());

            return xml.getList();
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    private <T> Result delRecord(List<T> beans, String key) {
        try {
            String filePath = getConfigurationEntry(key);

            FileWriter writer = new FileWriter(filePath);
            Serializer serializer = new Persister();

            WrapXML<T> xml = new WrapXML<T>();
            xml.setList(beans);

            serializer.write(xml, writer);
            return new Result(SUCCESS);
        } catch (Exception e) {
            log.error(e);
            return new Result(ERROR);
        }
    }
}
