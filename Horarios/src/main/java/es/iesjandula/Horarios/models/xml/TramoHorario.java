package es.iesjandula.Horarios.models.xml;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoHorario
{

	private int id;
	private int dia;
	private Date horaInicio;
	private Date horaFinal;
	
}
