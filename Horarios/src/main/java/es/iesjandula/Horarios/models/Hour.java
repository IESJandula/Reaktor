package es.iesjandula.Horarios.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hour
{
	private String hour;
	
	private String start;
	
	private String end;
}
