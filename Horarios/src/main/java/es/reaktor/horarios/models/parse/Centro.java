package es.reaktor.horarios.models.parse;


import lombok.Data;

/**
 * @author David Martinez
 *
 */
@Data
public class Centro
{
	/** Attribute datos */
	private Datos datos;

	/** Attribute horarios */
	private Horarios horarios;

	/** Attribute nombreCentro */
	private String nombreCentro;

	/** Attribute autor */
	private String autor;

	/** Attribute fecha */
	private String fecha;

	public Datos getDatos()
	{
		return datos;
	}
}
