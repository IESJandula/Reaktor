package es.acceso.adiministracion.utils;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name="task")
public class Task 
{
	@EmbeddedId
	private TaskId taskId;
	@Column(length = 10)
	private Status status;
	@ManyToOne
	@JoinColumn(name = "serialNumber")
	@MapsId("serialNumber")
	private MotherBoard serialNumber;
	@ManyToOne
	@JoinColumn(name = "name")
	@MapsId("name")
	private Action name;

}
