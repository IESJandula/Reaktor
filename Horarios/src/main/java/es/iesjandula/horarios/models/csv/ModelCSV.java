package es.iesjandula.horarios.models.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Pablo Ruiz Canovas
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelCSV 
{
	/**Nombre de la persona del csv */
	private String nombre;
	/**Apellido de la persona del csv */
	private String apellido;
	/**Cuenta de correo de la persona del csv */
	private String email;
	/**Lista de roles de la persona del csv */
	private String [] roles;
}
