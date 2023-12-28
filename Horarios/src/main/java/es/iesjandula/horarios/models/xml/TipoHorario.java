package es.iesjandula.horarios.models.xml;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Javier Martínez Magías
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoHorario
{
	/**Numero de hora asistidas**/
	private int hor_num_int_tipo;
	/**Numero de un**/
	private int tot_un;
	/**Numero de actividades**/
	private int tot_ac;
	/**Numero de hora asistidas**/
	private List<Actividad> actividades;
}
