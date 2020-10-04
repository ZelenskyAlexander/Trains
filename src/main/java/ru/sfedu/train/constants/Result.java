package ru.sfedu.train.constants;

/**
 * Class Result
 */
public class Result {

  //
  // Fields
  //

  private Status status;
  private String answer;
  
  //
  // Constructors
  //
  public Result () { };

  public Result(Status status, String answer) {
    this.status = status;
    this.answer = answer;
  }

  public Result(Status status) {
    this.status = status;
  }

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Get the value of status
   * @return the value of status
   */
  public Status getStatus () {
    return status;
  }

  /**
   * Get the value of answer
   * @return the value of answer
   */
  public String getAnswer () {
    return answer;
  }

  //
  // Other methods
  //

}
