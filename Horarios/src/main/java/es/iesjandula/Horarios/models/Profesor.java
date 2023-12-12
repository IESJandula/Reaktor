package es.iesjandula.Horarios.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profesor
{
	private String nombre;
	
	private String apellidos;
	
	private String cuentaDeCorreo;
	
	private List<RolReaktor> listaRoles;
}
