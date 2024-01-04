package es.iesjandula.Horarios.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.xml.sax.SAXException;

import es.iesjandula.Horarios.exceptions.HorarioError;
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
			Document document = documentBuilder.parse(content);

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
		List<Asignatura> asignaturas = this.parseAsignaturas(nodesAsignaturas);

		NodeList nodesGrupos = datos.getElementsByTagName("GRUPO");
		List<Grupo> GRUPOS = this.parseGrupos(nodesGrupos);

		NodeList nodesAulas = datos.getElementsByTagName("AULA");
		List<Aula> aulas = this.parseAulas(nodesAulas);

		NodeList nodesProfesores = datos.getElementsByTagName("PROFESOR");
		List<Profesor> profesores = this.parseProfesores(nodesProfesores);

		NodeList nodesTramos = datos.getElementsByTagName("TRAMO");
		List<TramoHorario> tramos = this.parseTramos(nodesTramos);

		return new Datos(asignaturas, GRUPOS, aulas, profesores, tramos);
	}

	private List<Asignatura> parseAsignaturas(NodeList list)
	{
		List<Asignatura> asignaturas = new ArrayList<Asignatura>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_as").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			asignaturas.add(new Asignatura(id, abreviatura, nombre));
		}

		return asignaturas;
	}

	private List<Grupo> parseGrupos(NodeList list)
	{
		List<Grupo> grupos = new ArrayList<Grupo>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_as").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			grupos.add(new Grupo(id, abreviatura, nombre));
		}

		return grupos;
	}

	private List<Aula> parseAulas(NodeList list)
	{
		List<Aula> aulas = new ArrayList<Aula>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_as").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			aulas.add(new Aula(id, abreviatura, nombre));
		}

		return aulas;
	}

	private List<Profesor> parseProfesores(NodeList list)
	{
		List<Profesor> profesores = new ArrayList<Profesor>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element asignatura = (Element) list.item(i);

			int id = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_as").getTextContent());
			String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
			String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();

			profesores.add(new Profesor(id, abreviatura, nombre));
		}

		return profesores;
	}

	private List<TramoHorario> parseTramos(NodeList list)
	{
		List<TramoHorario> tramos = new ArrayList<TramoHorario>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element tramo = (Element) list.item(i);

			int id = Integer.valueOf(tramo.getAttributes().getNamedItem("num_tr").getTextContent());
			int dia = Integer.valueOf(tramo.getAttributes().getNamedItem("numero_dia").getTextContent());
			String hI = tramo.getAttributes().getNamedItem("hora_inicio").getTextContent();
			String hF = tramo.getAttributes().getNamedItem("hora_final").getTextContent();
			Date horaInicio;
			Date horaFinal;
			try
			{
				horaInicio = new SimpleDateFormat("HH:mm").parse(hI);
				horaFinal = new SimpleDateFormat("HH:mm").parse(hF);
				tramos.add(new TramoHorario(id, dia, horaInicio, horaFinal));

			} catch (ParseException e)
			{
				String message = "Error";
				logger.error(message, e);
			}

		}

		return tramos;
	}

	private Horarios parseHorarios(Element horarios, Datos datos)
	{

		NodeList nodesHorariosAsignaturas = horarios.getElementsByTagName("HORARIO_ASIG");
		Map<Asignatura, List<Actividad>> horariosAsignaturas = this.parseHorariosAsignaturas(nodesHorariosAsignaturas,
				datos);

		NodeList nodesHorariosGrupos = horarios.getElementsByTagName("HORARIO_GRUP");
		Map<Grupo, List<Actividad>> horariosGrupos= this.parseHorariosGrupo(nodesHorariosGrupos, datos);

		NodeList nodesHorariosAulas = horarios.getElementsByTagName("HORARIO_AULA");
		Map<Aula, List<Actividad>> horariosAulas= this.parseHorariosAulas(nodesHorariosAulas, datos);

		NodeList nodesHorariosProfesores = horarios.getElementsByTagName("HORARIO_PROF");
		Map<Profesor, List<Actividad>> horariosProfesores= this.parseHorariosProfesores(nodesHorariosProfesores, datos);

		return new Horarios(horariosAsignaturas, horariosGrupos, horariosAulas, horariosProfesores);
	}

	private Map<Asignatura, List<Actividad>> parseHorariosAsignaturas(NodeList list, Datos datos)
	{

		Map<Asignatura, List<Actividad>> horariosAsignaturas = new TreeMap<Asignatura, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioAsignatura = (Element) list.item(i);

			int idAsignatura = Integer
					.valueOf(horarioAsignatura.getAttributes().getNamedItem("num_int_as").getTextContent());

			Asignatura asignatura = datos.getAsignaturas().get(idAsignatura);

			NodeList actividades = horarioAsignatura.getElementsByTagName("Actividad");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element grupoActividad = (Element) horarioAsignatura.getElementsByTagName("GRUPO").item(0);

				int idGrupo = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
				Grupo grupo = datos.getGrupos().get(idGrupo);

				int idProfesor = Integer
						.valueOf(horarioAsignatura.getAttributes().getNamedItem("profesor").getTextContent());
				Profesor profesor = datos.getProfesores().get(idProfesor);

				int idTramo = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tramo").getTextContent());
				TramoHorario tramo = datos.getTramos().get(idTramo);

				int idAula = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("aula").getTextContent());
				Aula aula = datos.getAulas().get(idAula);

				listActividades.add(new Actividad(asignatura, grupo, profesor, tramo, aula));
			}

			horariosAsignaturas.put(asignatura, listActividades);
		}
		return horariosAsignaturas;
	}

	private Map<Grupo, List<Actividad>> parseHorariosGrupo(NodeList list, Datos datos)
	{

		Map<Grupo, List<Actividad>> horariosGrupos = new TreeMap<Grupo, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioGrupo = (Element) list.item(i);
			
			int idGrupo = Integer.valueOf(horarioGrupo.getAttributes().getNamedItem("hor_num_int_gr").getTextContent());
			Grupo grupo = datos.getGrupos().get(idGrupo);

			NodeList actividades = horarioGrupo.getElementsByTagName("Actividad");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{
				
				int idAsignatura = Integer
						.valueOf(horarioGrupo.getAttributes().getNamedItem("asignatura").getTextContent());
				Asignatura asignatura = datos.getAsignaturas().get(idAsignatura);

				int idProfesor = Integer
						.valueOf(horarioGrupo.getAttributes().getNamedItem("profesor").getTextContent());
				Profesor profesor = datos.getProfesores().get(idProfesor);

				int idTramo = Integer.valueOf(horarioGrupo.getAttributes().getNamedItem("tramo").getTextContent());
				TramoHorario tramo = datos.getTramos().get(idTramo);

				int idAula = Integer.valueOf(horarioGrupo.getAttributes().getNamedItem("aula").getTextContent());
				Aula aula = datos.getAulas().get(idAula);

				listActividades.add(new Actividad(asignatura, grupo, profesor, tramo, aula));
			}

			horariosGrupos.put(grupo, listActividades);
		}
		return horariosGrupos;
	}

	private Map<Aula, List<Actividad>> parseHorariosAulas(NodeList list, Datos datos)
	{

		Map<Aula, List<Actividad>> horariosAulas = new TreeMap<Aula, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioAula = (Element) list.item(i);

			int idAula = Integer
					.valueOf(horarioAula.getAttributes().getNamedItem("hor_num_int_au").getTextContent());

			Aula aula = datos.getAulas().get(idAula);

			NodeList actividades = horarioAula.getElementsByTagName("Actividad");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element grupoActividad = (Element) horarioAula.getElementsByTagName("GRUPO").item(0);

				int idGrupo = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
				Grupo grupo = datos.getGrupos().get(idGrupo);

				int idProfesor = Integer
						.valueOf(horarioAula.getAttributes().getNamedItem("profesor").getTextContent());
				Profesor profesor = datos.getProfesores().get(idProfesor);

				int idTramo = Integer.valueOf(horarioAula.getAttributes().getNamedItem("tramo").getTextContent());
				TramoHorario tramo = datos.getTramos().get(idTramo);

				int idAsignatura = Integer
						.valueOf(horarioAula.getAttributes().getNamedItem("asignatura").getTextContent());
				Asignatura asignatura = datos.getAsignaturas().get(idAsignatura);

				listActividades.add(new Actividad(asignatura, grupo, profesor, tramo, aula));
			}

			horariosAulas.put(aula, listActividades);
		}
		return horariosAulas;
	}

	private Map<Profesor, List<Actividad>> parseHorariosProfesores(NodeList list, Datos datos)
	{

		Map<Profesor, List<Actividad>> horariosAulas = new TreeMap<Profesor, List<Actividad>>();

		for (int i = 0 ; i < list.getLength() ; i++)
		{
			Element horarioProfesor = (Element) list.item(i);

			int idProfesor = Integer
					.valueOf(horarioProfesor.getAttributes().getNamedItem("profesor").getTextContent());
			Profesor profesor = datos.getProfesores().get(idProfesor);

			NodeList actividades = horarioProfesor.getElementsByTagName("Actividad");

			List<Actividad> listActividades = new ArrayList<Actividad>();

			for (int j = 0 ; j < actividades.getLength() ; j++)
			{

				Element grupoActividad = (Element) horarioProfesor.getElementsByTagName("GRUPO").item(0);

				int idGrupo = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
				Grupo grupo = datos.getGrupos().get(idGrupo);
				
				int idAula = Integer.valueOf(horarioProfesor.getAttributes().getNamedItem("aula").getTextContent());
				Aula aula = datos.getAulas().get(idAula);

				int idTramo = Integer.valueOf(horarioProfesor.getAttributes().getNamedItem("tramo").getTextContent());
				TramoHorario tramo = datos.getTramos().get(idTramo);

				int idAsignatura = Integer
						.valueOf(horarioProfesor.getAttributes().getNamedItem("asignatura").getTextContent());
				Asignatura asignatura = datos.getAsignaturas().get(idAsignatura);

				listActividades.add(new Actividad(asignatura, grupo, profesor, tramo, aula));
			}

			horariosAulas.put(profesor, listActividades);
		}
		return horariosAulas;
	}
	
}
