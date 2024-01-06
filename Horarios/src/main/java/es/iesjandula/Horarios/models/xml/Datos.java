package es.iesjandula.Horarios.models.xml;

import java.util.List;
import java.util.Map;

import es.iesjandula.Horarios.models.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Datos
{
	private Map<Integer, Asignatura> asignaturas;
	private Map<Integer, Grupo> grupos;
	private Map<Integer, Aula> aulas;
	private Map<Integer, Profesor> profesores;
	private Map<Integer, TramoHorario> tramos;
	private List<Student> alumnos;
	
}
