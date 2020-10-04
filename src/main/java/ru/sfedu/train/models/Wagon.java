package ru.sfedu.train.models;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;

/**
 * Class Wagon
 */
public class Wagon {

  //
  // Fields
  //

  @CsvBindByPosition(position = 0)
  protected long id;
  @CsvBindByPosition(position = 1)
  protected String producer;
  
  //
  // Constructors
  //
  public Wagon () { };

  public Wagon(long id, String producer) {
    this.id = id;
    this.producer = producer;
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
   * Set the value of producer
   * @param newVar the new value of producer
   */
  @Element(name = "Producer")
  public void setProducer (String newVar) {
    producer = newVar;
  }

  /**
   * Get the value of producer
   * @return the value of producer
   */
  @Element(name = "Producer")
  public String getProducer () {
    return producer;
  }

  //
  // Other methods
  //


}
