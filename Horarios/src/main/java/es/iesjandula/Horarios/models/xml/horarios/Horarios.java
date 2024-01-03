package es.iesjandula.Horarios.models.xml.horarios;

import java.util.List;
import java.util.Map;

import es.iesjandula.Horarios.models.xml.Profesor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import es.iesjandula.Horarios.models.xml.Asignatura;
import es.iesjandula.Horarios.models.xml.Aula;
import es.iesjandula.Horarios.models.xml.Grupo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horarios
{

	Map<Asignatura, List<Actividad>> HorariosAsignaturas;
	Map<Grupo, List<Actividad>> HorariosGrupos;
	Map<Aula, List<Actividad>> HorariosAulas;
	Map<Profesor, List<Actividad>> HorariosProfesores;
	
}
