package es.iesjandula.horarios.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import es.iesjandula.horarios.exception.HorarioError;
import es.iesjandula.horarios.models.xml.Actividad;
import es.iesjandula.horarios.models.xml.Asignatura;
import es.iesjandula.horarios.models.xml.Aula;
import es.iesjandula.horarios.models.xml.Centro;
import es.iesjandula.horarios.models.xml.Datos;
import es.iesjandula.horarios.models.xml.Grupo;
import es.iesjandula.horarios.models.xml.GrupoActividad;
import es.iesjandula.horarios.models.xml.Horarios;
import es.iesjandula.horarios.models.xml.Profesor;
import es.iesjandula.horarios.models.xml.TipoHorario;
import es.iesjandula.horarios.models.xml.Tramo;


/**
 * @author Javier Martínez Megías
 */
public interface ParserXml
{
	static Logger log = LogManager.getLogger();
	
	/**
	 * 
	 * @param xml
	 * @return Centro
	 * @throws HorarioError
	 */
	public default Centro parserFileToObject(MultipartFile xml) throws HorarioError 
	{
		File file = MultipartFileTransferToFile(xml);	

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		
		/** inicializamos los objetos **/
		Centro centro = null;
		Datos datos = null;
		
		List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
		List<Grupo> listaGrupos = new ArrayList<Grupo>();
		List<Aula> listaAulas = new ArrayList<Aula>();
		List<Profesor> listaProfesores = new ArrayList<Profesor>();
		List<Tramo> listaTramos = new ArrayList<Tramo>();
		
		Horarios horarios = null;
		List<TipoHorario> listaHorariosAsignaturas = new ArrayList<TipoHorario>();
		List<TipoHorario> listaHorariosGrupo = new ArrayList<TipoHorario>();
		List<TipoHorario> listaHorariosAulas = new ArrayList<TipoHorario>();
		List<TipoHorario> listaHorariosProfesores = new ArrayList<TipoHorario>();
		
		try
		{
			
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();			
			Document document = documentBuilder.parse(file);
			
			/** Elemento root centro **/
			Element rootCentro = document.getDocumentElement();
			
			/** Cogemos los atributos de centro **/
			String nombreCentro = rootCentro.getAttributes().getNamedItem("nombre_centro").getTextContent();
			String Autor = rootCentro.getAttributes().getNamedItem("autor").getTextContent();
			String Fecha = rootCentro.getAttributes().getNamedItem("fecha").getTextContent();
			
			/** Elemento asignaturas **/
			NodeList nodeListDatos = rootCentro.getElementsByTagName("DATOS");
			
			/** Lista de asignaturas **/
			NodeList nodeListAsignaturas = nodeListDatos.item(0).getChildNodes();
			/** contador que recorre la lista de asignaturas **/
			int cont = 0;
			
			/** creamos los objetos Asignatura **/
			while(nodeListAsignaturas.getLength() > cont)
			{
				Element asignatura = (Element) nodeListAsignaturas.item(cont);
				
				int num_int_as = Integer.valueOf(asignatura.getAttributes().getNamedItem("num_int_as").getTextContent());
				String abreviatura = asignatura.getAttributes().getNamedItem("abreviatura").getTextContent();
				String nombre = asignatura.getAttributes().getNamedItem("nombre").getTextContent();
				
				listaAsignaturas.add(new Asignatura(num_int_as, abreviatura, nombre));
				cont++;
			}
			
			cont = 0;
			/** Lista de grupo **/
			NodeList nodeListGrupos = nodeListDatos.item(1).getChildNodes();
			
			/** creamos los objetos grupo **/
			while(nodeListGrupos.getLength() > cont)
			{
				Element grupo = (Element) nodeListGrupos.item(cont);
				
				int num_int_as = Integer.valueOf(grupo.getAttributes().getNamedItem("num_int_gr").getTextContent());
				String abreviatura = grupo.getAttributes().getNamedItem("abreviatura").getTextContent();
				String nombre = grupo.getAttributes().getNamedItem("nombre").getTextContent();
				
				listaGrupos.add(new Grupo(num_int_as, abreviatura, nombre));
				cont++;
			}
			
			cont = 0;
			/** Lista de aulas **/
			NodeList nodeListAulas = nodeListDatos.item(2).getChildNodes();
			
			/** creamos los objetos aula **/
			while(nodeListAulas.getLength() > cont)
			{
				Element aula = (Element) nodeListAulas.item(cont);
				
				int num_int_as = Integer.valueOf(aula.getAttributes().getNamedItem("num_int_au").getTextContent());
				String abreviatura = aula.getAttributes().getNamedItem("abreviatura").getTextContent();
				String nombre = aula.getAttributes().getNamedItem("nombre").getTextContent();
				
				listaAulas.add(new Aula(num_int_as, abreviatura, nombre));
				cont++;
			}
			
			cont = 0;
			/** Lista de profesores **/
			NodeList nodeListProfesores = nodeListDatos.item(3).getChildNodes();
			
			/** creamos los objetos profesor **/
			while(nodeListProfesores.getLength() > cont)
			{
				Element profesor = (Element) nodeListProfesores.item(cont);
				
				int num_int_as = Integer.valueOf(profesor.getAttributes().getNamedItem("num_int_pr").getTextContent());
				String abreviatura = profesor.getAttributes().getNamedItem("abreviatura").getTextContent();
				String nombre = profesor.getAttributes().getNamedItem("nombre").getTextContent();
				
				listaProfesores.add(new Profesor(num_int_as, abreviatura, nombre));
				cont++;
			}	
			
			cont = 0;
			/** Lista de tramos **/
			NodeList nodeListTramos = nodeListDatos.item(4).getChildNodes();
			
			/** creamos los objetos tramo **/
			while(nodeListTramos.getLength() > cont)
			{
				Element tramo = (Element) nodeListTramos.item(cont);
				
				int num_tr = Integer.valueOf(tramo.getAttributes().getNamedItem("num_tr").getTextContent());
				int numero_dia = Integer.valueOf(tramo.getAttributes().getNamedItem("numero_dia").getTextContent());
				String hora_inicio = tramo.getAttributes().getNamedItem("hora_inicio").getTextContent();
				String hora_final = tramo.getAttributes().getNamedItem("nombre").getTextContent();
				
				listaTramos.add(new Tramo(num_tr,numero_dia,hora_inicio,hora_final));
				cont++;
			}	
			
			
			
			/** Elemento horarios asignaturas **/
			NodeList nodeListHorarios = rootCentro.getElementsByTagName("HORARIOS");
			
			/**------------------------------------------------ ASIGNATURAS -------------------------------------------------------------------------------**/
			/** Lista de horarios asignaturas **/
			NodeList nodeListHorariosAsignatura = nodeListHorarios.item(0).getChildNodes();
			
			cont = 0;
			
			/** creamos los objetos tramo **/
			while(nodeListHorariosAsignatura.getLength() > cont)
			{
				Element horarioAsignatura = (Element) nodeListHorariosAsignatura.item(cont);
				
				int hor_num_int_as = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("hor_num_int_as").getTextContent());
				int tot_un = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_un").getTextContent());
				int tot_ac = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_ac").getTextContent());
				
				
				/** Lista de horarios asignatura **/
				NodeList actividades = nodeListHorariosAsignatura.item(0).getChildNodes();
				
				List<Actividad> listaActividades = new ArrayList<Actividad>();
				int cont1 = 0;
				while(actividades.getLength() > cont1)
				{
					Element actividad = (Element) actividades.item(cont1);
					int num_act = Integer.valueOf(actividad.getAttributes().getNamedItem("num_act").getTextContent());
					int num_un = Integer.valueOf(actividad.getAttributes().getNamedItem("num_un").getTextContent());
					int tramo = Integer.valueOf(actividad.getAttributes().getNamedItem("tramo").getTextContent());
					int aula = Integer.valueOf(actividad.getAttributes().getNamedItem("aula").getTextContent());
					int profesor = Integer.valueOf(actividad.getAttributes().getNamedItem("profesor").getTextContent());
					
					
					Element grupoActividad  = (Element) actividad.getElementsByTagName("GRUPOS_ACTIVIDAD");
					int tot_gr_act = 0;
					int grupo_1 = 0;
					int grupo_2 = 0;
					GrupoActividad grupoAct = null;
					
					if(grupoActividad.hasAttribute("grupo_1") && grupoActividad.hasAttribute("tot_gr_act") && grupoActividad.hasAttribute("grupo_2"))
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupo_1 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
						grupo_2 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_2").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act, grupo_1, grupo_2);
					}
					else if(grupoActividad.hasAttribute("grupo_1") && grupoActividad.hasAttribute("tot_gr_act"))
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupo_1 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act, grupo_1);
					}
					else
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act);
					}
					
					listaActividades.add(new Actividad(num_act, num_un, tramo, aula, profesor, grupoAct ));
					
				}
				
				listaHorariosAsignaturas.add(new TipoHorario(hor_num_int_as, tot_un, tot_ac, listaActividades));
				cont++;
			}	
			
			/**----------------------------------------------------- GRUPOS -------------------------------------------------------------------------------**/
			NodeList nodeListHorariosGrupos = nodeListHorarios.item(1).getChildNodes();
			
			cont = 0;
			
			/** creamos los objetos tramo **/
			while(nodeListHorariosGrupos.getLength() > cont)
			{
				Element horarioAsignatura = (Element) nodeListHorariosGrupos.item(cont);
				
				int hor_num_int_as = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("hor_num_int_as").getTextContent());
				int tot_un = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_un").getTextContent());
				int tot_ac = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_ac").getTextContent());
				
				
				/** Lista de horarios asignatura **/
				NodeList actividades = nodeListHorariosAsignatura.item(0).getChildNodes();
				
				List<Actividad> listaActividades = new ArrayList<Actividad>();
				int cont1 = 0;
				while(actividades.getLength() > cont1)
				{
					Element actividad = (Element) actividades.item(cont1);
					int num_act = Integer.valueOf(actividad.getAttributes().getNamedItem("num_act").getTextContent());
					int num_un = Integer.valueOf(actividad.getAttributes().getNamedItem("num_un").getTextContent());
					int tramo = Integer.valueOf(actividad.getAttributes().getNamedItem("tramo").getTextContent());
					int aula = Integer.valueOf(actividad.getAttributes().getNamedItem("aula").getTextContent());
					int profesor = Integer.valueOf(actividad.getAttributes().getNamedItem("profesor").getTextContent());
					
					
					listaActividades.add(new Actividad(num_act, num_un, tramo, aula, profesor));
					
				}
				
				listaHorariosAsignaturas.add(new TipoHorario(hor_num_int_as, tot_un, tot_ac, listaActividades));
				cont++;
			}	
			
			/** -------------------------------------------- AULAS -------------------------------------------------------------------------------**/
			NodeList nodeListHorariosAula = nodeListHorarios.item(2).getChildNodes();
			
			cont = 0;
			
			/** creamos los objetos tramo **/
			while(nodeListHorariosAsignatura.getLength() > cont)
			{
				Element horarioAsignatura = (Element) nodeListHorariosAula.item(cont);
				
				int hor_num_int_as = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("hor_num_int_as").getTextContent());
				int tot_un = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_un").getTextContent());
				int tot_ac = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_ac").getTextContent());
				
				
				/** Lista de horarios asignatura **/
				NodeList actividades = nodeListHorariosAsignatura.item(0).getChildNodes();
				
				List<Actividad> listaActividades = new ArrayList<Actividad>();
				int cont1 = 0;
				while(actividades.getLength() > cont1)
				{
					Element actividad = (Element) actividades.item(cont1);
					int num_act = Integer.valueOf(actividad.getAttributes().getNamedItem("num_act").getTextContent());
					int num_un = Integer.valueOf(actividad.getAttributes().getNamedItem("num_un").getTextContent());
					int tramo = Integer.valueOf(actividad.getAttributes().getNamedItem("tramo").getTextContent());
					int aula = Integer.valueOf(actividad.getAttributes().getNamedItem("aula").getTextContent());
					int profesor = Integer.valueOf(actividad.getAttributes().getNamedItem("profesor").getTextContent());
					
					
					Element grupoActividad  = (Element) actividad.getElementsByTagName("GRUPOS_ACTIVIDAD");
					int tot_gr_act = 0;
					int grupo_1 = 0;
					int grupo_2 = 0;
					GrupoActividad grupoAct = null;
					
					if(grupoActividad.hasAttribute("grupo_1") && grupoActividad.hasAttribute("tot_gr_act") && grupoActividad.hasAttribute("grupo_2"))
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupo_1 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
						grupo_2 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_2").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act, grupo_1, grupo_2);
					}
					else if(grupoActividad.hasAttribute("grupo_1") && grupoActividad.hasAttribute("tot_gr_act"))
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupo_1 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act, grupo_1);
					}
					else
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act);
					}
					
					listaActividades.add(new Actividad(num_act, num_un, tramo, aula, profesor, grupoAct ));
					
				}
				
				listaHorariosAsignaturas.add(new TipoHorario(hor_num_int_as, tot_un, tot_ac, listaActividades));
				cont++;
			}	
			
			/** ---------------------------------------------PROFESOR -------------------------------------------------------------------------------**/
			NodeList nodeListHorariosProfesor = nodeListHorarios.item(3).getChildNodes();
			
			cont = 0;
			
			/** creamos los objetos tramo **/
			while(nodeListHorariosAsignatura.getLength() > cont)
			{
				Element horarioAsignatura = (Element) nodeListHorariosProfesor.item(cont);
				
				int hor_num_int_as = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("hor_num_int_as").getTextContent());
				int tot_un = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_un").getTextContent());
				int tot_ac = Integer.valueOf(horarioAsignatura.getAttributes().getNamedItem("tot_ac").getTextContent());
				
				
				/** Lista de horarios asignatura **/
				NodeList actividades = nodeListHorariosAsignatura.item(0).getChildNodes();
				
				List<Actividad> listaActividades = new ArrayList<Actividad>();
				int cont1 = 0;
				while(actividades.getLength() > cont1)
				{
					Element actividad = (Element) actividades.item(cont1);
					int num_act = Integer.valueOf(actividad.getAttributes().getNamedItem("num_act").getTextContent());
					int num_un = Integer.valueOf(actividad.getAttributes().getNamedItem("num_un").getTextContent());
					int tramo = Integer.valueOf(actividad.getAttributes().getNamedItem("tramo").getTextContent());
					int aula = Integer.valueOf(actividad.getAttributes().getNamedItem("aula").getTextContent());
					int profesor = Integer.valueOf(actividad.getAttributes().getNamedItem("profesor").getTextContent());
					
					
					Element grupoActividad  = (Element) actividad.getElementsByTagName("GRUPOS_ACTIVIDAD");
					int tot_gr_act = 0;
					int grupo_1 = 0;
					int grupo_2 = 0;
					GrupoActividad grupoAct = null;
					
					if(grupoActividad.hasAttribute("grupo_1") && grupoActividad.hasAttribute("tot_gr_act") && grupoActividad.hasAttribute("grupo_2"))
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupo_1 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
						grupo_2 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_2").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act, grupo_1, grupo_2);
					}
					else if(grupoActividad.hasAttribute("grupo_1") && grupoActividad.hasAttribute("tot_gr_act"))
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupo_1 = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("grupo_1").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act, grupo_1);
					}
					else
					{
						tot_gr_act = Integer.valueOf(grupoActividad.getAttributes().getNamedItem("tot_gr_act").getTextContent());
						grupoAct = new GrupoActividad(tot_gr_act);
					}
					
					listaActividades.add(new Actividad(num_act, num_un, tramo, aula, profesor, grupoAct ));
					
				}
				
				listaHorariosAsignaturas.add(new TipoHorario(hor_num_int_as, tot_un, tot_ac, listaActividades));
				cont++;
			}	
			
			horarios = new Horarios(listaHorariosAsignaturas, listaHorariosGrupo, listaHorariosAulas, listaHorariosProfesores);
			datos = new Datos(listaAsignaturas, listaGrupos, listaAulas, listaProfesores, listaTramos, horarios);
			centro = new Centro(nombreCentro, Autor, Fecha, datos);
		}
		catch(ParserConfigurationException exception)
		{
			String error = "Error con el fichero";
			log.error(error,exception);
			throw new HorarioError(1,error);
		}
		catch(SAXException exception)
		{
			String error = "Error con XML";
			log.error(error,exception);
			throw new HorarioError(1,error);
		}
		catch(IOException exception)
		{
			String error = "Error en la lectura";
			log.error(error,exception);
			throw new HorarioError(1,error);
		}
		return centro;
		
	
					
	}

	/**
	 * Transform a MultiparFile to a File
	 * @param MultiparFile
	 * @return File
	 * @throws HorarioError
	 */
	private File MultipartFileTransferToFile(MultipartFile xml) throws HorarioError
	{
		/** the route for the new File with the xml info **/
		File file = new File("fichero.xml");
		try
		{
			/** Move the information from a MultipartFile to a File**/
			xml.transferTo(file);
		} 
		catch (IllegalStateException | IOException exception)
		{
			String error = "Error al trasferir el fichero";
			log.error(error,exception);
			throw new HorarioError(1,error);
		}
		return file;
	}
}
