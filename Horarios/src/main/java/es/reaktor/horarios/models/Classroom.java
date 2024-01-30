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
public class Classroom
{
	/** Attribute number*/
	private String number;
	
	/** Attribute floor*/
	private String floor;
}
