package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ticket {


	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer ticketID;
	private String descripcion;
	private Date fecha;
	private Integer asiento;
	private double valor = 20;
	
	public Ticket(){
		super();
	}
	public Ticket(Integer id, String d, Date f, Integer asiento){
		super();
		this.ticketID = id;
		this.descripcion = d;
		this.fecha = f;
		this.asiento = asiento;
	}
	
	public Ticket(String d, Date f, Integer asiento){
		super();
		this.descripcion = d;
		this.fecha = f;
		this.asiento = asiento;
	}
	
	
	public Integer getTicketID() {
		return ticketID;
	}
	
	public void setTicketID(Integer tID) {
		this.ticketID = tID;
	}
	
	public String getDescription() {
		return this.descripcion;
	}
	
	public void setDescription(String d) {
		this.descripcion = d;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date f) {
		this.fecha = f;
	}


	public Integer getAsiento() {
		return asiento;
	}


	public void setAsiento(Integer asiento) {
		this.asiento = asiento;
	}
}
