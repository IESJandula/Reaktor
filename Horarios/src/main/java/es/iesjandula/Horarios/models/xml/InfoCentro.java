package es.iesjandula.Horarios.models.xml;

import es.iesjandula.Horarios.models.xml.horarios.Horarios;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoCentro
{

	private Datos datos;
	private Horarios horarios;

}
