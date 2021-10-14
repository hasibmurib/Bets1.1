package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Premio {


	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer prizeID;
	private String prizeName;
	private String estado;
	private double valor;
	
	
	public Premio(Integer id, String name, String estado, double valor){
		this.prizeID = id;
		this.prizeName = name;
		//listo, repartido, retirado son estados de un premio
		this.estado = estado;
		this.valor = valor;
	}
	
	public Premio(String name, double valor) {
		this.prizeName = name;
		this.valor = valor;
		this.estado = "Listo";
	}
	
	public Integer getPrizeID() {
		return prizeID;
	}
	
	public void setPrizeID(Integer prizeID) {
		this.prizeID = prizeID;
	}
	
	public String getName() {
		return prizeName;
	}
	
	public void setName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public double getValor() {
		return this.valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
}
