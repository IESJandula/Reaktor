package es.reaktor.horarios.models;



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
public class Tramo
{
	/** Attribute numTr*/
	private String numTr;
	
	/** Attribute numeroDia*/
	private String numeroDia;
	
	/** Attribute horaInicio*/
	private String horaInicio;
	
	/** Attribute horaFinal*/
	private String horaFinal;
}