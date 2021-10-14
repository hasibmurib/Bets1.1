package domain;

import java.util.ArrayList;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User {
	
	
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private String user;
	private String pass;
	private boolean admin;
	private long banco;
	private double ganancias;
	private ArrayList<Premio> premiosDisponibles = new ArrayList<Premio>();
	private double money;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Apuesta> bets=new ArrayList<Apuesta>();
	private ArrayList<Ticket> tickets=new ArrayList<Ticket>();
	
	public User(String user, String pass, long banco2) {

		this.user=user;
		this.pass=pass;
		this.banco=banco2;
		this.money=5;
		admin=false;
		this.ganancias = 0;
	}
	public User(String user, String pass, boolean admin) {
		this.user = user;
		this.pass = pass;
		this.admin = admin;
	}
	


	public String getPassword() {
		return this.pass;
	}

	public String getUser() {
		return this.user;
	}
	
	public boolean getAdmin() {
		return this.admin;
	}
	
	public void setUser(String user) {
		this.user=user;
	}

	public void setPassword(String pass) {
		this.pass=pass;
	}
	public void setBanco(int banco) {
		this.banco=banco;
	}
	public long getBanco() {
		return this.banco;
	}
	public void setMoney(double money) {
		this.money=money;
	}
	public double getMoney() {
		return this.money;
	}
	
	public void addBet(Apuesta bet)  {       
        bets.add(bet);
	}
	
	public ArrayList<Apuesta> getBets() {
		return bets;
	}
	public String toString(){
		return user+"; dinero: " +Double.toString(money);
	}
	public double getGanancias() {
		return ganancias;
	}
	public void setGanancias(double ganancias) {
		this.ganancias = ganancias;
	}
	public ArrayList<Premio> getPremiosDisponibles() {
		return premiosDisponibles;
	}
	public void setPremiosDisponibles(ArrayList<Premio> premiosDisponibles) {
		this.premiosDisponibles = premiosDisponibles;
	}
	public void addPremio(Premio p) {
		premiosDisponibles.add(p);
	}
	public ArrayList<Ticket> getTickets(){
		return tickets;
	}
	public void setTickets(ArrayList<Ticket> ti) {
		this.tickets = ti;
	}
	public void addTicket(Ticket ti) {
		tickets.add(ti);
	}

}
