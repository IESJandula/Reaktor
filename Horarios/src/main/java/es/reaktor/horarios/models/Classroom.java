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
	private Integer number;
	
	/** Attribute floor*/
	private Integer floor;
}
