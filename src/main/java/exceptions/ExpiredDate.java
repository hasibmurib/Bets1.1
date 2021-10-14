package exceptions;

public class ExpiredDate extends Exception {
 private static final long serialVersionUID = 1L;
 
 public ExpiredDate()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public ExpiredDate(String s)
  {
    super(s);
  }
}