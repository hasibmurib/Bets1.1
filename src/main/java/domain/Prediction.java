package domain;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Prediction implements Serializable {
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer predictionNumber; 
	private float gains; 
	private String result;  
	@XmlIDREF
	private Question question;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Apuesta> bets=new ArrayList<Apuesta>();

	public Prediction(){
		super();
	}
	
	public Prediction(Integer predictionNumber, String result, float gains, Question question) {
		super();
		this.predictionNumber = predictionNumber;
		this.result = result;
		this.gains = gains;
		this.question = question;
	}
	
	public Prediction(String result, float gains, Question question) {
		super();
		this.result = result;
		this.gains = gains;
		this.question = question;

		
	}

	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getPredictionNumber() {
		return predictionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setPredictionNumber(Integer predictionNumber) {
		this.predictionNumber = predictionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public Question getQuestion() {
		return question;
	}
	
	public Integer getQuestionNumber() {
		return question.getQuestionNumber();
	}

	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(Question question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getGains() {
		return gains;
	}
	

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setGains(float gains) {
		this.gains = gains;
	}
	



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(String result) {
		this.result = result;
	}


	public String toString(){
		return predictionNumber+"; resultados: " +result + "; ganancia: "+Float.toString(gains);
	}
	
	public void addBet(Apuesta b)  {
        bets.add(b);
	}
	
	public ArrayList<Apuesta> getBets() {
		return bets;
	}

	
}