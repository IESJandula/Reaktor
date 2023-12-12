package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Tramo
{
	/**Numero del tramo */
	private int num_tr;
	/**Dia en el que se inicia el tramo */
	private int numero_dia;
	/**Hora de inicio del tramo */
	private String hora_inicio;
	/**Hora final del tramo */
	private String hora_final;
	/**
	 * Constructor por defecto para el rest API
	 */
	public Tramo() 
	{
		//Constructor publico
	}
	
	
}
