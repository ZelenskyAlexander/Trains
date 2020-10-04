package ru.sfedu.train.models;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.models.converter.DriverConverter;
import ru.sfedu.train.models.converter.WagonConverter;

import java.util.StringJoiner;

/**
 * Class Train
 */
public class Train {

  //
  // Fields
  //

  @CsvBindByPosition(position = 0)
  private long id;
  @CsvCustomBindByPosition(position = 1, converter = WagonConverter.class)
  private Wagon wagon;
  @CsvCustomBindByPosition(position = 2, converter = DriverConverter.class)
  private Driver driver;
  @CsvBindByPosition(position = 3)
  private String type;
  
  //
  // Constructors
  //
  public Train () { };

  public Train(long id, Wagon wagon, Driver driver, String type) {
    this.id = id;
    this.wagon = wagon;
    this.driver = driver;
    this.type = type;
  }

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  @Element(name = "Id")
  public void setId (long newVar) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  @Element(name = "Id")
  public long getId () {
    return id;
  }

  /**
   * Set the value of wagon
   * @param newVar the new value of wagon
   */
  @Element(name = "Wagon", required = false)
  public void setWagon (Wagon newVar) {
    wagon = newVar;
  }

  /**
   * Get the value of wagon
   * @return the value of wagon
   */
  @Element(name = "Wagon", required = false)
  public Wagon getWagon () {
    return wagon;
  }

  /**
   * Set the value of driver
   * @param newVar the new value of driver
   */
  @Element(name = "Driver", required = false)
  public void setDriver (Driver newVar) {
    driver = newVar;
  }

  /**
   * Get the value of driver
   * @return the value of driver
   */
  @Element(name = "Driver", required = false)
  public Driver getDriver () {
    return driver;
  }

  /**
   * Set the value of type
   * @param newVar the new value of type
   */
  @Element(name = "Type")
  public void setType (String newVar) {
    type = newVar;
  }

  /**
   * Get the value of type
   * @return the value of type
   */
  @Element(name = "Type")
  public String getType () {
    return type;
  }

  //
  // Other methods
  //


  @Override
  public String toString() {
    return new StringJoiner(", ", Train.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("wagon=" + wagon)
            .add("driver=" + driver)
            .add("type=" + type)
            .toString();
  }
}
