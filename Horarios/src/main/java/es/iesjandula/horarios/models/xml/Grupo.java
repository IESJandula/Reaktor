package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Grupo
{
	/**Numero del grupo */
	private int num_int_gr;
	/**Abreviatura del grupo */
	private String abreviatura;
	/**Nombre completo del grupo */
	private String nombre;
	/**
	 * Constructor por defecto para la rest API
	 */
	public Grupo() 
	{
		//Constructor publico
	}
}