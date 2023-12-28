package es.iesjandula.horarios.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.csv.ModelCSV;
import es.iesjandula.horarios.models.xml.Actividad;
import es.iesjandula.horarios.models.xml.Aula;
import es.iesjandula.horarios.models.xml.Centro;
import es.iesjandula.horarios.models.xml.Profesor;
import es.iesjandula.horarios.models.xml.TipoHorario;
import es.iesjandula.horarios.models.xml.Tramo;

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
	 * @author Pablo Ruiz Canovas
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
	 * @author Pablo Ruiz Canovas
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
	/**
	 * Metodo que mediante un nombre,apellido,dia y centro encuentra un aula y devuelve su valor
	 * @param name
	 * @param surneme
	 * @param dia
	 * @param centro
	 * @return el aula encontrada o un valor nulo si no la encuentra
	 * @author Javier Martinez Megias
	 */
	public default String getAulaByProfesorName(String name, String surneme, Integer dia, Centro centro)
	{	
		//Obtencion de la hora actual
		String horaActual = this.getActualHour();
		//Si el dia es nulo cogemos el dia del sistema
		if(dia == null)
		{
			LocalDate date = LocalDate.now()  ;
			DayOfWeek day = date.getDayOfWeek();
			dia = day.getValue();
		}
		
		//Obtencion de las listas de profesores, tramos, horarios profesores y aulas del xml
		List<Profesor> listaProfesores = centro.getDatos().getProfesor();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		List<TipoHorario> listaHorarioProf = centro.getDatos().getHorarios().getHorarioProfesores();
		List<Aula> listaAulas = centro.getDatos().getAula();
		
		//Variables que representan las claves a buscar de los profesores, tramos y aulas
		int numeroProfesor = 0;
		int numeroTramo = 0;
		int aula = 0;
		
		//Obtencion de la clave de un profesor
		for(Profesor x : listaProfesores)
		{
			if(x.getNombre().equalsIgnoreCase(surneme+", "+name))
			{
				numeroProfesor=x.getNum_int_pr();
			}
		}
		
		//Obtencion de la clave de un tramo
		for(Tramo x : listaTramos)
		{
			//Obtencion de la hora final de un tramo horario
			String horaFinal = x.getHora_final();
			//Obtencion de la hora inicial de un tramo horario
			String horaInicio = x.getHora_inicio();
			//Se compara la hora actual con los tramos para comprobar que se encuetra en los tramos indicados
			int menorHoraFinal = horaFinal.compareTo(horaActual);
			int mayorHoraInicial = horaInicio.compareTo(horaActual);
			if(menorHoraFinal >= 0 && mayorHoraInicial <= 0 && x.getNumero_dia() == dia)
			{
				numeroTramo = x.getNum_tr();
			}
		}
		//Obtencion de la clave del aula usando el profesor y el tramo
		for(TipoHorario x :  listaHorarioProf)
		{
			if(x.getHor_num_int_tipo() == numeroProfesor)
			{
				for(Actividad y : x.getActividades())
				{
					if(y.getTramo() == numeroTramo)
					{
						aula = y.getProfesorOAula();
					}
				}
			}
		}
		//Obtencion del nombre del aula usando su clave
		for(Aula x : listaAulas)
		{
			if(x.getNum_int_au() == aula)
			{
				return x.getNombre();
			}
		}
		
		//En caso de no encontrar nada se devuelve este valor "nulo"
		return "No se ecuentra en ningun aula";	
		
	}
	
	/**
	 * Metodo que devuelve la hora y minutos en formato %%:%%
	 * @return la hora y minutos actuales 
	 * @author Javier Martinez Megias
	 */
	private String getActualHour()
	{
		
		LocalDateTime locaDate = LocalDateTime.now();
		int hours  = locaDate.getHour();
		int minutes = locaDate.getMinute();
		
		return  hours  +":"+ minutes ;
	}
}