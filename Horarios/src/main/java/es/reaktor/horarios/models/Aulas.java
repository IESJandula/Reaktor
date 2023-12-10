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
public class Aulas
{
	/** Attribute aula*/
	private List<Aula> aula;
	
	/** Attribute totAu*/
	private String totAu;
}