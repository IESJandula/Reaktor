package es.iesjandula.Horarios.models.xml;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoHorario implements Comparable<TramoHorario>
{

	private int id;
	private int dia;
	private Date horaInicio;
	private Date horaFinal;
	@Override
	public int compareTo(TramoHorario o)
	{
		
		if (this.dia == o.dia)
		{
			return this.horaInicio.compareTo(o.horaInicio);
		}else {
			
			return this.dia - o.dia;
			
		}
		
		
	}
	
}
