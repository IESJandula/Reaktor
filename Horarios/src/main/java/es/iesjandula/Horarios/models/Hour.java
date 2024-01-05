package es.iesjandula.Horarios.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hour
{
	private String hour;
	
	private Date start;
	
	private Date end;
}
