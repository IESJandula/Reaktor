package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Javier Martínez Megías
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Centro
{
	/** Nombre del centro**/
	private String nombre_centro;
	/** Nombre del autor**/
	private String autor;
	/** Fecha en la que se realizo**/
	private String fecha;
	/** Datos del centro **/
	private Datos datos;
	
}
