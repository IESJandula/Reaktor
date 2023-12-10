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
public class Asignatura
{
	/** Attribute numIntAs */
	private String numIntAs;

	/** Attribute abreviatura */
	private String abreviatura;

	/** Attribute nombre */
	private String nombre;
}