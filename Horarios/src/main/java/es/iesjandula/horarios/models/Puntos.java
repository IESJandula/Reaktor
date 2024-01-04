package es.iesjandula.horarios.models;

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
public class Puntos 
{
	/**Valor positivo o negativo de la infraccion */
	private int puntos;
	/**Descripcion de la infraccion */
	private String descripcion;
}
