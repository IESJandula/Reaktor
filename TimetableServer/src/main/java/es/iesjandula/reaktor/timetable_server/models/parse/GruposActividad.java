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
public class GruposActividad
{
	/** Attribute totGrAct*/
	private String totGrAct;
	
	/** Attribute grupo1*/
	private String grupo1;
	
	/** Attribute grupo2*/
	private String grupo2;
	
	/** Attribute grupo3*/
	private String grupo3;
	
	/** Attribute grupo4*/
	private String grupo4;
	
	/** Attribute grupo5*/
	private String grupo5;

}
