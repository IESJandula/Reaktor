package es.iesjandula.Horarios.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import es.iesjandula.Horarios.models.xml.Asignatura;
import es.iesjandula.Horarios.models.xml.Aula;
import es.iesjandula.Horarios.models.xml.Datos;
import es.iesjandula.Horarios.models.xml.Grupo;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.TramoHorario;

public class XmlParser
{

	final static Logger logger = LogManager.getLogger();

	public Datos parseDataFromXmlFile(MultipartFile file)
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
			
	/*<HORARIO_ASIG hor_num_int_as="1" tot_un="3" tot_ac="3">
        	<ACTIVIDAD num_act="1" num_un="73" tramo="15" aula="42" profesor="3">
          		<GRUPOS_ACTIVIDAD tot_gr_act="1" grupo_1="1" />
        	</ACTIVIDAD>
        	<ACTIVIDAD num_act="2" num_un="74" tramo="12" aula="42" profesor="3">
          		<GRUPOS_ACTIVIDAD tot_gr_act="1" grupo_1="29" />
        	</ACTIVIDAD>
        	<ACTIVIDAD num_act="3" num_un="75" tramo="34" aula="42" profesor="3">
          		<GRUPOS_ACTIVIDAD tot_gr_act="1" grupo_1="12" />
        	</ACTIVIDAD>
      	</HORARIO_ASIG>*/

		} catch (ParserConfigurationException parserConfigurationException)
		{
			String message = "Error";
			logger.error(message, parserConfigurationException);
		} catch (SAXException saxException)
		{
			String message = "Error";
			logger.error(message, saxException);
		} catch (IOException ioException)
		{
			String message = "Error";
			logger.error(message, ioException);
		}

		return new Datos();

	}

	private Datos parseDatos(Element datos) {
		
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

		for (int i = 0; i < list.getLength(); i++)
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

		for (int i = 0; i < list.getLength(); i++)
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

		for (int i = 0; i < list.getLength(); i++)
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

		for (int i = 0; i < list.getLength(); i++)
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

		for (int i = 0; i < list.getLength(); i++)
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
}
