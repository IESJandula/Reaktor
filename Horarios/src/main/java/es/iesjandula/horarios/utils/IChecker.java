package es.iesjandula.horarios.utils;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.csv.ModelCSV;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias
 *
 */
public interface IChecker 
{
	/**Class logger */
	static Logger log = LogManager.getLogger();
	/**
	 * Metodo que comprueba que el parametro email no sea nulo
	 * @param email
	 * @throws HorarioError
	 */
	public default void checkParam(String email,List<ModelCSV> modelos) throws HorarioError
	{
		//Comrpobamos que el email no sea nulo
		if(email.isEmpty())
		{
			throw new HorarioError(7,"El parametro email no puede venir vacio");
		}
		//Comprobamos que el email exista en la lista de modelos
		int count = 0;
		boolean encontrado = false;
		while(count<modelos.size())
		{
			//En el momento que lo encuentre se sale del bucle y no ocurre nada
			if(modelos.get(count).getEmail().equals(email))
			{
				encontrado = true;
				break;
			}
			count++;
		}
		//Si no lo encuentra arroja una excepcion
		if(!encontrado)
		{
			throw new HorarioError(8,"El email "+email+" no existe en la lista de modelos");
		}
	}
	/**
	 * Metodo que busca un rol mediante un email, este metodo se ejecuta despues de checkParam 
	 * por lo que si o si devuelve una lista de roles
	 * @param email
	 * @param modelos
	 * @return el rol o roles encontrado
	 * @see #checkParam(String, List)
	 */
	public default String [] searchRol(String email,List<ModelCSV>modelos)
	{
		int count = 0;
		String [] roles = null;
		while(count<modelos.size())
		{
			//En el momento que lo encuentre se sale del bucle y no ocurre nada
			if(modelos.get(count).getEmail().equals(email))
			{
				roles = modelos.get(count).getRoles();
				break;
			}
			count++;
		}
		return roles;
	}
}