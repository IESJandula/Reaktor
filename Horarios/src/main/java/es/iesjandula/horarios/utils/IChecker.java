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
	public default Aula getAulaByProfesorName(String name, String surneme, Integer dia, Centro centro, String hora)
	{	
		//Obtencion de la hora actual
		String horaActual = hora==null ?  this.getActualHour() : hora;
		
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
				return x;
			}
		}
		
		//En caso de no encontrar nada se devuelve este valor "nulo"
		return null;	
		
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
	
	public default void checkNameSurnameDay(String name,String surname,int dia,  List<Profesor> listaProfesores) throws HorarioError
	{
		//Comprobamos que el nombre o apellido no esten vacios
		if (name.isEmpty() || surname.isEmpty())
		{
			log.error("El nombre y apellidos estan vacios");
			throw new HorarioError(9, "El nombre o el apellido estan vacios");		
		}
		//Comprobamos que los dias esten bien puestos
		else if(dia > 5 || dia < 1)
		{
			log.error("El dia "+dia+" no se encuentra en el ranfo de 1 y 5");
			throw new HorarioError(10, "El numero elegido no es un dia de la semana");	
		}
		else 
		{
			//Buscamos al profesor por su nombre y apellido
			boolean encontrado = false;
			for(Profesor x : listaProfesores)
			{
				if(x.getNombre().equals(surname+", "+name))
				{
					encontrado = true;
				}
			}
			//Si no lo encuentra devuelve un error
			if(!encontrado)
			{
				log.error("No se ha encontrado al profesor");
				throw new HorarioError(11, "No se ha encontrado el profesor");
			}
		}
		
	}
	
	/**
	 * Metodo que comprueba que la hora este en el formato correcto %%:%%
	 * @param hora
	 * @throws HorarioError
	 */
	public default void checkHora(String hora) throws HorarioError
	{
		
		try
		{
			//Separamos la hora
			String [] splitHora = hora.split(":");
			splitHora[1] = splitHora[1].substring(0);
			//Convertimos la hora y minutos a enteros
			Integer numHora = Integer.parseInt(splitHora[0]);
			Integer numMinutos = Integer.parseInt(splitHora[1]);
			//Comprobamos que el array este bien compuesto
			if(splitHora.length!=2)
			{
				log.error("El array de horas no cumple la longitud establecida que son 2");
				throw new HorarioError(12,"El formato de la hora esta mal introducido, el formato debe de ser hh:mm");
			}
			//Comprobamos que la hora esta bien introducida
			else if(numHora>14 || numHora<8)
			{
				log.error("La hora "+numHora+" no entra en el rango de 8 a 14");
				throw new HorarioError(13,"La hora"+numHora+" no coincide con la establecida en el centro de 8 a 14");
			}
			//Comprobamos que los minutos esten bien introducidos
			else if(numMinutos>60 || numMinutos<1)
			{
				log.error("Los minutos "+numMinutos+ "no coinciden en el rango de 0 a 60");
				throw new HorarioError(14,"Los minutos estan mal introducidos");
			}
		}
		catch(IndexOutOfBoundsException ex)
		{
			log.error("Error al parsear la hora, hay menos datos de los previstos");
			throw new HorarioError(15,"El formato de la hora esta mal introducido, el formato debe de ser hh:mm");
		}
		catch(NumberFormatException ex)
		{
			log.error("Error al parsear las horas a formato entero",ex);
			throw new HorarioError(16,"La hora esta en un formato incorrecto");
		}
		
	}
}