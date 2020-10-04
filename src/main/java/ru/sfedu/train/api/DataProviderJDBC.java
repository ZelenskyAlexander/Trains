package ru.sfedu.train.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.train.constants.Result;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.models.*;
import ru.sfedu.train.models.Driver;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.sfedu.train.constants.Constants.*;
import static ru.sfedu.train.constants.Status.ERROR;
import static ru.sfedu.train.constants.Status.SUCCESS;
import static ru.sfedu.train.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJDBC implements IDataProvider {

    private static Logger log = LogManager.getLogger(DataProviderXML.class);
    /**
     * @param bean
     * @return Result
     */
    @Override
    public Result appendPassenger(Passenger bean) {
        try {
            return execute(String.format(DB_PASSENGER_INSERT, bean.getId(),
                    bean.getProducer(), bean.getNumPassenger(), bean.getType()));
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
            return execute(String.format(DB_FREIGHT_INSERT, bean.getId(),
                    bean.getProducer(), bean.getMaxCapacity(), bean.getType()));
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
            return execute(String.format(DB_DRIVER_INSERT, bean.getId(), bean.getFirstName(), bean.getLastName()));
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
            ResultSet set = select(String.format(DB_PASSENGER_SELECT, id));
            if (set != null && set.next()) {
                passenger.setId(set.getLong(DB_PASSENGER_ID));
                passenger.setProducer(set.getString(DB_PASSENGER_PRODUCER));
                passenger.setNumPassenger(set.getInt(DB_PASSENGER_NUMBER));
                passenger.setType(set.getString(DB_PASSENGER_TYPE));
                return passenger;
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
            ResultSet set = select(String.format(DB_FREIGHT_SELECT, id));
            if (set != null && set.next()) {
                freight.setId(set.getLong(DB_FREIGHT_ID));
                freight.setProducer(set.getString(DB_FREIGHT_PRODUCER));
                freight.setMaxCapacity(set.getInt(DB_FREIGHT_CAPACITY));
                freight.setType(set.getString(DB_FREIGHT_TYPE));
                return freight;
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
            ResultSet set = select(String.format(DB_DRIVER_SELECT, id));
            if (set != null && set.next()) {
                driver.setId(set.getLong(DB_DRIVER_ID));
                driver.setFirstName(set.getString(DB_DRIVER_FIRST_NAME));
                driver.setLastName(set.getString(DB_DRIVER_LAST_NAME));
                return driver;
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
            if (execute(String.format(DB_PASSENGER_DELETE, id)).getStatus() == SUCCESS) {
                return execute(String.format(DB_PASSENGER_DELETE_CASCADE, id));
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
            if (execute(String.format(DB_FREIGHT_DELETE, id)).getStatus() == SUCCESS) {
                return execute(String.format(DB_FREIGHT_DELETE_CASCADE, id));
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
            if (execute(String.format(DB_DRIVER_DELETE, id)).getStatus() == SUCCESS) {
                return execute(String.format(DB_DRIVER_DELETE_CASCADE, id));
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
            ResultSet set = select(String.format(DB_TRAIN_VALID_SELECT, bean.getId(),
                    bean.getDriver().getId(), bean.getWagon().getId(), bean.getType()));
            if (set != null && set.next()) {
                if (set.getInt(DB_TRAIN_VALID_CNT) == 0) {
                    return execute(String.format(DB_TRAIN_INSERT, bean.getId(),
                            bean.getWagon().getId(), bean.getDriver().getId(), bean.getType()));
                }
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
        List<Train> trains = new ArrayList<>();
        try {
            ResultSet set = select(String.format(DB_TRAIN_SELECT, id));
            while (set != null && set.next()) {
                Train train = new Train();
                train.setId(set.getLong(DB_TRAIN_ID));
                train.setDriver(getDriverById(set.getLong(DB_TRAIN_DRIVER)));
                train.setWagon(getWagonById(WagonType.valueOf(set.getString(DB_TRAIN_TYPE)), set.getLong(DB_TRAIN_WAGON)));
                train.setType(WagonType.valueOf(set.getString(DB_TRAIN_TYPE).toUpperCase()).toString());
                trains.add(train);
            }
            return trains;
        } catch (Exception e) {
             log.error(e);
        }
        return Collections.emptyList();
    }

    /**
     * @param id
     * @return Result
     */
    @Override
    public Result dissolveTrain(long id) {
        return execute(String.format(DB_TRAIN_DELETE, id));
    }

    /**
     * @param trainId
     * @param wagonId
     * @return Result
     */
    @Override
    public Result hookWagon(long trainId, long wagonId) {
        Train train = new Train();
        try {
            ResultSet set = select(String.format(DB_HOOK_VALID_SELECT, wagonId, trainId));
            if (set != null && set.next()) {
                train.setId(set.getLong(DB_TRAIN_ID));
                train.setDriver(getDriverById(set.getLong(DB_TRAIN_DRIVER)));
                train.setType(set.getString(DB_TRAIN_TYPE));
                train.setWagon(getWagonById(WagonType.valueOf(set.getString(DB_TRAIN_TYPE)), wagonId));

                return execute(String.format(DB_TRAIN_INSERT, train.getId(), train.getWagon().getId(),
                        train.getDriver().getId(), train.getType()));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    /**
     * @param trainId
     * @param wagonId
     * @return Result
     */
    @Override
    public Result unhookWagon(long trainId, long wagonId) {
        return execute(String.format(DB_UNHOOK_DELETE, trainId, wagonId));
    }

//

    private Connection connection() throws IOException, ClassNotFoundException, SQLException {
        Class.forName(getConfigurationEntry(DB_DRIVER));
        return DriverManager.getConnection(
                getConfigurationEntry(DB_CONNECT),
                getConfigurationEntry(DB_USER),
                getConfigurationEntry(DB_PASS));
    }

    private Result execute(String sql) {
        try {
            log.info(sql);
            PreparedStatement statement = connection().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            return new Result(SUCCESS);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result(ERROR);
    }

    public ResultSet select(String sql){
        try {
            log.info(sql);
            PreparedStatement statement = connection().prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            connection().close();
            return set;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.error(e);
        }
        return null;
    }

}
