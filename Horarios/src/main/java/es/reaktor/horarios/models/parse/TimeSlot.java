package es.reaktor.horarios.models.parse;



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
public class TimeSlot
{
	/** Attribute numTr*/
	private String numTr;
	
	/** Attribute numeroDia*/
	private String dayNumber;
	
	/** Attribute horaInicio*/
	private String startHour;
	
	/** Attribute horaFinal*/
	private String endHour;
	
	
}