package es.iesjandula.horarios.models.xml;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Javier Martínez Megías
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horarios
{
	/**lista de Horarios de asignaturas**/
	private List<TipoHorario> horarioAsignatura;
	/**lista de Horarios de grupo**/
	private List<TipoHorario> horarioGrupo;
	/**lista de Horarios de aula**/
	private List<TipoHorario> horarioAulas;
	/**lista de Horarios de profesores**/
	private List<TipoHorario> horarioProfesores;
}
