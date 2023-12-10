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
public class HorarioAsig
{
	/** Attribute actividad*/
	private List<Actividad> actividad;
	
	/** Attribute horNumIntAs*/
	private String horNumIntAs;
	
	/** Attribute totUn*/
	private String totUn;
	
	/** Attribute totAC*/
	private String totAC;
}