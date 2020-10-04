package ru.sfedu.train.models;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;

import java.util.StringJoiner;

/**
 * Class Driver
 */
public class Driver {

  //
  // Fields
  //
  @CsvBindByPosition(position = 0)
  private long id;
  @CsvBindByPosition(position = 1)
  private String firstName;
  @CsvBindByPosition(position = 2)
  private String lastName;
  
  //
  // Constructors
  //
  public Driver () { };

  public Driver(long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
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
   * Set the value of firstName
   * @param newVar the new value of firstName
   */
  @Element(name = "FirstName")
  public void setFirstName (String newVar) {
    firstName = newVar;
  }

  /**
   * Get the value of firstName
   * @return the value of firstName
   */
  @Element(name = "FirstName")
  public String getFirstName () {
    return firstName;
  }

  /**
   * Set the value of lastName
   * @param newVar the new value of lastName
   */
  @Element(name = "LastName")
  public void setLastName (String newVar) {
    lastName = newVar;
  }

  /**
   * Get the value of lastName
   * @return the value of lastName
   */
  @Element(name = "LastName")
  public String getLastName () {
    return lastName;
  }

  //
  // Other methods
  //

  @Override
  public String toString() {
    return new StringJoiner(", ", Driver.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .toString();
  }
}
