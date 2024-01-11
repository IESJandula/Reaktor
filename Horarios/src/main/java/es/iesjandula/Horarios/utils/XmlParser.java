package es.iesjandula.Horarios.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.Student;
import es.iesjandula.Horarios.models.xml.Asignatura;
import es.iesjandula.Horarios.models.xml.Aula;
import es.iesjandula.Horarios.models.xml.Datos;
import es.iesjandula.Horarios.models.xml.Grupo;
import es.iesjandula.Horarios.models.xml.InfoCentro;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.TramoHorario;
import es.iesjandula.Horarios.models.xml.horarios.Actividad;
import es.iesjandula.Horarios.models.xml.horarios.Horarios;

public class XmlParser
{

	final static Logger logger = LogManager.getLogger();

	public InfoCentro parseDataFromXmlFile(MultipartFile file) throws HorarioError
	{

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;

		try
		{
			String content = file.getResource().getContentAsString(Charset.defaultCharset());

			documentBuilder = documentBuilderFactory.newDocumentBuilder();

			InputSource inputSource = new InputSource(new java.io.StringReader(content));

			Document document = documentBuilder.parse(inputSource);

			Element rootElement = document.getDocumentElement();

			Datos datos = this.parseDatos(rootElement);

			Horarios horarios = this.parseHorarios(rootElement, datos);

			return new InfoCentro(datos, horarios);
		} catch (ParserConfigurationException parserConfigurationException)
		{
			String message = "Error";
			logger.error(message, parserConfigurationException);
			throw new HorarioError(1, message, parserConfigurationException);
		} catch (SAXException saxException)
		{
			String message = "Error";
			logger.error(message, saxException);
			throw new HorarioError(2, message, saxException);
		} catch (IOException ioException)
		{
			String message = "Error";
			logger.error(message, ioException);
			throw new HorarioError(3, message, ioException);
		}
	}

	private Datos parseDatos(Element datos)
	{

		NodeList nodesAsignaturas = datos.getElementsByTagName("ASIGNATURA");
		Map<Integer, Asignatura> asignaturas = this.parseAsignaturas(nodesAsignaturas);

		NodeList nodesGrupos = datos.getElementsByTagName("GRUPO");
		Map<Integer, Grupo> GRUPOS = this.parseGrupos(nodesGrupos);

		NodeList nodesAulas = datos.getElementsByTagName("AULA");
		Map<Integer, Aula> aulas = this.parseAulas(nodesAulas);

		NodeList nodesProfesores = datos.getElementsByTagName("PROFESOR");
		Map<Integer, Profesor> profesores = this.parseProfesores(nodesProfesores);

		NodeList nodesTramos = datos.getElementsByTagName("TRAMO");
		Map<Integer, TramoHorario> tramos = this.parseTramos(nodesTramos);
		
		List<Student> alumnos = new ArrayList<Student>(List.of(
				new Student("Juan", "Sutil", "1234", "1ESOA"),
				new Student("Manuel", "Martin", "1111", "2ESOC"),
				new Student("Alejandro", "Cazalla", "2222", "3ESOA"),
				new Student("Alvaro", "Marmol", "3333", "4ESOA")		
				));
		
		return new Datos(asignaturas, GRUPOS, aulas, profesores, tramos, alumnos);
	}

	private Map<Integer, Asignatura> parseAsignaturas(NodeList list)
	{
		Map<Integer, Asignatura> asignaturas = new TreeMap<Integer, Asignatura>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_as").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			asignaturas.put(id, new Asignatura(id, abreviatura, nombre));
		}

		return asignaturas;
	}

	private Map<Integer, Grupo> parseGrupos(NodeList list)
	{
		Map<Integer, Grupo> grupos = new TreeMap<Integer, Grupo>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element grupo = (Element) list.item(i);

			int id = Integer.valueOf(grupo.getAttributes().getNamedItem("num_int_gr").getTextContent());
			String abreviatura = grupo.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = grupo.getAttributes().getNamedItem("nombre").getTextContent();

			grupos.put(id, new Grupo(id, abreviatura, nombre));
		}

		return grupos;
	}

	private Map<Integer, Aula> parseAulas(NodeList list)
	{
		Map<Integer, Aula> aulas = new TreeMap<Integer, Aula>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_au").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			aulas.put(id, new Aula(id, abreviatura, nombre));
		}

		return aulas;
	}

	private Map<Integer, Profesor> parseProfesores(NodeList list)
	{
		Map<Integer, Profesor> profesores = new TreeMap<Integer, Profesor>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_pr").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			profesores.put(id, new Profesor(id, abreviatura, nombre));
		}

		return profesores;
	}

	@SuppressWarnings("deprecation")
	private Map<Integer, TramoHorario> parseTramos(NodeList list)
	{
		Map<Integer, TramoHorario> tramos = new TreeMap<Integer, TramoHorario>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element tramo = (Element) list.item(i);

			int id = Integer.valueOf(tramo.getAttributes().getNamedItem("num_tr").getTextContent());
			int dia = Integer.valueOf(tramo.getAttributes().getNamedItem("numero_dia").getTextContent());
			String[] hI = tramo.getAttributes().getNamedItem("hora_inicio").getTextContent().split(":");
			String[] hF = tramo.getAttributes().getNamedItem("hora_final").getTextContent().split(":");
			Date horaInicio = new Date();
			Date horaFinal = new Date();
			horaInicio.setHours(Integer.valueOf(hI[0].strip()));
			horaInicio.setMinutes(Integer.valueOf(hI[1].strip()));
			
			horaFinal.setHours(Integer.valueOf(hF[0].strip()));
			horaFinal.setMinutes(Integer.valueOf(hF[1].strip()));
			tramos.put(id, new TramoHorario(id, dia, horaInicio, horaFinal));

		}

		return tramos;
	}

	private Horarios parseHorarios(Element horarios, Datos datos)
	{

		NodeList nodesHorariosAsignaturas = horarios.getElementsByTagName("HORARIO_ASIG");
		Map<Asignatura, List<Actividad>> horariosAsignaturas = this.parseHorariosAsignaturas(nodesHorariosAsignaturas,
				datos);

		NodeList nodesHorariosGrupos = horarios.getElementsByTagName("HORARIO_GRUP");
		Map<Grupo, List<Actividad>> horariosGrupos = this.parseHorariosGrupo(nodesHorariosGrupos, datos);

		NodeList nodesHorariosAulas = horarios.getElementsByTagName("HORARIO_AULA");
		Map<Aula, List<Actividad>> horariosAulas = this.parseHorariosAulas(nodesHorariosAulas, datos);

		NodeList nodesHorariosProfesores = horarios.getElementsByTagName("HORARIO_PROF");
		Map<Profesor, List<Actividad>> horariosProfesores = this.parseHorariosProfesores(nodesHorariosProfesores,
				datos);

		return new Horarios(horariosAsignaturas, horariosGrupos, horariosAulas, horariosProfesores);
	}

	private Map<Asignatura, List<Actividad>> parseHorariosAsignaturas(NodeList list, Datos datos)
	{

		Map<Asignatura, List<Actividad>> horariosAsignaturas = new HashMap<Asignatura, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioAsignatura = (Element) list.item(i);

			int idAsignatura = Integer
					.valueOf(horarioAsignatura.getAttributes().getNamedItem("hor_num_int_as").getTextContent());

			Asignatura asignatura = datos.getAsignaturas().get(idAsignatura);
			
			NodeList actividades = horarioAsignatura.getElementsByTagName("ACTIVIDAD");

			List<Actividad> listActividades = new ArrayList<Actividad>();
			
			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element elementActividad = (Element) actividades.item(j);
				
				Actividad actividad = this.createActividad(elementActividad, datos);

				actividad.setAsignatura(asignatura);
				
				listActividades.add(actividad);
			}

			horariosAsignaturas.put(asignatura, listActividades);
		}
		return horariosAsignaturas;
	}

	private Map<Grupo, List<Actividad>> parseHorariosGrupo(NodeList list, Datos datos)
	{

		Map<Grupo, List<Actividad>> horariosGrupos = new HashMap<Grupo, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioGrupo = (Element) list.item(i);

			int idGrupo = Integer.valueOf(horarioGrupo.getAttributes().getNamedItem("hor_num_int_gr").getTextContent());
			Grupo grupo = datos.getGrupos().get(idGrupo);

			NodeList actividades = horarioGrupo.getElementsByTagName("ACTIVIDAD");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element elementActividad = (Element) actividades.item(j);
				
				Actividad actividad = this.createActividad(elementActividad, datos);

				actividad.setGrupo(grupo);
				
				listActividades.add(actividad);
			}

			horariosGrupos.put(grupo, listActividades);
		}
		return horariosGrupos;
	}

	private Map<Aula, List<Actividad>> parseHorariosAulas(NodeList list, Datos datos)
	{

		Map<Aula, List<Actividad>> horariosAulas = new HashMap<Aula, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioAula = (Element) list.item(i);

			int idAula = Integer.valueOf(horarioAula.getAttributes().getNamedItem("hor_num_int_au").getTextContent());

			Aula aula = datos.getAulas().get(idAula);

			NodeList actividades = horarioAula.getElementsByTagName("ACTIVIDAD");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element elementActividad = (Element) actividades.item(j);
				
				Actividad actividad = this.createActividad(elementActividad, datos);

				actividad.setAula(aula);
				
				listActividades.add(actividad);
			}

			horariosAulas.put(aula, listActividades);
		}
		return horariosAulas;
	}

	private Map<Profesor, List<Actividad>> parseHorariosProfesores(NodeList list, Datos datos)
	{

		Map<Profesor, List<Actividad>> horariosProfesor = new HashMap<Profesor, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioProfesor = (Element) list.item(i);

			int idProfesor = Integer.valueOf(horarioProfesor.getAttributes().getNamedItem("hor_num_int_pr").getTextContent());
			Profesor profesor = datos.getProfesores().get(idProfesor);

			NodeList actividades = horarioProfesor.getElementsByTagName("ACTIVIDAD");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element elementActividad = (Element) actividades.item(j);
				
				Actividad actividad = this.createActividad(elementActividad, datos);

				actividad.setProfesor(profesor);
				
				listActividades.add(actividad);
			}

			horariosProfesor.put(profesor, listActividades);
		}
		return horariosProfesor;
	}

	private Actividad createActividad(Element element, Datos datos) {
		
		Asignatura asignatura = null;
		if (element.hasAttribute("asignatura"))
		{
			int idAsignatura = Integer
					.valueOf(element.getAttributes().getNamedItem("asignatura").getTextContent());
			asignatura = datos.getAsignaturas().get(idAsignatura);
		}
		
		Grupo grupo = null;
		Element grupoActividad = (Element) element.getElementsByTagName("GRUPOS_ACTIVIDAD").item(0);
		
		if (grupoActividad != null && grupoActividad.hasAttribute("grupo_1"))
		{
			int idGrupo = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
			grupo = datos.getGrupos().get(idGrupo);
		}

		Profesor profesor = null;
		if(element.hasAttribute("profesor")) {
			int idProfesor = Integer
					.valueOf(element.getAttributes().getNamedItem("profesor").getTextContent());
			profesor = datos.getProfesores().get(idProfesor);
		}
		
		TramoHorario tramo = null;
		if (element.hasAttribute("tramo"))
		{
			int idTramo = Integer.valueOf(element.getAttributes().getNamedItem("tramo").getTextContent());
			tramo = datos.getTramos().get(idTramo);
		}
		

		Aula aula = null;
		if (element.hasAttribute("aula"))
		{
			int idAula = Integer.valueOf(element.getAttributes().getNamedItem("aula").getTextContent());
			aula =  datos.getAulas().get(idAula);
		}
		
		return new Actividad(asignatura, grupo, profesor, tramo, aula);
	}
	
}
