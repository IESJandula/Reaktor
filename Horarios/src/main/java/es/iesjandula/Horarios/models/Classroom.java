package es.iesjandula.Horarios.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Alejandro Cazalla Pérez
 */
public class Classroom
{
	private int number;
	
	private int floor;
}
