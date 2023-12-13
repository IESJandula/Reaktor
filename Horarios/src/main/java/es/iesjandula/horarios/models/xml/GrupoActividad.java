package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Javier Martínez Megías
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoActividad
{
	/**
	 * Numero total de actividades de grupo
	 */
	private int tot_gr_act;
	/**
	 * Numero de grupo 1
	 */
	private int grupo_1;
	/**
	 * Numero de grupo 2
	 */
	private int grupo_2;
	/**
	 * @param tot_gr_act
	 * @param grupo_1
	 */
	public GrupoActividad(int tot_gr_act, int grupo_1)
	{
		this.tot_gr_act = tot_gr_act;
		this.grupo_1 = grupo_1;
	}
	/**
	 * @param tot_gr_act
	 */
	public GrupoActividad(int tot_gr_act)
	{
		this.tot_gr_act = tot_gr_act;
	}
	
	
}
