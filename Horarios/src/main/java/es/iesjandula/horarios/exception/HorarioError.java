package es.iesjandula.horarios.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pablo Ruiz Canovas
 */
public class HorarioError extends Exception
{

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -7407451954907017825L;
	
	/**Codigo del error */
	private int codigo;
	
	/**Mensaje de informacion del error*/
	private String mensaje;

	/**
	 * Constructor que crea la excepcion para documentar los errores del proyecto
	 * @param codigo del error
	 * @param mensaje de informacion del error
	 */
	public HorarioError(int codigo,String mensaje)
	{
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
	/**
	 * Metodo que devuelve un mapa con la informacion del error
	 * @return el mapa con el codigo y mensaje como atributo
	 */
	public Map<String,Object> getBodyMessageException()
	{
		Map<String,Object> error = new HashMap<String, Object>();
		
		//Se añade el codigo como atributo
		error.put("codigo", codigo);
		
		//Se añade el mensaje como atributo
		error.put("mensaje",mensaje);
		
		return error;
	}
}
