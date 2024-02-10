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
@Table(name="motherBoard")
public class MotherBoard 
{
	@Id
	@Column(length = 10)
	private Long serialNumber;
	
	@ManyToOne
	private Task name;
	

}
