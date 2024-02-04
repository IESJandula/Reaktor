package es.iesjandula.reaktor.timetable_server.models.parse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author David Martinez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Datos
{
	/** Attribute asignaturas*/
	private Asignaturas asignaturas;
	
	/** Attribute grupos*/
	private Grupos grupos;
	
	/** Attribute aulas*/
	private Aulas aulas;
	
	/** Attribute profesores*/
	private Profesores profesores;
	
	/** Attribute tramosHorarios*/
	private TramosHorarios tramosHorarios;

}