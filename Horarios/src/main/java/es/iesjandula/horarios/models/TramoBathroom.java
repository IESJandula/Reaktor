package es.iesjandula.horarios.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Pablo Ruiz Canovas
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramoBathroom 
{
	/**Alumno que va al bathroom */
	private Alumno alumno;
	/**Tramo de inicio */
	private String tramoInicial;
	/**Tramo final */
	private String tramoFinal;
	/**Dia en el que ha ido al servicio */
	private String dia;
}
