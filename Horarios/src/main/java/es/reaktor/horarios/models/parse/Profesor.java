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
public class Profesor
{
	/** Attribute numIntPR*/
	private String numIntPR;
	
	/** Attribute abreviatura*/
	private String abreviatura;
	
	/** Attribute nombre*/
	private String nombre;
	
	/** Attribute primerApellido*/
	private String primerApellido;
	
	/** Attribute segundoApellido*/
	private String segundoApellido;
}