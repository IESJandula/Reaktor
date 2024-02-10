package es.acceso.adiministracion.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="action")
public class Action 
{
	@Id
	@Column(length = 100)
	private String name;
	@Column(length = 100)
	private String commandL;
	@Column(length = 100)
	private String commandW;
	
	@ManyToOne
	private MotherBoard serialNumber;
	
	

}
