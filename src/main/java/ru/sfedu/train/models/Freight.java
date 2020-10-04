package ru.sfedu.train.models;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import ru.sfedu.train.constants.FreightType;

import java.util.StringJoiner;

/**
 * Class Freight
 */
public class Freight extends Wagon {

  //
  // Fields
  //

  @CsvBindByPosition(position = 2)
  private int maxCapacity;
  @CsvBindByPosition(position = 3)
  private String type;
  
  //
  // Constructors
  //
  public Freight () { };

  public Freight(long id, String producer, int maxCapacity, String type) {
    super(id, producer);
    this.maxCapacity = maxCapacity;
    this.type = type;
  }

//
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of maxCapacity
   * @param newVar the new value of maxCapacity
   */
  @Element(name = "MaxCapacity")
  public void setMaxCapacity (int newVar) {
    maxCapacity = newVar;
  }

  /**
   * Get the value of maxCapacity
   * @return the value of maxCapacity
   */
  @Element(name = "MaxCapacity")
  public int getMaxCapacity () {
    return maxCapacity;
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
    return new StringJoiner(", ", Freight.class.getSimpleName() + "[", "]")
            .add("maxCapacity=" + maxCapacity)
            .add("type=" + type)
            .add("id=" + id)
            .add("producer='" + producer + "'")
            .toString();
  }
}
