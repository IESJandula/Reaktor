package es.iesjandula.horarios.rest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.xml.Actividad;
import es.iesjandula.horarios.models.xml.Aula;
import es.iesjandula.horarios.models.xml.Centro;
import es.iesjandula.horarios.models.xml.Profesor;
import es.iesjandula.horarios.models.xml.TipoHorario;
import es.iesjandula.horarios.models.xml.Tramo;
import es.iesjandula.horarios.utils.ParserXml;

/**
 * 
 * @author Pablo Ruiz Canovas, Javier Martinez Megias, Juan Alberto Jurado Valdivia
 *
 */
@RestController
@RequestMapping( value = "/horarios",produces = "application/json")
public class RestHandlerHorarios implements ParserXml 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	private static Centro centro= null;
	/**
	 * Constructor por defecto
	 */
	public RestHandlerHorarios() 
	{	
		
	}
	
	/**
	 * 
	 * @param xml
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = "multipart/form-data")
    public ResponseEntity<?> parseXML(
            @RequestPart MultipartFile xml)
    {
		try
		{
			if(chekIfEmptyAndXml(xml))
			{
				centro = this.parserFileToObject(xml);
				return ResponseEntity.ok(centro.getDatos());
	    	}
			else
			{
				throw new HorarioError(1,"El fichero tiene que ser xml");
			}
		}
		catch (HorarioError exception)
		{
			String error = "Is not a xml or is empty";
			HorarioError horarioError = new HorarioError(404, error);
			return ResponseEntity.status(404).body(horarioError.getBodyMessageException());
		}
        catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }

	/**
	 * @param xml
	 * @return
	 */
	private boolean chekIfEmptyAndXml(MultipartFile xml)
	{
		if(!xml.isEmpty())
		{
			
				return true;
			
		}
		return false;
	}
	
	/**
	 * 
	 * @param profesorName
	 * @param profesorSurname
	 * @param dia
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/classroom", produces = "multipart/form-data")
    public ResponseEntity<?> getClassroomTeacher(
            @RequestHeader(value= "profesorName", required = true) String profesorName,
            @RequestHeader (value= "profesorSurname", required = true)String profesorSurname,
            @RequestHeader (value= "dia", required = false)Integer dia)
    {
		try
		{		
				return ResponseEntity.ok(getAulaByProfesorName(profesorName, profesorSurname, dia));    	
		}
		catch (Exception exception)
		{     	
			log.error(exception.getMessage());
			HorarioError horarioError = new HorarioError(500, exception.getMessage());
			return ResponseEntity.status(500).body(horarioError.getBodyMessageException());
		}
    }
	
	
	private String getAulaByProfesorName(String name, String surneme, Integer dia)
	{	
		String horaActual = getActualHour();
		
		if(dia == null)
		{
			LocalDate date = LocalDate.now()  ;
			DayOfWeek day = date.getDayOfWeek();
			dia = day.getValue();
		}
		
		List<Profesor> listaProfesores = centro.getDatos().getProfesor();
		List<Tramo> listaTramos = centro.getDatos().getTramo();
		List<TipoHorario> listaHorarioProf = centro.getDatos().getHorarios().getHorarioProfesores();
		List<Aula> listaAulas = centro.getDatos().getAula();
		
		int numeroProfesor = 0;
		int numeroTramo = 0;
		int aula = 0;
		for(Profesor x : listaProfesores)
		{
			if(x.getNombre().equalsIgnoreCase(surneme+", "+name))
			{
				numeroProfesor=x.getNum_int_pr();
			}
		}
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
		for(TipoHorario x :  listaHorarioProf)
		{
			if(x.getHor_num_int_typo() == numeroProfesor)
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
		for(Aula x : listaAulas)
		{
			if(x.getNum_int_au() == aula)
			{
				return x.getNombre();
			}
		}
		
		
		return "No se ecuentra en ningun aula";	
		
	}

	private String getActualHour()
	{
		
		LocalDateTime locaDate = LocalDateTime.now();
		int hours  = locaDate.getHour();
		int minutes = locaDate.getMinute();
		
		return  hours  +":"+ minutes ;
	}
}
