package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Apuesta;
import domain.Event;
import domain.Prediction;
import domain.Premio;
import domain.Question;
import domain.Ticket;
import domain.User;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event("Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event("Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event("Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event("Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event("Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event("Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			User admin = new User("admin", "admin", true);

			db.persist(admin);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public ArrayList<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		ArrayList<Event> res = new ArrayList<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public ArrayList<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		ArrayList<Date> res = new ArrayList<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean createUser(String user, String pass, long banco) {

		System.out.println(">> DataAccess: createUser");

		User usuario = db.find(User.class, user);

		if (usuario != null)
			return false;

		else {
			db.getTransaction().begin();
			User usuarioNuevo = new User(user, pass, banco);
			db.persist(usuarioNuevo);
			db.getTransaction().commit();
			return true;
		}

	}

	public User authenticateUser(String user, String pass) {

		System.out.println(">> DataAccess:authenticateUser");

		User usuario = db.find(User.class, user);

		if (usuario == null)
			return null;

		else {
			if (usuario.getPassword().equals(pass))
				return usuario;
			else
				return null;
		}

	}

	public boolean DoesEventExists(String description, Date eventDate) {
		for (Event ev : getEvents(eventDate)) {
			if (ev.getDescription().compareTo(description) == 0)
				return true;
		}
		return false;
	}

	public Event createEvent(String description, Date eventDate) {

		System.out.println(">> DataAccess:createEvent");

		if (DoesEventExists(description, eventDate))
			return null;
		else {
			db.getTransaction().begin();
			Event eventoNuevo = new Event(description, eventDate);
			db.persist(eventoNuevo);
			db.getTransaction().commit();
			return eventoNuevo;
		}
	}

	public Prediction createPrediction(String result, float gains, Integer questionNumber) {
		System.out.println(">> DataAccess: createPrediction=> question= " + questionNumber + " result= " + result
				+ " gains=" + gains);

		Question q = db.find(Question.class, questionNumber);

		if (q.DoesPredictionExists(result))
			return null;
		else {

			db.getTransaction().begin();
			Prediction p = q.addPrediction(result, gains);
			// db.persist(q);
			db.persist(p); // db.persist(q) not required when CascadeType.PERSIST is added in questions
			// property of Event class
			// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return p;
		}
	}

	public ArrayList<Prediction> getPrediction(int ques) {

		System.out.println(">> DataAccess: getPrediction");

		Question q = db.find(Question.class, ques);

		return q.getPredictions();
	}

	public Apuesta createBet(double bet, String user, Integer predictionNumber) {
		System.out.println(
				">> DataAccess: createBet=> prediction= " + predictionNumber + " user= " + user + " bet=" + bet);

		Prediction p = db.find(Prediction.class, predictionNumber);
		User u = db.find(User.class, user);
		Question q = p.getQuestion();
		float min = q.getBetMinimum();
		double money = u.getMoney();
		if (money >= bet && bet >= min) {
			Apuesta b = new Apuesta(user, bet, predictionNumber);
			System.out.println(b);
			db.getTransaction().begin();
			p.addBet(b);
			u.addBet(b);
			u.setMoney(money - bet);
			db.persist(u);
			db.persist(b);
			db.getTransaction().commit();
			return b;
		} else {
			System.out.println("The money you tried to bet is " + bet + ", the money you actually have is " + money
					+ " and the minimun you have to bet is " + min);
			return null;
		}

	}

	public boolean closeQuestion(Integer questionNumber, Integer predictionNumber) {

		Question q = db.find(Question.class, questionNumber);
		Prediction p = db.find(Prediction.class, predictionNumber);

		if (q != null) {
			if (p != null) {

				payWinners(questionNumber, predictionNumber);
				db.getTransaction().begin();
				q.setResult(p.getResult());
				Event e = q.getEvent();
				System.out.println(e.toString());
				e.removeQuestion(q);
				db.persist(q);
				db.persist(e);
				db.getTransaction().commit();
				return true;
			}

			else {
				return false;
			}
		}	
		else
			return false;
	}

	// metodo que devuelve el dinero del usuario
	public double usersFunds(String user) {
		User u = db.find(User.class, user);
		double money = u.getMoney();
		System.out.println(money);

		return money;

	}

	public double addFunds(String user, double m) {
		db.getTransaction().begin();
		User u = db.find(User.class, user);
		double money = usersFunds(user);
		money = money + m;
		u.setMoney(money);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println(money);
		return money;
	}

	public double withdrawFunds(String user, double m) {
		db.getTransaction().begin();
		User u = db.find(User.class, user);
		double money = usersFunds(user);
		money = money - m;
		u.setMoney(money);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println(money);
		return money;
	}

	private void payWinners(Integer questionNumber, Integer predictionNumber) {

		Question q = db.find(Question.class, questionNumber);

		ArrayList<Prediction> pre = q.getPredictions();

		for (int i = 0; i < pre.size(); i++) {

			Prediction p = pre.get(i);

			ArrayList<Apuesta> bets = pre.get(i).getBets();

			if (p.getPredictionNumber() == predictionNumber) {

				for (int j = 0; j < bets.size(); j++) {

					Apuesta a = bets.get(j);

					String user = a.getUser();

					double bet = a.getBet();

					float gains = p.getGains();

					db.getTransaction().begin();

					User u = db.find(User.class, user);

					double money = u.getMoney();

					u.setMoney(money + bet * gains);
					
					double ganancias = u.getGanancias();
					
					u.setGanancias(ganancias + bet*gains);
					
					a.setStatus("Win");

					System.out.println(a.toString());
					System.out.println(user + " had " + money + " and " + user + " has won " + bet * gains);

					db.persist(u);

					db.persist(a);

					db.getTransaction().commit();

				}
			} else {

				for (int j = 0; j < bets.size(); j++) {

					Apuesta a = bets.get(j);

					a.setStatus("Lose");

					String user = a.getUser();

					System.out.println(user + " has lost money!!! -> " + a.getBet());
				}

			}
		}
	}

	public ArrayList<Apuesta> getBets(String loggedUser) {
		User u = db.find(User.class, loggedUser);
		return u.getBets();
	}

	public void closeEvent(Integer evNum) {
		Event e = db.find(Event.class, evNum);
		db.getTransaction().begin();
		db.remove(e);
		db.getTransaction().commit();
	}
	
	//Creamos un premio y lo almacenamos en la BD
	public Premio createPrize(String name, double valor) {
		System.out.println(">> DataAccess:createPrize");
		db.getTransaction().begin();
		Premio premioNuevo = new Premio(name, valor);
		db.persist(premioNuevo);
		db.getTransaction().commit();
		return premioNuevo;
	}
	
	public ArrayList<User> viewRanking() {
		ArrayList<User> ranking = new ArrayList<User>();
		TypedQuery<User> query = db.createQuery("SELECT us FROM User us WHERE us.admin=false", User.class);
		List<User> list = query.getResultList();
		while(list.isEmpty()==false) {
			ranking.add(topUser(list));
		}					
		return ranking;
	}
	private User topUser(List<User> list) {
		double max = -1;
		User u = null;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getGanancias()>max) {
				u=list.get(i);
				max = u.getGanancias();
			}
		}
		list.remove(u);
		return u;
	}
	public ArrayList<Premio> viewPrizes(){
		ArrayList<Premio> premios = new ArrayList<Premio>();
		TypedQuery<Premio> query = db.createQuery("SELECT pr FROM Premio pr WHERE estado='Listo'", Premio.class);
		List<Premio> list = query.getResultList();
		for (Premio p : list) {			
			premios.add(p);
		}
		return premios;
	}
	
	public void reward(String u, Integer pNum) {
		Premio p = db.find(Premio.class, pNum);
		User user = db.find(User.class, u);
		db.getTransaction().begin();
		user.addPremio(p);
		p.setEstado("Premiado");
		db.persist(user);
		db.persist(p);
		db.getTransaction().commit();
	}
	
	public void resetRanking(){
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.admin=false", User.class);
		List<User> list = query.getResultList();
		for (User u : list) {	
			db.getTransaction().begin();
			u.setGanancias(0);
			db.persist(u);
			db.getTransaction().commit();
		}
	}
	
	public ArrayList<Premio> viewMyPrizes(String user){
		User u = db.find(User.class, user);
		System.out.println(u.getPremiosDisponibles());
		return u.getPremiosDisponibles();
	}
	
	public boolean claimPrize(String u, Integer pNum) {
		User user = db.find(User.class, u);
		Premio p = db.find(Premio.class, pNum);
		if (!p.getEstado().equals("Recibido")) {
			db.getTransaction().begin();
			double gains = p.getValor();
			p.setEstado("Recibido");
			double money = user.getMoney();
			user.setMoney(money+gains);
			db.persist(p);
			db.persist(user);
			db.getTransaction().commit();
			return true;
		}
		
		else return false;
		
	}
	
	public boolean emptyEvent(Integer qNum) {
		Question q = db.find(Question.class, qNum);
		Event e = q.getEvent();
		Vector<Question> ques = e.getQuestions();
		if(ques.isEmpty())
			return true;
		else
			return false;
	}
	
	public Ticket createTicket(String d, Date f, Integer a, Integer eNum) {
		System.out.println(">> DataAccess:createTicket");
			Event e = db.find(Event.class, eNum);
			db.getTransaction().begin();
			Ticket ticket = new Ticket(d, f, a);
			db.persist(ticket);
			e.addTicket(ticket);
			db.persist(e);
			db.getTransaction().commit();
			return ticket;
	}
	
	
	public Ticket buyTicket(Event ev, String user) {
		Event e = db.find(Event.class, ev.getEventNumber());
		User u = db.find(User.class, user);
		Ticket ti = null;
		if(u!=null&&e!=null) {
			System.out.println("wako"+u.getMoney()+ " "+e.ticketStock());
			if(u.getMoney()>=20 && e.ticketStock()>0) {
				db.getTransaction().begin();
				Vector<Ticket> tickets = e.getTickets();
				ti = tickets.firstElement();
				u.addTicket(ti);
				e.removeTicket(ti);
				u.setMoney(u.getMoney()-20);
				db.persist(e);
				db.persist(u);
				db.getTransaction().commit();
				return ti;
			}
			else
				return ti;
		}
		return ti;
	}
	
	public ArrayList<Ticket> viewMyTickets(String user) {
		User u = db.find(User.class, user);
		ArrayList<Ticket> tickets;
		tickets = u.getTickets();
		tickets.size();
		return tickets;
	}

}