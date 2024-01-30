package es.reaktor.horarios.models.parse;

import java.util.List;

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
public class HorarioAula
{
	/** Attribute actividad*/
	private List<Actividad> actividad;
	
	/** Attribute horNumIntAu*/
	private String horNumIntAu;
	
	/** Attribute totUn*/
	private String totUn;
	
	/** Attribute totAC*/
	private String totAC;
}