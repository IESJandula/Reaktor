package es.iesjandula.Horarios.models.xml;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Datos
{
	private List<Asignatura> asignaturas;
	private List<Grupo> grupos;
	private List<Aula> aulas;
	private List<Profesor> profesores;
	private List<TramoHorario> tramos;
	
}
