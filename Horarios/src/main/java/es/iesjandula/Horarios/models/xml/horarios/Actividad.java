package es.iesjandula.Horarios.models.xml.horarios;


import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.Asignatura;
import es.iesjandula.Horarios.models.xml.Aula;
import es.iesjandula.Horarios.models.xml.Grupo;
import es.iesjandula.Horarios.models.xml.TramoHorario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actividad
{
	private Asignatura asignatura;
	private Grupo grupo;
	private Profesor profesor;
	private TramoHorario tramo;
	private Aula aula;
}
