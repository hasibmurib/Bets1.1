package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Apuesta;
import domain.Event;
import domain.Prediction;
import domain.Premio;
//import domain.Booking;
import domain.Question;
import domain.Ticket;
import domain.User;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.NoSuchUserExist;
import exceptions.PredictionAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExist;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public ArrayList<Event> getEvents(Date date);
	
	@WebMethod public ArrayList<Prediction> getPrediction(int question);
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public ArrayList<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod public boolean createUser(String user, String pass, long l) throws UserAlreadyExist;


	@WebMethod public User authenticateUser(String user, String pass) throws NoSuchUserExist;
	
	@WebMethod public Event createEvent(String description,Date eventDate) throws EventAlreadyExist;
	
	@WebMethod public Prediction createPrediction(String result, float gains, Integer questionNumber) throws PredictionAlreadyExist;
	
	@WebMethod public Apuesta createBet(double bet, String user, Integer predictionNumber);
	
	@WebMethod public boolean closeQuestion(Integer questionNumber, Integer predictionNumber);

    @WebMethod public double usersFunds(String user);

    @WebMethod public double addFunds(String user, double m);
    
    @WebMethod public double withdrawFunds(String user, double m);


	@WebMethod public ArrayList<Apuesta> getBets(String loggedUser);
	
	@WebMethod public void closeEvent(Integer evNum);

    @WebMethod Premio createPrize(String name, double valor);
    
    @WebMethod public ArrayList<User> viewRanking();
    
    @WebMethod public ArrayList<Premio> viewPrizes();
    
    @WebMethod public void reward(String u, Integer pNum);
    
    @WebMethod public ArrayList<Premio> viewMyPrizes(String user);
    
    @WebMethod public boolean claimPrize(String u, Integer pNum);
    
    @WebMethod public void resetRanking();
    
    @WebMethod public boolean emptyEvent(Integer qNum);
    
    @WebMethod public Ticket createTicket(String d, Date f, Integer a, Integer eNum);
    
    @WebMethod public Ticket buyTicket(Event evNum, String user);
    
    @WebMethod public ArrayList<Ticket> viewMyTickets(String user);


	
	
}
