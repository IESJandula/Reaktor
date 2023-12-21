package es.iesjandula.Horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aula
{
	private int id;
	private String abreviatura;
	private String nombre;
}
