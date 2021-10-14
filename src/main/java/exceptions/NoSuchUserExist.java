package exceptions;

public class NoSuchUserExist extends Exception {

	private static final long serialVersionUID = 1L;
	 
	 public NoSuchUserExist()
	  {
	    super();
	  }
	  /**This exception is triggered if the user already exists 
	  *@param s String of the exception
	  */
	  public NoSuchUserExist(String s)
	  {
	    super(s);
	  }
}
