package businessLogic;
import java.util.ArrayList;
//hola
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Apuesta;
import domain.Event;
import domain.Prediction;
import domain.Premio;
import domain.Question;
import domain.Ticket;
import domain.User;
import exceptions.EventFinished;
import exceptions.PredictionAlreadyExist;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
//un comentario
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
			}
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

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
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public ArrayList<Event> getEvents(Date date)  {
		dbManager.open(false);
		ArrayList<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    @WebMethod	
	public ArrayList<Prediction> getPrediction(int ques)  {
		dbManager.open(false);
		ArrayList<Prediction>  predictions =dbManager.getPrediction(ques);
		dbManager.close();
		return predictions;
	}
    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod 
	public ArrayList<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		ArrayList<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
    
    public boolean createUser(String user, String pass, long banco){
		
		dbManager.open(false);
		boolean  insertado = dbManager.createUser(user, pass, banco);
		dbManager.close();
		return insertado;
		
	}
    @WebMethod
    public User authenticateUser(String user, String pass) {
		
		dbManager.open(false);
		User  user1 = dbManager.authenticateUser(user, pass);
		dbManager.close();
		return user1;
	}
    @WebMethod
    public Event createEvent(String description,Date eventDate) {
    	
    	dbManager.open(false);
    	Event insertado = dbManager.createEvent(description, eventDate);
    	dbManager.close();
    	return insertado;

    }
    
    @WebMethod
    public Prediction createPrediction(String result, float gains, Integer questionNumber) throws PredictionAlreadyExist {
    	
    	dbManager.open(false);
    	Prediction p = dbManager.createPrediction(result, gains, questionNumber);
    	dbManager.close();
    	return p;

    }
    
    @WebMethod
    public Apuesta createBet(double bet, String user, Integer predictionNumber) {
    	
    	dbManager.open(false);
    	Apuesta b = dbManager.createBet(bet, user, predictionNumber);
    	dbManager.close();
    	return b;

    }
    @WebMethod
    public boolean closeQuestion(Integer questionNumber, Integer predictionNumber) {
    	
    	dbManager.open(false);
    	boolean eventClosed = dbManager.closeQuestion(questionNumber, predictionNumber);
    	dbManager.close();
    	return eventClosed;

    }
   
		
   
    @WebMethod
    public double usersFunds(String user) {
    	
    	dbManager.open(false);
    	double money = dbManager.usersFunds(user);
    	dbManager.close();
    	return money;

    }
    @WebMethod
    public double addFunds(String user, double m) {
    	dbManager.open(false);
    	double money = dbManager.addFunds(user, m);
    	dbManager.close();
    	return money;
    	
    }
    @WebMethod
    public double withdrawFunds(String user, double m) {
    	dbManager.open(false);
    	double money = dbManager.withdrawFunds(user, m);
    	dbManager.close();
    	return money;
    	
    }

	@WebMethod
	public ArrayList<Apuesta> getBets(String loggedUser) {
		dbManager.open(false);
		ArrayList<Apuesta> bets = dbManager.getBets(loggedUser);
		dbManager.close();
		return bets;
	}
	
	@WebMethod
	public void closeEvent(Integer evNum) {
		dbManager.open(false);
    	dbManager.closeEvent(evNum);
    	dbManager.close();
	}

	@Override
	public Premio createPrize(String name, double valor) {
		dbManager.open(false);
    	Premio prizeCreated = dbManager.createPrize(name, valor);
    	dbManager.close();
		return prizeCreated ;
	}
	
	@WebMethod
	public ArrayList<User> viewRanking(){
		dbManager.open(false);
		ArrayList<User> ranking = dbManager.viewRanking();
		dbManager.close();
		return ranking;
	}
	
	@WebMethod
	public ArrayList<Premio> viewPrizes(){
		dbManager.open(false);
		ArrayList<Premio> premios = dbManager.viewPrizes();
		dbManager.close();
		return premios;
	}
	
	@WebMethod
	public void reward(String u, Integer pNum){
		dbManager.open(false);
		dbManager.reward(u, pNum);
		dbManager.close();
	}
	
	public ArrayList<Premio> viewMyPrizes(String user){
		dbManager.open(false);
		ArrayList<Premio> premios = dbManager.viewMyPrizes(user);
		dbManager.close();
		return premios;
	}
	
	@WebMethod
	public boolean claimPrize(String u, Integer pNum) {
		dbManager.open(false);
		boolean result = dbManager.claimPrize(u, pNum);
		dbManager.close();
		return result;
	}
	
	public void resetRanking(){
		dbManager.open(false);
		dbManager.resetRanking();
		dbManager.close();
	}
	
	public boolean emptyEvent(Integer qNum) {
		dbManager.open(false);
		boolean empty = dbManager.emptyEvent(qNum);
		dbManager.close();
		return empty;
	}
	
	public Ticket createTicket(String d, Date f, Integer a, Integer eNum) {
		dbManager.open(false);
		Ticket ticket = dbManager.createTicket(d, f, a, eNum);
		dbManager.close();
		return ticket;
	}
	
	public Ticket buyTicket(Event evNum, String user) {
		dbManager.open(false);
		Ticket ticket = dbManager.buyTicket(evNum, user);
		dbManager.close();
		return ticket;	
	}
	
	public ArrayList<Ticket> viewMyTickets(String user) {
		dbManager.open(false);
		ArrayList<Ticket> tickets = dbManager.viewMyTickets(user);
		dbManager.close();
		return tickets;
	}
}

