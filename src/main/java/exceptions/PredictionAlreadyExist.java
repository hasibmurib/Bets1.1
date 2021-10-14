package exceptions;
public class PredictionAlreadyExist extends Exception {
 private static final long serialVersionUID = 1L;
 
 public PredictionAlreadyExist()
  {
    super();
  }
  /**This exception is triggered if the prediction already exists 
  *@param s String of the exception
  */
  public PredictionAlreadyExist(String s)
  {
    super(s);
  }
}