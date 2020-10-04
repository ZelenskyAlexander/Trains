package ru.sfedu.train.models;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.train.constants.PassengerType;

import java.util.StringJoiner;

/**
 * Class Passenger
 */
public class Passenger extends Wagon {

  //
  // Fields
  //

  @CsvBindByPosition(position = 2)
  private int numPassenger;
  @CsvBindByPosition(position = 3)
  private String type;
  
  //
  // Constructors
  //
  public Passenger () { };

  public Passenger(long id, String producer, int numPassenger, String type) {
    super(id, producer);
    this.numPassenger = numPassenger;
    this.type = type;
  }

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of numPassenger
   * @param newVar the new value of numPassenger
   */
  @Element(name = "NumPassenger")
  public void setNumPassenger (int newVar) {
    numPassenger = newVar;
  }

  /**
   * Get the value of numPassenger
   * @return the value of numPassenger
   */
  @Element(name = "NumPassenger")
  public int getNumPassenger () {
    return numPassenger;
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
    return new StringJoiner(", ", Passenger.class.getSimpleName() + "[", "]")
            .add("numPassenger=" + numPassenger)
            .add("type=" + type)
            .add("id=" + id)
            .add("producer='" + producer + "'")
            .toString();
  }
}
