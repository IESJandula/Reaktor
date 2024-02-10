package es.acceso.administracion.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "action")
	private List<Task> taskList;
	
}
