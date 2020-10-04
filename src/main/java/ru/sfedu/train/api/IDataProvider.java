package ru.sfedu.train.api;

import ru.sfedu.train.constants.Result;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.models.*;

import java.util.List;

/**
 * Interface IDataProvider
 */
public interface IDataProvider {

  /**
   * @return       Result
   * @param        bean
   */
  public Result appendPassenger(Passenger bean);


  /**
   * @return       Result
   * @param        bean
   */
  public Result appendFreight(Freight bean);


  /**
   * @return       Result
   * @param        bean
   */
  public Result appendDriver(Driver bean);


  /**
   * @return       Passenger
   * @param        id
   */
  public Passenger getPassengerById(long id);


  /**
   * @return       Freight
   * @param        id
   */
  public Freight getFreightById(long id);


  /**
   * @return       Driver
   * @param        id
   */
  public Driver getDriverById(long id);


  /**
   * @return       Result
   * @param        id
   */
  public Result delPassengerById(long id);


  /**
   * @return       Result
   * @param        id
   */
  public Result delFreightById(long id);


  /**
   * @return       Result
   * @param        id
   */
  public Result delDriverById(long id);


  /**
   * @return       Wagon
   * @param        type
   * @param        id
   */
  public Wagon getWagonById(WagonType type, long id);


  /**
   * @return       Result
   * @param        bean
   */
  public Result installTrain(Train bean);


  /**
   * @return       List<Train>
   * @param        id
   */
  public List<Train> getTrainById(long id);


  /**
   * @return       Result
   * @param        id
   */
  public Result dissolveTrain(long id);


  /**
   * @return       Result
   * @param        trainId, wagonId
   */
  public Result hookWagon(long trainId, long wagonId);


  /**
   * @return       Result
   * @param        trainId, wagonId
   */
  public Result unhookWagon(long trainId, long wagonId);


}
