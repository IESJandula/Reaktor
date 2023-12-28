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
	 * Numero de grupo 2
	 */
	private int grupo_3;
	/**
	 * Numero de grupo 2
	 */
	private int grupo_4;
	/**
	 * Numero de grupo 2
	 */
	private int grupo_5;
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
	/**
	 * @param tot_gr_act
	 * @param grupo_1
	 * @param grupo_2
	 */
	public GrupoActividad(int tot_gr_act, int grupo_1, int grupo_2)
	{
		this.tot_gr_act = tot_gr_act;
		this.grupo_1 = grupo_1;
		this.grupo_2 = grupo_2;
	}
	/**
	 * @param tot_gr_act
	 * @param grupo_1
	 * @param grupo_2
	 * @param grupo_3
	 */
	public GrupoActividad(int tot_gr_act, int grupo_1, int grupo_2, int grupo_3)
	{
		this.tot_gr_act = tot_gr_act;
		this.grupo_1 = grupo_1;
		this.grupo_2 = grupo_2;
		this.grupo_3 = grupo_3;
	}
	/**
	 * @param tot_gr_act
	 * @param grupo_1
	 * @param grupo_2
	 * @param grupo_3
	 * @param grupo_4
	 */
	public GrupoActividad(int tot_gr_act, int grupo_1, int grupo_2, int grupo_3, int grupo_4)
	{
		this.tot_gr_act = tot_gr_act;
		this.grupo_1 = grupo_1;
		this.grupo_2 = grupo_2;
		this.grupo_3 = grupo_3;
		this.grupo_4 = grupo_4;
	}
	
	
}
