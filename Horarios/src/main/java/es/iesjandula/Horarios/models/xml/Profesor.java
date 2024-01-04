package es.iesjandula.Horarios.models.xml;

import java.util.ArrayList;
import java.util.List;

import es.iesjandula.Horarios.models.RolReaktor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor
{
	private int id;
	
	private String abreviatura;
	
	private String nombre;
	
	private String cuentaDeCorreo;
	
	private List<RolReaktor> listaRoles;

	/**
	 * @param id
	 * @param abreviatura
	 * @param nombre
	 */
	public Profesor(int id, String abreviatura, String nombre)
	{
		this.id = id;
		this.abreviatura = abreviatura;
		this.nombre = nombre;
		this.cuentaDeCorreo = "";
		this.listaRoles = new ArrayList<RolReaktor>();
	}
	
	
	
}
