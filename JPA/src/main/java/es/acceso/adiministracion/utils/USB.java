package es.acceso.adiministracion.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usb")
public class USB 
{
	@Id
	@Column(length = 10)
	private Long id;
	@Column(length = 10)
	private Boolean active;
	@Column(length = 100)
	private String type;
	@ManyToOne
	@JoinColumn(name = "serialNumber")
	@MapsId("serialNumber")
	private MotherBoard serialNumber;
	

}
