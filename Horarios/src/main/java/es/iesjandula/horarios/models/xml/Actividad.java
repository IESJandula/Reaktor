package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Javier Martínez Megías
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actividad
{
	/** Numero de actividades**/
	private int num_act;
	/** Numero de un**/
	private int num_un;
	/** Numero de tramo**/
	private int tramo;
	/** Numero de aulo **/
	private int aulaOAsignatura;
	/** Numero de profesor **/
	private int profesorOAula;
	/*Grupo de actividad*/
	private GrupoActividad grupoActividad;
	/**
	 * @param num_act
	 * @param num_un
	 * @param tramo
	 * @param aula
	 * @param profesor
	 */
	public Actividad(int num_act, int num_un, int tramo, int aulaOAsignatura, int profesorOAula)
	{
		this.num_act = num_act;
		this.num_un = num_un;
		this.tramo = tramo;
		this.aulaOAsignatura = aulaOAsignatura;
		this.profesorOAula = profesorOAula;
	}
	
	
}
