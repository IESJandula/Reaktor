package es.iesjandula.horarios.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.Alumno;
import es.iesjandula.horarios.models.TramoBathroom;
import es.iesjandula.horarios.models.csv.ModelCSV;
import es.iesjandula.horarios.models.xml.Actividad;
import es.iesjandula.horarios.models.xml.Asignatura;
import es.iesjandula.horarios.models.xml.Aula;
import es.iesjandula.horarios.models.xml.Centro;
import es.iesjandula.horarios.models.xml.Grupo;
import es.iesjandula.horarios.models.xml.Profesor;
import es.iesjandula.horarios.models.xml.TipoHorario;
import es.iesjandula.horarios.models.xml.Tramo;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
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
	 * Metodo que carga las aulas del xml en una lista de mapas
	 * @param centro
	 * @return lista de mapas con clave nombre y aula y el valor el nombre del curso y el numero de aula
	 * @author Juan Alberto Jurado Valdivia
	 */
	public default List<Map<String,String>> cargarCursos(Centro centro)
	{
		List<Map<String,String>> listaCursos = new LinkedList<Map<String,String>>();
		Map<String,String> mapa = new HashMap<String,String>();
		//Obtenemos la lista de aulas
		List<Aula> aulas = centro.getDatos().getAula();
		//Iteramos las aulas
		for(Aula a:aulas)
		{
			//Introducimos los datos
			mapa.put("nombre", a.getNombre());
			mapa.put("aula", a.getAbreviatura());
			listaCursos.add(mapa);
			//Nos cargamos el mapa para guardar nuevos datos sin alterar la lista
			mapa = new HashMap<String,String>();
		}
		return listaCursos;	
	}
	/**
	 * Metodo que carga los alumnos de una lista de java a un array para posteriormente ordenarlos
	 * @param alumnos
	 * @return array de alumnos cargados
	 * @throws HorarioError
	 * @author Pablo Ruiz Canovas
	 */
	public default Alumno [] sortAlumno(List<Alumno> alumnos) throws HorarioError
	{
		Alumno [] array = new Alumno[0];
		//Comprobamos que la lista este cargada de alumnos
		if(alumnos.isEmpty())
		{
			log.error("No existen alumnos en el sistema");
			throw new HorarioError(9,"No se ha cargado ningun alumno en el sistema");
		}
		else
		{
			//Cargamos los alumnos en un array
			for(Alumno a: alumnos)
			{
				array = Arrays.copyOf(array, array.length+1);
				array[array.length-1] = a;
			}
		}
		return array;
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
	public default String getActualHour()
	{
		
		LocalDateTime locaDate = LocalDateTime.now();
		int hours  = locaDate.getHour();
		int minutes = locaDate.getMinute();
		
		return  hours  +":"+ minutes ;
	}
	/**
	 * Metodo 
	 * @return
	 */
	public default String getActualDay()
	{
		LocalDate date = LocalDate.now();
		return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear();
	}
	
	public default void checkNameSurnameDay(String name,String surname,int dia,  List<Profesor> listaProfesores) throws HorarioError
	{
		//Comprobamos que el nombre o apellido no esten vacios
		if (name.isEmpty() || surname.isEmpty())
		{
			log.error("El nombre y apellidos estan vacios");
			throw new HorarioError(10, "El nombre o el apellido estan vacios");		
		}
		//Comprobamos que los dias esten bien puestos
		else if(dia > 5 || dia < 1)
		{
			log.error("El dia "+dia+" no se encuentra en el ranfo de 1 y 5");
			throw new HorarioError(11, "El numero elegido no es un dia de la semana");	
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
				throw new HorarioError(12, "No se ha encontrado el profesor");
			}
		}
		
	}
	
	/**
	 * Metodo que comprueba que la hora este en el formato correcto %%:%%
	 * @param hora
	 * @throws HorarioError
	 * @author Pablo Ruiz Canovas
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
				throw new HorarioError(13,"El formato de la hora esta mal introducido, el formato debe de ser hh:mm");
			}
			//Comprobamos que la hora esta bien introducida
			else if(numHora>14 || numHora<8)
			{
				log.error("La hora "+numHora+" no entra en el rango de 8 a 14");
				throw new HorarioError(14,"La hora"+numHora+" no coincide con la establecida en el centro de 8 a 14");
			}
			//Comprobamos que los minutos esten bien introducidos
			else if(numMinutos>60 || numMinutos<1)
			{
				log.error("Los minutos "+numMinutos+ "no coinciden en el rango de 0 a 60");
				throw new HorarioError(15,"Los minutos estan mal introducidos");
			}
		}
		catch(IndexOutOfBoundsException ex)
		{
			log.error("Error al parsear la hora, hay menos datos de los previstos");
			throw new HorarioError(16,"El formato de la hora esta mal introducido, el formato debe de ser hh:mm");
		}
		catch(NumberFormatException ex)
		{
			log.error("Error al parsear las horas a formato entero",ex);
			throw new HorarioError(17,"La hora esta en un formato incorrecto");
		}
		
	}
	/**
	 * Metodo que comprueba que el curso este bien formado y que el curso exista
	 * @param alumnos
	 * @param curso
	 * @throws HorarioError
	 * @author Pablo Ruiz Canovas
	 */
	public default void checkCourse(List<Alumno> alumnos,String curso) throws HorarioError
	{
		//Booleano centinela que controla que se encuentre el valor
		boolean encontrado = false;
		//Comprobamos que existan alumnos en la lista
		if(alumnos.isEmpty())
		{
			log.error("No existen alumnos en el sistema");
			throw new HorarioError(9,"No se ha cargado ningun alumno en el sistema");
		}
		//Comprobamos que el curso este vacio
		if(curso.isEmpty())
		{
			log.error("El curso esta vacio");
			throw new HorarioError(18,"El curso no puede estar vacio");
		}
		else
		{
			//Si no esta vacio iteramos los alumnos
			for(Alumno a:alumnos)
			{
				//Si lo encuentra cambia el valor del booleano centinela
				if(a.getCurso().equals(curso))
				{
					encontrado = true;
				}
			}
		}
		//Si no ha encontrado ninguno lanza un error
		if(!encontrado)
		{
			log.error("El curso "+curso+" no existe en la lista");
			throw new HorarioError(19,"Curso no encontrado");
		}
	}
	/**
	 * Metodo que devuelve un array de alumnos de un curso pasado como parametro
	 * @param alumnos
	 * @param curso
	 * @return array de alumnos de un curso
	 * @author Pablo Ruiz Canovas
	 */
	public default Alumno [] getAlumnoByCourse(List<Alumno> alumnos,String curso)
	{
		Alumno [] array = new Alumno[0];
		for(Alumno a:alumnos)
		{
			if(curso.equals(a.getCurso()))
			{
				array = Arrays.copyOf(array, array.length+1);
				array[array.length-1] = a;
			}
		}
		return array;
	}
	
	/**
	 * Pasas el dia , la hora actual y la lista de tramos para obtener el numero de tramo
	 * @param dia
	 * @param horaActual
	 * @param listaTramos
	 * @param numeroTramo
	 * @return int
	 * @author Javier Martinez Megias
	 */
	private int getTramoHorario(Integer dia, String horaActual, List<Tramo> listaTramos)
	{
		int numeroTramo = 0;
		for(Tramo x : listaTramos)
		{
			String horaFinal = x.getHora_final();
			String horaInicio = x.getHora_inicio();
			int menorHoraFinal = horaFinal.compareTo(horaActual);
			int mayorHoraInicial = horaInicio.compareTo(horaActual);
			
			if(menorHoraFinal >= 0 && mayorHoraInicial <= 0 && x.getNumero_dia() == dia)
			{
				numeroTramo = x.getNum_tr();
			}
		}
		return numeroTramo;
	}

	/**
	 * Return the actual day
	 * @return integer
	 * @author Javier Martinez Megias
	 */
	private Integer getDay()
	{
		Integer dia;
		LocalDate date = LocalDate.now()  ;
		DayOfWeek day = date.getDayOfWeek();
		dia = day.getValue();
		return dia;
	}
	
	/**
	 * Se obrtiene el aula mediante el nombre del curso
	 * @param nombreDelCurso
	 * @return String
	 * @author Javier Martinez Megias
	 */
	public default String nombreAula(String nombreDelCurso,Centro centro)
	{
		//Se obtiene la hora y el dia
		String hora = this.getActualHour();
		int dia = this.getDay();
		
		List<TipoHorario> listaHorarios = centro.getDatos().getHorarios().getHorarioGrupo();
		List<Grupo> listaGrupos = centro.getDatos().getGrupo();
		List<Aula> listaAulas = centro.getDatos().getAula();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		Integer numeroTramo = 0;
		Integer numeroGrupo = 0;
		Integer numeroAula = 0;
		String nombreAula = "";
		String abreviatura = "";
		String result = "";
		
		//Se obtine el numero de tramo
		numeroTramo = getTramoHorario(dia, hora, listaTramos);
		
		//Se obtiene el numero de grupo
		for(Grupo grupo : listaGrupos)
		{
			if(nombreDelCurso.equals(grupo.getNombre()))
			{
				numeroGrupo = grupo.getNum_int_gr();
			}
		}
		
		//Se obtine el numero de aula
		for(TipoHorario tipoHorario : listaHorarios)
		{
			if(numeroGrupo == tipoHorario.getHor_num_int_tipo())
			{
				for(Actividad actividad : tipoHorario.getActividades())
				{
					if(actividad.getTramo() == numeroTramo)
					{
						numeroAula = actividad.getProfesorOAula();
						
					}
				}
			}
		}
		
		//Se obtiene el nombre del aula y su abreviatura
		for(Aula aula : listaAulas)
		{
			if(aula.getNum_int_au() == numeroAula)
			{
				nombreAula = aula.getNombre();
				abreviatura = aula.getAbreviatura();
			}
		}
		
		//Se comprueba que no sea vacio
		if(!nombreAula.equals("") && !abreviatura.equals(""))
		{
			result = "Nombre del Aula: "+nombreAula+"\nAula: "+abreviatura+"\nDia: "+dia+"\nHora: "+hora;
		}
		
				
		return result;
	}
	
	/**
	 * Obtienes el profesor y la asignatura que tiene ese grupo ese dia a esa hora
	 * @param nombreDelCurso
	 * @return String 
	 * @author Javier Martinez Megias
	 */
	public default String nombreProfesorAsignatura(String nombreDelCurso, Centro centro)
	{
		//Obtenemso el dia y la hora
		String hora = "10:15";
		int dia = 5;
		
		List<TipoHorario> listaHorarios = centro.getDatos().getHorarios().getHorarioGrupo();
		List<Grupo> listaGrupos = centro.getDatos().getGrupo();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		List<Profesor> listaProfesores = centro.getDatos().getProfesor();
		List<Asignatura> listaAsignaturas = centro.getDatos().getAsignatura();
		Integer numeroTramo = 0;
		Integer numeroGrupo = 0;
		Integer numeroProfesor = 0;
		Integer numeroAsignatura = 0;
		String nombreProfesor = "";
		String nombreAsignatura = "";
		String result = "";
		
		//Obtenemos el numero de tramo
		numeroTramo = getTramoHorario(dia, hora, listaTramos);
		
		//Obtenemso el grupo
		for(Grupo grupo : listaGrupos)
		{
			if(nombreDelCurso.equals(grupo.getNombre()) || nombreDelCurso.equals(grupo.getAbreviatura()))
			{
				numeroGrupo = grupo.getNum_int_gr();
			}
		}
		
		//Se obtienen el numero de profesor y asignatura
		for(TipoHorario tipoHorario : listaHorarios)
		{
			if(numeroGrupo == tipoHorario.getHor_num_int_tipo())
			{
				for(Actividad actividad : tipoHorario.getActividades())
				{
					if(actividad.getTramo() == numeroTramo)
					{
						numeroProfesor = actividad.getProfesorOAula();
						numeroAsignatura = actividad.getAulaOAsignatura();
					}
				}
			}
		}
		
		//obtenemos el nombre del profesor
		for(Profesor profesor : listaProfesores)
		{
			if(profesor.getNum_int_pr() == numeroProfesor)
			{
				nombreProfesor = profesor.getNombre();
			}
		}
		
		//obtenemos el nombre de la asignatura
		for(Asignatura asignatura : listaAsignaturas)
		{
			if(asignatura.getNum_int_as() == numeroAsignatura)
			{
				nombreAsignatura = asignatura.getNombre();
			}
		}
		
		//Comprueba que no sea vacio
		if(!nombreAsignatura.equals("") && !nombreProfesor.equals(""))
		{
			result = "Profesor: "+nombreProfesor+"\nAsignatura: "+nombreAsignatura+"\nDia: "+dia+"\nHora: "+hora;
		}
		
				
		return result;
	}
	/**
	 * Metodo que busca y comprueba si un alumno existe pasandole un nombre y un apellido
	 * @param nombre
	 * @param apellido
	 * @param alumnos
	 * @return el alumno encontrado o una excepcion si no lo encuentra
	 * @throws HorarioError
	 * @author Pablo Ruiz Canovas
	 */
	public default Alumno checkAlumno(String nombre,String apellido,List<Alumno> alumnos) throws HorarioError
	{
		Alumno alumno = null;
		if(nombre.isEmpty() || apellido.isEmpty())
		{
			log.error("El nombre y apellidos estan vacios");
			throw new HorarioError(10, "El nombre o el apellido estan vacios");	
		}
		else if(alumnos.isEmpty())
		{
			log.error("No existen alumnos en el sistema");
			throw new HorarioError(9,"No se ha cargado ningun alumno en el sistema");
		}
		else
		{
			boolean encontrado = false;
			int index = 0;
			while(index<alumnos.size())
			{
				Alumno a = alumnos.get(index);
				if(a.getNombre().equals(nombre) && a.getApellido().equals(apellido))
				{
					encontrado = true;
					alumno = a;
					break;
				}
				index++;
			}
			if(!encontrado)
			{
				log.error("Alumno "+apellido+" "+nombre+" no encontrado en el sistema");
				throw new HorarioError(20,"Alumno "+apellido+" "+nombre+" no encontrado en el sistema");
			}
		}
		return alumno;
	}
	/**
	 * Metodo que busca un profesor en la lista de modelo y coge su telefono y correo
	 * @param datos
	 * @param profesores
	 * @return Nombre, telefono y email de un profesor guardados en un mapa
	 */
	public default String getDatosProfesor(String datos,List<ModelCSV> profesores)
	{
		String datosProfe = "";
		//Los datos vienen en lineas, los separamos por lineas y cogemos solo la primera
		String [] splitDatos = datos.split("\n");
		//Quitamos los datos innecesarios solo nos interesa obtener el nombre
		datos = splitDatos[0].substring(10);
		int index = 0;
		//Iteramos la lista de modelos
		while(index<profesores.size())
		{
			ModelCSV modelo = profesores.get(index);
			//Volvemos a separar el nombre completo para dividirlo en dos partes
			splitDatos = datos.split(",");
			//Eliminamos el primer espacio sobrante
			splitDatos[1] = splitDatos[1].substring(1);
			//Comparamos y si coincide el nombre y apellido recogemos datos
			if(splitDatos[0].equals(modelo.getApellido()) && splitDatos[1].equals(modelo.getNombre()))
			{
				datosProfe = "Nombre completo del tutor: "+modelo.getNombre()+" "+modelo.getApellido()+"\n"
						+"Telefono de contacto: "+modelo.getTelefono()+"\n"
						+"Correo electronico: "+modelo.getEmail();
				
				
				break;
			}
			index++;
		}
		return datosProfe;
		
	}
	/**
	 * Metodo que reemplaza un alumno en la lista de alumnos
	 * @param alumno
	 * @param alumnos
	 * @return lista de alumnos actualizada
	 * @author Pablo Ruiz Canovas
	 */
	public default List<Alumno> reemplazarAlumno(Alumno alumno,List<Alumno> alumnos) throws HorarioError
	{
		int index = 0;
		while(index<alumnos.size())
		{
			Alumno a = alumnos.get(index);
			if(a.getDni().equals(alumno.getDni()) && a.getNombre().equals(alumno.getNombre()) && a.getApellido().equals(alumno.getApellido()))
			{
				alumnos.remove(index);
				alumnos.add(alumno);
				break;
			}
			index++;
		}
		return alumnos;
	}
	
	public default TramoBathroom buscarTramo(Alumno alumno,List<TramoBathroom> tramos) throws HorarioError
	{
		if(tramos.isEmpty())
		{
			log.error("Lista de tramos vacia");
			throw new HorarioError(21,"Lista de tramos vacia");
		}
		boolean encontrado = false;
		int index = 0;
		TramoBathroom t = null;
		while(index<tramos.size())
		{
			t = tramos.get(index);
			if(t.getAlumno().equals(alumno))
			{
				encontrado = true;
				break;
			}
			index++;
		}
		if(!encontrado)
		{
			log.error("No existe un tramo con este alumno: "+alumno);
			throw new HorarioError(22,"Tramo con el alumno "+alumno+" no encontrado");
		}
		return t;
	}
	/**
	 * Metodo que reemplaza un tramo antiguo por el nuevo actualizado
	 * @param tramo
	 * @param tramos
	 * @return Lista de tramos actualizada
	 * @author Pablo Ruiz Canovas
	 */
	public default List<TramoBathroom> reemplazarTramo(TramoBathroom tramo,List<TramoBathroom> tramos)
	{
		int index = 0;
		while(index<tramos.size())
		{
			TramoBathroom a = tramos.get(index);
			if(a.getAlumno().equals(tramo.getAlumno()))
			{
				tramos.remove(index);
				tramos.add(tramo);
				break;
			}
			index++;
		}
		return tramos;
	}
	/**
	 * Metodo que devuelve una lista de mapas con la informacion de los tramos horarios, dias e informacion del alumno
	 * @param fechaInicio
	 * @param tramoFin
	 * @param alumno
	 * @param tramos
	 * @return 
	 * @throws HorarioError
	 * @author Pablo Ruiz Canovas
	 */
	public default List<Map<String,String>>getTimesBathroom(String fechaInicio,String tramoFin,Alumno alumno,List<TramoBathroom> tramos) throws HorarioError
	{
		List<Map<String,String>> mapas = new LinkedList<>();
		Map<String,String> veces = new HashMap<>();
		int index = 0;
		while(index<tramos.size())
		{
			TramoBathroom t = tramos.get(index);
			
			int horaInicial = fechaInicio.compareTo(t.getTramoFinal());
            int horaFinal = tramoFin.compareTo(t.getTramoInicial());

            if(horaFinal <= 0 && horaInicial <= 0)
            {
            	veces.put("alumno",t.getAlumno().getNombre()+" "+t.getAlumno().getApellido());
                veces.put("dia", t.getDia());
                veces.put("hora_inicio", t.getTramoInicial());
                veces.put("hora_fin", t.getTramoFinal());
                mapas.add(veces);
        		veces = new HashMap<>();
            }
				
			index++;
		}
		return mapas;
	}
	
}