package es.reaktor.horarios.models;

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
public class HorarioProf
{
	/** Attribute actividad*/
	private List<Actividad> actividad;
	
	/** Attribute horNumIntPR*/
	private String horNumIntPR;
	
	/** Attribute totUn*/
	private String totUn;
	
	/** Attribute totAC*/
	private String totAC;
}
