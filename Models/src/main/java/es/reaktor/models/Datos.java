package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Datos
{
	private Asignatura asignatura;
	private Grupo grupo;
	private Aula aula;
	private Profesor profesor;
	private Tramo tramo;
}
