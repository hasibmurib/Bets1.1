package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Apuesta {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNumber;
	private double bet;
	@XmlIDREF
	private Integer predictionNumber;
	private String user;
	private String status;
	
	public Apuesta(){
		super();
	}
	
	public Apuesta(Integer betNumber, String user, double bet, Integer prediction) {
		super();
		this.betNumber = betNumber;
		this.user = user;
		this.bet = bet;
		this.predictionNumber = prediction;
		this.status= "Ongoing";
	}
	
	
	public Apuesta(String user, double bet, Integer prediction) {
		super();
		this.user = user;
		this.bet = bet;	
		this.predictionNumber = prediction;
		this.status= "Ongoing";

	}
	
	
	public Integer getBetNumber() {
		return betNumber;
	}
	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}
	public double getBet() {
		return bet;
	}
	public void setBet(double bet) {
		this.bet = bet;
	}
	public Integer getPrediction() {
		return predictionNumber;
	}
	public void setPrediction(Integer prediction) {
		this.predictionNumber = prediction;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toString(){
		return betNumber+"; prediction: " +predictionNumber+ "; apostado: "+Double.toString(bet)+"; usuario: "+user+ "; estado: "+status;
	}
	
	
}
