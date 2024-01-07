package es.reaktor.horarios.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.reaktor.horarios.exceptions.HorariosError;
import es.reaktor.horarios.models.ApplicationPdf;
import es.reaktor.horarios.models.Classroom;
import es.reaktor.horarios.models.Course;
import es.reaktor.horarios.models.Hour;
import es.reaktor.horarios.models.Rol;
import es.reaktor.horarios.models.Student;
import es.reaktor.horarios.models.Teacher;
import es.reaktor.horarios.models.TeacherMoment;
import es.reaktor.horarios.models.parse.Actividad;
import es.reaktor.horarios.models.parse.Asignatura;
import es.reaktor.horarios.models.parse.Asignaturas;
import es.reaktor.horarios.models.parse.Aula;
import es.reaktor.horarios.models.parse.Aulas;
import es.reaktor.horarios.models.parse.Centro;
import es.reaktor.horarios.models.parse.Datos;
import es.reaktor.horarios.models.parse.Grupo;
import es.reaktor.horarios.models.parse.Grupos;
import es.reaktor.horarios.models.parse.GruposActividad;
import es.reaktor.horarios.models.parse.HorarioAsig;
import es.reaktor.horarios.models.parse.HorarioAula;
import es.reaktor.horarios.models.parse.HorarioGrup;
import es.reaktor.horarios.models.parse.HorarioProf;
import es.reaktor.horarios.models.parse.Horarios;
import es.reaktor.horarios.models.parse.HorariosAsignaturas;
import es.reaktor.horarios.models.parse.HorariosAulas;
import es.reaktor.horarios.models.parse.HorariosGrupos;
import es.reaktor.horarios.models.parse.HorariosProfesores;
import es.reaktor.horarios.models.parse.Profesor;
import es.reaktor.horarios.models.parse.Profesores;
import es.reaktor.horarios.models.parse.Tramo;
import es.reaktor.horarios.models.parse.TramosHorarios;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * @author David Martinez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/horarios")
@Slf4j
public class HorariosRest
{
	/** Attribute centroPdfs , used for get the info of PDFS */
	private Centro centroPdfs;

	/**
	 * Method sendXmlToObjects
	 *
	 * @param xmlFile
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> sendXmlToObjects(@RequestPart MultipartFile xmlFile, HttpSession session)
	{
		try
		{
			File xml = new File(xmlFile.getOriginalFilename());
			log.info("FILE NAME: " + xml.getName());
			if (xml.getName().endsWith(".xml"))
			{
				// ES UN XML
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder;
				// -- OBJECT CENTRO ---
				Centro centro = new Centro();
				try
				{
					InputStream is = xmlFile.getInputStream();
					documentBuilder = builderFactory.newDocumentBuilder();
					Document document = documentBuilder.parse(is);

					// --- ELEMENTO ROOT CENTRO ------
					Element rootCenterElement = document.getDocumentElement();
					// --- ELEMENT CENTRO ATTRIBUTES ---
					centro.setNombreCentro(rootCenterElement.getAttribute("nombre_centro"));
					centro.setAutor(rootCenterElement.getAttribute("autor"));
					centro.setFecha(rootCenterElement.getAttribute("fecha"));
					// --------------------------------------------------------------------------------------------------
					// --- OBJECT DATOS ---
					Datos datos = new Datos();
					// --------------------------------------------------------------------------------------------------

					// --- OBJECT ASIGNATURAS ---
					Asignaturas asignaturas = new Asignaturas();
					NodeList nodeAsignaturas = rootCenterElement.getElementsByTagName("ASIGNATURAS");

					// --- tot_as ATTRIBUTE VALOR ---
					asignaturas.setTotAs(nodeAsignaturas.item(0).getAttributes().item(0).getTextContent());

					// GETTING ASIGNATURAS (ONLY ONE)
					Element asignaturasElemet = (Element) rootCenterElement.getElementsByTagName("ASIGNATURAS").item(0);

					// GETTING THE LIST OF ASIGNATURA
					NodeList asignaturasNodeList = asignaturasElemet.getElementsByTagName("ASIGNATURA");

					List<Asignatura> asignaturasList = new ArrayList<>();
					// GETTING VALUES OF EACH ASIGNATURA
					this.gettingValuesOfAsignatura(asignaturasNodeList, asignaturasList);
					log.info(asignaturasList.toString());
					asignaturas.setAsignatura(asignaturasList);
					log.info(asignaturas.toString());
					datos.setAsignaturas(asignaturas);
					// --------------------------------------------------------------------------------------------------

					// --- OBJECT GRUPOS ---
					Grupos grupos = new Grupos();
					NodeList nodeGrupos = rootCenterElement.getElementsByTagName("GRUPOS");

					// --- tot_gr ATTRIBUTE VALOR ---
					grupos.setTotGr(nodeGrupos.item(0).getAttributes().item(0).getTextContent());

					// GETTING GRUPOS (ONLY ONE)
					Element gruposElement = (Element) rootCenterElement.getElementsByTagName("GRUPOS").item(0);

					// GETTING THE LIST OF GRUPO
					NodeList gruposNodeList = gruposElement.getElementsByTagName("GRUPO");

					List<Grupo> gruposList = new ArrayList<>();
					// GETTING VALUES OF EACH GRUPO
					this.gettingValuesOfGrupo(gruposNodeList, gruposList);
					log.info(gruposList.toString());
					grupos.setGrupo(gruposList);
					log.info(grupos.toString());
					datos.setGrupos(grupos);
					// --------------------------------------------------------------------------------------------------

					// --- OBJECT AULAS ---
					Aulas aulas = new Aulas();
					NodeList nodeAulas = rootCenterElement.getElementsByTagName("AULAS");

					// --- tot_au ATTRIBUTE VALOR ---
					aulas.setTotAu(nodeAulas.item(0).getAttributes().item(0).getTextContent());

					// GETTING AULAS (ONLY ONE)
					Element aulasElement = (Element) rootCenterElement.getElementsByTagName("AULAS").item(0);

					// GETTING THE LIST OF AULA
					NodeList aulasNodeList = aulasElement.getElementsByTagName("AULA");

					List<Aula> aulasList = new ArrayList<>();
					// GETTING VALUES OF EACH AULA
					this.gettingValuesOfAula(aulasNodeList, aulasList);
					log.info(aulasList.toString());
					aulas.setAula(aulasList);
					log.info(aulas.toString());
					datos.setAulas(aulas);
					// --------------------------------------------------------------------------------------------------

					// --- OBJECT PROFESORES ---
					Profesores profesores = new Profesores();
					NodeList nodeProfesores = rootCenterElement.getElementsByTagName("PROFESORES");

					// --- tot_pr ATTRIBUTE VALOR ---
					profesores.setTotPR(nodeProfesores.item(0).getAttributes().item(0).getTextContent());

					// GETTING PROFESORES (ONLY ONE)
					Element profesoresElement = (Element) rootCenterElement.getElementsByTagName("PROFESORES").item(0);

					// GETTING THE LIST OF PROFESOR
					NodeList profesoresNodeList = profesoresElement.getElementsByTagName("PROFESOR");

					List<Profesor> profesoresList = new ArrayList<>();
					// GETTING VALUES OF EACH PROFESOR
					this.gettingValuesOfProfesor(profesoresNodeList, profesoresList);
					log.info(profesoresList.toString());
					profesores.setProfesor(profesoresList);
					log.info(profesores.toString());
					datos.setProfesores(profesores);
					// --------------------------------------------------------------------------------------------------

					// --- OBJECT TramosHorarios ---
					TramosHorarios tramosHorarios = new TramosHorarios();
					NodeList nodeTramosHorarios = rootCenterElement.getElementsByTagName("TRAMOS_HORARIOS");

					// --- tot_tr ATTRIBUTE VALOR ---
					tramosHorarios.setTotTr(nodeTramosHorarios.item(0).getAttributes().item(0).getTextContent());

					// GETTING TRAMOS_HORARIOS (ONLY ONE)
					Element tramosHorariosElement = (Element) rootCenterElement.getElementsByTagName("TRAMOS_HORARIOS")
							.item(0);

					// GETTING THE LIST OF TRAMO
					NodeList tramosHorariosNodeList = tramosHorariosElement.getElementsByTagName("TRAMO");

					List<Tramo> tramosList = new ArrayList<>();
					// GETTING VALUES OF EACH TRAMO
					this.gettingValuesOfTramo(tramosHorariosNodeList, tramosList);
					log.info(tramosList.toString());
					tramosHorarios.setTramo(tramosList);
					log.info(tramosHorarios.toString());
					// --------------------------------------------------------------------------------------------------
					datos.setTramosHorarios(tramosHorarios);
					// --------------------------------------------------------------------------------------------------

					// ---- END OF DATOS ---
					centro.setDatos(datos);

					// --- HORARIOS ---
					Horarios horarios = new Horarios();

					// --------------------------------------------------------------------------------------------------
					// --- HORARIOS ELEMENT ---
					Element horariosElement = (Element) rootCenterElement.getElementsByTagName("HORARIOS").item(0);

					// --------------------------------------------------------------------------------------------------

					// --- HORARIOS ASIGNATURAS ONLY ONE ---
					HorariosAsignaturas horariosAsignaturas = new HorariosAsignaturas();

					// --- HORARIOS ASIGNATURAS ELEMENT ---
					Element horariosAsignaturasElement = (Element) horariosElement
							.getElementsByTagName("HORARIOS_ASIGNATURAS").item(0);

					// NODELIST HORAIO_ASIG
					NodeList horarioAsigNodeList = horariosAsignaturasElement.getElementsByTagName("HORARIO_ASIG");

					// EACH HORARIOS_ASIG
					List<HorarioAsig> horarioAsigList = new ArrayList<>();
					this.gettingValuesOfHorarioAsig(horarioAsigNodeList, horarioAsigList);
					log.info(horarioAsigList.toString());
					horariosAsignaturas.setHorarioAsig(horarioAsigList);
					horarios.setHorariosAsignaturas(horariosAsignaturas);
					// --------------------------------------------------------------------------------------------------

					// --- HORARIOS_GRUPOS ONLY ONE ---
					HorariosGrupos horariosGrupos = new HorariosGrupos();

					// --- HORARIO_GRUP ELEMENT ---
					Element horariosGruposElement = (Element) horariosElement.getElementsByTagName("HORARIOS_GRUPOS")
							.item(0);

					// NODELIST HORARIO_GRUP
					NodeList horarioGrupNodeList = horariosGruposElement.getElementsByTagName("HORARIO_GRUP");

					// EACH HORARIO_GRUP
					List<HorarioGrup> horarioGrupList = new ArrayList<>();
					this.gettingValuesOfHorarioGrup(horarioGrupNodeList, horarioGrupList);
					// log.info(horarioAsigList);
					horariosGrupos.setHorarioGrup(horarioGrupList);
					horarios.setHorariosGrupos(horariosGrupos);
					// --------------------------------------------------------------------------------------------------

					// --- HORARIOS HORARIOS_AULAS ONLY ONE ---
					HorariosAulas horariosAulas = new HorariosAulas();

					// --- HORARIOS HORARIOS_AULAS ELEMENT ---
					Element horariosAulasElement = (Element) horariosElement.getElementsByTagName("HORARIOS_AULAS")
							.item(0);

					// NODELIST HORARIO_AULA
					NodeList horarioAulaNodeList = horariosAulasElement.getElementsByTagName("HORARIO_AULA");

					// EACH HORARIO_AULA
					List<HorarioAula> horarioAulaList = new ArrayList<>();
					this.gettingValuesOfHorarioAula(horarioAulaNodeList, horarioAulaList);
					// log.info(horarioAulaList);
					horariosAulas.setHorarioAula(horarioAulaList);
					horarios.setHorariosAulas(horariosAulas);
					// --------------------------------------------------------------------------------------------------

					// --- HORARIOS HORARIOS_PROFESORES ONLY ONE ---
					HorariosProfesores horariosProfesores = new HorariosProfesores();

					// --- HORARIOS HORARIOS_AULAS ELEMENT ---
					Element horariosProfesoresElement = (Element) horariosElement
							.getElementsByTagName("HORARIOS_PROFESORES").item(0);

					// NODELIST HORARIO_AULA
					NodeList horarioProfNodeList = horariosProfesoresElement.getElementsByTagName("HORARIO_PROF");

					// EACH HORARIO_PROF
					List<HorarioProf> horarioProfList = new ArrayList<>();
					this.gettingValuesOfHorarioProf(horarioProfNodeList, horarioProfList);
					log.info(horarioAulaList.toString());
					horariosProfesores.setHorarioProf(horarioProfList);
					horarios.setHorariosProfesores(horariosProfesores);
					// -------------------------------------------------------------------------------------------------------------------------------------------------
					centro.setHorarios(horarios);
					// -------------------------------------------------------------------------------------------------------------------------------------------------
					log.info("File :" + xmlFile.getName() + " load-Done");
				}
				catch (ParserConfigurationException exception)
				{
					String error = "Parser Configuration Exception";
					log.error(error, exception);
					HorariosError horariosException = new HorariosError(400, exception.getLocalizedMessage(),
							exception);
					return ResponseEntity.status(400).body(horariosException.toMap());

				}
				catch (SAXException exception)
				{
					String error = "SAX Exception";
					log.error(error, exception);
					HorariosError horariosException = new HorariosError(400, exception.getLocalizedMessage(),
							exception);
					return ResponseEntity.status(400).body(horariosException.toMap());
				}
				catch (IOException exception)
				{
					String error = "In Out Exception";
					log.error(error, exception);
					HorariosError horariosException = new HorariosError(400, exception.getLocalizedMessage(),
							exception);
					return ResponseEntity.status(400).body(horariosException.toMap());
				}

				// --- SESSION ---------
				session.setAttribute("storedCentro", centro);
				log.info("UserVisits: " + centro);
				// --- SESSION RESPONSE_ENTITY ---------
				this.centroPdfs = centro;
				return ResponseEntity.ok(session.getAttribute("storedCentro"));
			}
			else
			{
				// NO ES UN XML
				String error = "The file is not a XML file";
				HorariosError horariosException = new HorariosError(400, error, new Exception());
				log.error(error, horariosException);
				return ResponseEntity.status(400).body(horariosException.toMap());
			}
		}
		catch (Exception except)
		{
			// SERVER ERROR
			String error = "Server Error";
			HorariosError horariosException = new HorariosError(500, except.getLocalizedMessage(), except);
			log.error(error, horariosException);
			return ResponseEntity.status(500).body(horariosException.toMap());
		}
	}

	/**
	 * Method getRoles , returns ResponseEntity with the teacher roles
	 * 
	 * @param email
	 * @param session
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/roles", produces = "application/json")
	public ResponseEntity<?> getRoles(@RequestHeader(required = true) String email, HttpSession session)
	{
		try
		{
			List<Teacher> teacherList = new ArrayList<Teacher>();
			// --- VALIDATING CSV INFO (IN SESSION)---
			if (session.getAttribute("csvInfo") != null && session.getAttribute("csvInfo") instanceof List)
			{
				// -- GETTIN TEACHER LIST FROM CSV INFO --- (SESSION)
				teacherList = (List<Teacher>) session.getAttribute("csvInfo");

				if (!email.trim().isEmpty())
				{
					// --- GETTING THE TEACHER WITH THE SPECIFIC EMAIL ---
					for (Teacher teacher : teacherList)
					{
						if (teacher.getEmail().equals(email))
						{
							return ResponseEntity.ok().body(teacher);
						}
					}
				}
				else
				{
					// --- EMAIL NOT VALID ---
					String error = "Email is not valid";
					HorariosError horariosError = new HorariosError(400, error, null);
					log.error(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}
			}

			String error = "CSV data is not loadaed Or not found for this email";
			HorariosError horariosError = new HorariosError(400, error, null);
			log.error(error, horariosError);
			return ResponseEntity.status(400).body(horariosError);
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method getListStudentsAlphabetically
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/sortstudents", produces = "application/json")
	public ResponseEntity<?> getListStudentsAlphabetically()
	{
		try
		{
			// --- CREATING FAKE STUDEN LIST ---
			List<Student> studentList = new ArrayList<Student>();
			for (int i = 100; i > studentList.size(); i--)
			{
				Student student = new Student();
				student.setCourse(new Course("Course" + i, new Classroom(String.valueOf(i), String.valueOf(i))));
				student.setName("Alumno1" + (int) (Math.random() * 100 + 1));
				student.setLastName("PrimerApp" + (int) (Math.random() * 100 + 1));
				studentList.add(student);
			}
			// -- IF ALL ITS DONE , RETURN THE LIST SORTED ---
			Collections.sort(studentList);
			return ResponseEntity.ok().body(studentList);
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method gettingValuesOfHorarioProf
	 * 
	 * @param horarioProfNodeList
	 * @param horarioProfList
	 */
	private void gettingValuesOfHorarioProf(NodeList horarioProfNodeList, List<HorarioProf> horarioProfList)
	{
		for (int i = 0; i < horarioProfNodeList.getLength(); i++)
		{
			HorarioProf newHorarioProf = new HorarioProf();
			newHorarioProf.setHorNumIntPR(horarioProfNodeList.item(i).getAttributes().item(0).getTextContent());
			newHorarioProf.setTotAC(horarioProfNodeList.item(i).getAttributes().item(1).getTextContent());
			newHorarioProf.setTotUn(horarioProfNodeList.item(i).getAttributes().item(2).getTextContent());

			// GETTING ELEMENT HORARIO_PROF(i)
			Element horarioProfElement = (Element) horarioProfNodeList.item(i);
			// GETTING THE ACTIVIDAD NODE LIST
			NodeList actividadNodeList = horarioProfElement.getElementsByTagName("ACTIVIDAD");

			// --- ACTIVIDAD LIST ---
			List<Actividad> actividadList = new ArrayList<>();
			for (int j = 0; j < actividadNodeList.getLength(); j++)
			{
				Actividad newActividad = new Actividad();
				newActividad.setAsignatura(actividadNodeList.item(j).getAttributes().item(0).getTextContent());
				newActividad.setNumAct(actividadNodeList.item(j).getAttributes().item(2).getTextContent());
				newActividad.setNumUn(actividadNodeList.item(j).getAttributes().item(3).getTextContent());
				newActividad.setTramo(actividadNodeList.item(j).getAttributes().item(4).getTextContent());
				newActividad.setAula(actividadNodeList.item(j).getAttributes().item(1).getTextContent());

				// --- GETTING GRUPOS ACTIVIDAD ---
				GruposActividad gruposActividad = new GruposActividad();
				// ((Element)actividadNodeList.item(j)).getElementsByTagName("GRUPOS_ACTIVIDAD").item(0).getAttributes().item(0).getTextContent()

				// --- IF THE ELEMENT GRUPOS_ACTIVIDAD HAS 1 , 2, 3 , 4 OR 5 ATTRIBUTES---
				NamedNodeMap gruposActividadNodeMap = ((Element) actividadNodeList.item(j))
						.getElementsByTagName("GRUPOS_ACTIVIDAD").item(0).getAttributes();
				this.gettingValuesOfGruposActividadAttrs(actividadList, newActividad, gruposActividad,
						gruposActividadNodeMap);
			}
			// --- ADD ACTIVIDAD LIST TO HORARIO_AULA
			newHorarioProf.setActividad(actividadList);
			log.info(actividadList.toString());

			// -- ADD HORARIO_AULA TO LIST ---
			horarioProfList.add(newHorarioProf);
		}
	}

	/**
	 * Method gettingValuesOfHorarioAula
	 * 
	 * @param horarioAulaNodeList
	 * @param horarioAulaList
	 */
	private void gettingValuesOfHorarioAula(NodeList horarioAulaNodeList, List<HorarioAula> horarioAulaList)
	{
		for (int i = 0; i < horarioAulaNodeList.getLength(); i++)
		{
			HorarioAula newHorarioAula = new HorarioAula();
			newHorarioAula.setHorNumIntAu(horarioAulaNodeList.item(i).getAttributes().item(0).getTextContent());
			newHorarioAula.setTotAC(horarioAulaNodeList.item(i).getAttributes().item(1).getTextContent());
			newHorarioAula.setTotUn(horarioAulaNodeList.item(i).getAttributes().item(2).getTextContent());

			// GETTING ELEMENT HORARIO AULA (i)
			Element horarioAulaElement = (Element) horarioAulaNodeList.item(i);
			// GETTING THE ACTIVIDAD NODE LIST
			NodeList actividadNodeList = horarioAulaElement.getElementsByTagName("ACTIVIDAD");

			// --- ACTIVIDAD LIST ---
			List<Actividad> actividadList = new ArrayList<>();
			for (int j = 0; j < actividadNodeList.getLength(); j++)
			{
				Actividad newActividad = new Actividad();
				newActividad.setAsignatura(actividadNodeList.item(j).getAttributes().item(0).getTextContent());
				newActividad.setNumAct(actividadNodeList.item(j).getAttributes().item(1).getTextContent());
				newActividad.setNumUn(actividadNodeList.item(j).getAttributes().item(2).getTextContent());
				newActividad.setTramo(actividadNodeList.item(j).getAttributes().item(4).getTextContent());
				newActividad.setProfesor(actividadNodeList.item(j).getAttributes().item(3).getTextContent());

				// --- GETTING GRUPOS ACTIVIDAD ---
				GruposActividad gruposActividad = new GruposActividad();
				// ((Element)actividadNodeList.item(j)).getElementsByTagName("GRUPOS_ACTIVIDAD").item(0).getAttributes().item(0).getTextContent()

				// --- IF THE ELEMENT GRUPOS_ACTIVIDAD HAS 1 , 2, 3 , 4 OR 5 ATTRIBUTES---
				NamedNodeMap gruposActividadNodeMap = ((Element) actividadNodeList.item(j))
						.getElementsByTagName("GRUPOS_ACTIVIDAD").item(0).getAttributes();
				this.gettingValuesOfGruposActividadAttrs(actividadList, newActividad, gruposActividad,
						gruposActividadNodeMap);
			}
			// --- ADD ACTIVIDAD LIST TO HORARIO_AULA
			newHorarioAula.setActividad(actividadList);
			log.info(actividadList.toString());

			// -- ADD HORARIO_AULA TO LIST ---
			horarioAulaList.add(newHorarioAula);
		}
	}

	/**
	 * Method gettingValuesOfHorarioGrup
	 * 
	 * @param horarioGrupNodeList
	 * @param horarioGrupList
	 */
	private void gettingValuesOfHorarioGrup(NodeList horarioGrupNodeList, List<HorarioGrup> horarioGrupList)
	{
		for (int i = 0; i < horarioGrupNodeList.getLength(); i++)
		{
			HorarioGrup newHorarioGrup = new HorarioGrup();
			newHorarioGrup.setHorNumIntGr(horarioGrupNodeList.item(i).getAttributes().item(0).getTextContent());
			newHorarioGrup.setTotAC(horarioGrupNodeList.item(i).getAttributes().item(1).getTextContent());
			newHorarioGrup.setTotUn(horarioGrupNodeList.item(i).getAttributes().item(2).getTextContent());

			// GETTING ELEMENT HORARIO_GRUP (i)
			Element horarioGrupElement = (Element) horarioGrupNodeList.item(i);
			// GETTING THE ACTIVIDAD NODE LIST
			NodeList actividadNodeList = horarioGrupElement.getElementsByTagName("ACTIVIDAD");

			// --- ACTIVIDAD LIST ---
			List<Actividad> actividadList = new ArrayList<>();
			for (int j = 0; j < actividadNodeList.getLength(); j++)
			{
				Actividad newActividad = new Actividad();
				newActividad.setAula(actividadNodeList.item(j).getAttributes().item(1).getTextContent());
				newActividad.setNumAct(actividadNodeList.item(j).getAttributes().item(2).getTextContent());
				newActividad.setNumUn(actividadNodeList.item(j).getAttributes().item(3).getTextContent());
				newActividad.setTramo(actividadNodeList.item(j).getAttributes().item(5).getTextContent());
				newActividad.setProfesor(actividadNodeList.item(j).getAttributes().item(4).getTextContent());
				newActividad.setAsignatura(actividadNodeList.item(j).getAttributes().item(0).getTextContent());

				actividadList.add(newActividad);
			}
			// --- ADD ACTIVIDAD LIST TO HORARIO_GRUP
			newHorarioGrup.setActividad(actividadList);
			log.info(actividadList.toString());

			// -- ADD HORARIO_GRUP TO LIST ---
			horarioGrupList.add(newHorarioGrup);
		}
	}

	/**
	 * Method gettingValuesOfHorarioAsig
	 * 
	 * @param horarioAsigNodeList
	 * @param horarioAsigList
	 */
	private void gettingValuesOfHorarioAsig(NodeList horarioAsigNodeList, List<HorarioAsig> horarioAsigList)
	{
		for (int i = 0; i < horarioAsigNodeList.getLength(); i++)
		{
			HorarioAsig newHorarioAsig = new HorarioAsig();
			newHorarioAsig.setHorNumIntAs(horarioAsigNodeList.item(i).getAttributes().item(0).getTextContent());
			newHorarioAsig.setTotAC(horarioAsigNodeList.item(i).getAttributes().item(1).getTextContent());
			newHorarioAsig.setTotUn(horarioAsigNodeList.item(i).getAttributes().item(2).getTextContent());

			// GETTING ELEMENT HORARIO ASIG (i)
			Element horarioAsigElement = (Element) horarioAsigNodeList.item(i);
			// GETTING THE ACTIVIDAD NODE LIST
			NodeList actividadNodeList = horarioAsigElement.getElementsByTagName("ACTIVIDAD");

			// --- ACTIVIDAD LIST ---
			List<Actividad> actividadList = new ArrayList<>();
			this.gettingValuesOfActividad(actividadNodeList, actividadList);
			// --- ADD ACTIVIDAD LIST TO HORARIO_ASIG
			newHorarioAsig.setActividad(actividadList);
			log.info(actividadList.toString());

			// -- ADD HORARIO_ASIG TO LIST ---
			horarioAsigList.add(newHorarioAsig);
		}
	}

	/**
	 * Method gettingValuesOfActividad
	 * 
	 * @param actividadNodeList
	 * @param actividadList
	 */
	private void gettingValuesOfActividad(NodeList actividadNodeList, List<Actividad> actividadList)
	{
		for (int j = 0; j < actividadNodeList.getLength(); j++)
		{
			Actividad newActividad = new Actividad();
			newActividad.setAula(actividadNodeList.item(j).getAttributes().item(0).getTextContent());
			newActividad.setNumAct(actividadNodeList.item(j).getAttributes().item(1).getTextContent());
			newActividad.setNumUn(actividadNodeList.item(j).getAttributes().item(2).getTextContent());
			newActividad.setTramo(actividadNodeList.item(j).getAttributes().item(4).getTextContent());
			newActividad.setProfesor(actividadNodeList.item(j).getAttributes().item(3).getTextContent());

			// --- GETTING GRUPOS ACTIVIDAD ---
			GruposActividad gruposActividad = new GruposActividad();
			// ((Element)actividadNodeList.item(j)).getElementsByTagName("GRUPOS_ACTIVIDAD").item(0).getAttributes().item(0).getTextContent()

			// --- IF THE ELEMENT GRUPOS_ACTIVIDAD HAS 1 , 2, 3 , 4 OR 5 ATTRIBUTES---
			NamedNodeMap gruposActividadNodeMap = ((Element) actividadNodeList.item(j))
					.getElementsByTagName("GRUPOS_ACTIVIDAD").item(0).getAttributes();
			this.gettingValuesOfGruposActividadAttrs(actividadList, newActividad, gruposActividad,
					gruposActividadNodeMap);
		}
	}

	/**
	 * Method gettingValuesOfGruposActividadAttrs
	 * 
	 * @param actividadList
	 * @param newActividad
	 * @param gruposActividad
	 * @param gruposActividadNodeMap
	 */
	private void gettingValuesOfGruposActividadAttrs(List<Actividad> actividadList, Actividad newActividad,
			GruposActividad gruposActividad, NamedNodeMap gruposActividadNodeMap)
	{
		if (gruposActividadNodeMap.getLength() == 1)
		{
			// 1 ATTR CASE
			Node node = gruposActividadNodeMap.item(0);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, node);

		}
		if (gruposActividadNodeMap.getLength() == 2)
		{
			// 2 ATTR CASE
			Node nodeOne = gruposActividadNodeMap.item(0);
			Node nodeTwo = gruposActividadNodeMap.item(1);

			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeOne);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeTwo);

		}
		if (gruposActividadNodeMap.getLength() == 3)
		{
			// 3 ATTR CASE
			Node nodeOne = gruposActividadNodeMap.item(0);
			Node nodeTwo = gruposActividadNodeMap.item(1);
			Node nodeThree = gruposActividadNodeMap.item(2);

			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeOne);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeTwo);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeThree);

		}
		if (gruposActividadNodeMap.getLength() == 4)
		{
			// 4 ATTR CASE
			Node nodeOne = gruposActividadNodeMap.item(0);
			Node nodeTwo = gruposActividadNodeMap.item(1);
			Node nodeThree = gruposActividadNodeMap.item(2);
			Node nodeFour = gruposActividadNodeMap.item(3);

			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeOne);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeTwo);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeThree);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeFour);

		}
		if (gruposActividadNodeMap.getLength() == 5)
		{
			// 5 ATTR CASE
			Node nodeOne = gruposActividadNodeMap.item(0);
			Node nodeTwo = gruposActividadNodeMap.item(1);
			Node nodeThree = gruposActividadNodeMap.item(2);
			Node nodeFour = gruposActividadNodeMap.item(3);
			Node nodeFive = gruposActividadNodeMap.item(4);

			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeOne);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeTwo);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeThree);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeFour);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeFive);

		}
		if (gruposActividadNodeMap.getLength() == 6)
		{
			// 6 ATTR CASE
			Node nodeOne = gruposActividadNodeMap.item(0);
			Node nodeTwo = gruposActividadNodeMap.item(1);
			Node nodeThree = gruposActividadNodeMap.item(2);
			Node nodeFour = gruposActividadNodeMap.item(3);
			Node nodeFive = gruposActividadNodeMap.item(4);
			Node nodeSix = gruposActividadNodeMap.item(5);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeOne);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeTwo);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeThree);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeFour);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeFive);
			gruposActividad = this.getGruposActividadAttributeTexts(gruposActividad, nodeSix);
		}
		newActividad.setGruposActividad(gruposActividad);
		log.info(gruposActividad.toString());
		actividadList.add(newActividad);
	}

	/**
	 * Method gettingValuesOfTramo
	 * 
	 * @param tramosHorariosNodeList
	 * @param tramosList
	 */
	private void gettingValuesOfTramo(NodeList tramosHorariosNodeList, List<Tramo> tramosList)
	{
		for (int i = 0; i < tramosHorariosNodeList.getLength(); i++)
		{
			Tramo newTramo = new Tramo();
			newTramo.setHoraFinal(tramosHorariosNodeList.item(i).getAttributes().item(0).getTextContent());
			newTramo.setHoraInicio(tramosHorariosNodeList.item(i).getAttributes().item(1).getTextContent());
			newTramo.setNumeroDia(tramosHorariosNodeList.item(i).getAttributes().item(3).getTextContent());
			newTramo.setNumTr(tramosHorariosNodeList.item(i).getAttributes().item(2).getTextContent());

			tramosList.add(newTramo);
		}
	}

	/**
	 * Method gettingValuesOfProfesor
	 * 
	 * @param profesoresNodeList
	 * @param profesoresList
	 */
	private void gettingValuesOfProfesor(NodeList profesoresNodeList, List<Profesor> profesoresList)
	{
		for (int i = 0; i < profesoresNodeList.getLength(); i++)
		{
			Profesor newProfesor = new Profesor();
			newProfesor.setAbreviatura(profesoresNodeList.item(i).getAttributes().item(0).getTextContent());
			newProfesor.setNumIntPR(profesoresNodeList.item(i).getAttributes().item(2).getTextContent());

			// --- GETTING FULL NAME ---
			String nombreCompleto = profesoresNodeList.item(i).getAttributes().item(1).getTextContent();
			String[] nombreCompletoSpit = nombreCompleto.split(",");
			// --- GETING LASTNAME ---
			String[] apellidosSplit = nombreCompletoSpit[0].split(" ");

			/// --- SETTING VALUES ---
			newProfesor.setNombre(nombreCompletoSpit[nombreCompletoSpit.length - 1].trim());
			newProfesor.setPrimerApellido(apellidosSplit[0].trim());
			newProfesor.setSegundoApellido(apellidosSplit[1].trim());

			profesoresList.add(newProfesor);
		}
	}

	/**
	 * Method gettingValuesOfAula
	 * 
	 * @param aulasNodeList
	 * @param aulasList
	 */
	private void gettingValuesOfAula(NodeList aulasNodeList, List<Aula> aulasList)
	{
		for (int i = 0; i < aulasNodeList.getLength(); i++)
		{
			Aula newAula = new Aula();
			newAula.setAbreviatura(aulasNodeList.item(i).getAttributes().item(0).getTextContent());
			newAula.setNumIntAu(aulasNodeList.item(i).getAttributes().item(2).getTextContent());
			newAula.setNombre(aulasNodeList.item(i).getAttributes().item(1).getTextContent());

			aulasList.add(newAula);
		}
	}

	/**
	 * Method gettingValuesOfGrupo
	 * 
	 * @param gruposNodeList
	 * @param gruposList
	 */
	private void gettingValuesOfGrupo(NodeList gruposNodeList, List<Grupo> gruposList)
	{
		for (int i = 0; i < gruposNodeList.getLength(); i++)
		{
			Grupo newGrupo = new Grupo();
			newGrupo.setAbreviatura(gruposNodeList.item(i).getAttributes().item(0).getTextContent());
			newGrupo.setNumIntGr(gruposNodeList.item(i).getAttributes().item(2).getTextContent());
			newGrupo.setNombre(gruposNodeList.item(i).getAttributes().item(1).getTextContent());

			gruposList.add(newGrupo);
		}
	}

	/**
	 * Method gettingValuesOfAsignatura
	 * 
	 * @param asignaturasNodeList
	 * @param asignaturasList
	 */
	private void gettingValuesOfAsignatura(NodeList asignaturasNodeList, List<Asignatura> asignaturasList)
	{
		for (int i = 0; i < asignaturasNodeList.getLength(); i++)
		{
			Asignatura newAsignatura = new Asignatura();
			newAsignatura.setAbreviatura(asignaturasNodeList.item(i).getAttributes().item(0).getTextContent());
			newAsignatura.setNumIntAs(asignaturasNodeList.item(i).getAttributes().item(2).getTextContent());
			newAsignatura.setNombre(asignaturasNodeList.item(i).getAttributes().item(1).getTextContent());

			asignaturasList.add(newAsignatura);
		}
	}

	/**
	 * Method extracted
	 *
	 * @param gruposActividad
	 * @param node
	 */
	private GruposActividad getGruposActividadAttributeTexts(GruposActividad gruposActividad, Node node)
	{
		if (node.getNodeName().equals("tot_gr_act"))
		{
			gruposActividad.setTotGrAct(node.getTextContent());
		}
		if (node.getNodeName().equals("grupo_1"))
		{
			gruposActividad.setGrupo1(node.getTextContent());
		}
		if (node.getNodeName().equals("grupo_2"))
		{
			gruposActividad.setGrupo2(node.getTextContent());
		}
		if (node.getNodeName().equals("grupo_3"))
		{
			gruposActividad.setGrupo3(node.getTextContent());
		}
		if (node.getNodeName().equals("grupo_4"))
		{
			gruposActividad.setGrupo4(node.getTextContent());
		}
		if (node.getNodeName().equals("grupo_5"))
		{
			gruposActividad.setGrupo5(node.getTextContent());
		}
		return gruposActividad;
	}

	/**
	 * method sendCsvTo
	 * 
	 * @param archivo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/send/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> sendCsvTo(@RequestPart MultipartFile archivo, HttpSession session)
	{
		try
		{
			List<Teacher> teachers = new ArrayList<Teacher>();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream())))
			{
				// --- READEING LINES FROM CSV ---
				String line;
				br.readLine(); // --- READ 1 MORE TIME FOR HEADERS ---
				while ((line = br.readLine()) != null)
				{
					// --- GETTING TEACHER ---
					Teacher teacher = this.parsearLineaCSV(line);

					// --- IF TEACHER IS NOT NULL , ADD TO LIST ---
					if (teacher != null)
					{
						log.info("Teacher was create");
						teachers.add(teacher);
					}
				}
			}
			catch (IOException exception)
			{
				String error = "In/Out exception";
				HorariosError horariosError = new HorariosError(400, error, exception);
				log.error(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
			// --- PUT CSV INFO ON SESSION ---
			session.setAttribute("csvInfo", teachers);
			return ResponseEntity.ok().body(teachers);
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);

			return ResponseEntity.status(500).body(horariosError);
		}

	}

	/**
	 * Method parsearLineaCSV
	 * 
	 * @param linea
	 * @return Teacher
	 */
	private Teacher parsearLineaCSV(String linea)
	{
		Teacher teacher = null;
		try
		{
			String[] campos = linea.split(",");
			String nombre = campos[0].trim();
			String apellido = campos[1].trim();
			String correo = campos[2].trim();
			String telefono = campos[3].trim();

			// --- SPLIT BY [ FOR THE ROL LIST ---
			String[] temporalArray = linea.split("\\[");

			// --- GETTING THE PART OF ROL LIST ---
			String stringTemporal = temporalArray[temporalArray.length - 1];

			// --- DELETE THE CHAR "]" FOR THE LAST VALUE OF ROL ---
			stringTemporal = stringTemporal.replace("]", "");

			// -- SPLIT BY "," THE CLEAN ROL STRING ---
			String[] rolesArray = stringTemporal.split(",");

			// --- GETTING EACH VALUE OF STRING AND PARSE TO ROL ---
			List<Rol> listaRoles = new ArrayList<Rol>();
			for (String rol : rolesArray)
			{
				switch (rol.toLowerCase().trim())
				{
				case "administrador" ->
				{
					listaRoles.add(Rol.administrador);
				}
				case "docente" ->
				{
					listaRoles.add(Rol.docente);
				}
				case "conserje" ->
				{
					listaRoles.add(Rol.conserje);
				}
				}
			}
			teacher = new Teacher(nombre, apellido, correo, telefono, listaRoles);
		}
		catch (IllegalArgumentException illegalArgumentException)
		{
			log.info("Datos de CSV incompletos o incorrectos: " + linea);
		}

		return teacher;
	}

	/**
	 * Method getTeachers
	 * 
	 * @param session
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/teachers", produces = "application/json")
	public ResponseEntity<?> getTeachers(HttpSession session)
	{
		try
		{
			List<Teacher> teacherList = new ArrayList<Teacher>();

			// --- GETTING CSV FROM SESSION ---
			if (session.getAttribute("csvInfo") != null && session.getAttribute("csvInfo") instanceof List)
			{
				// --- CASTING TEACHER LIST ---
				teacherList = (List<Teacher>) session.getAttribute("csvInfo");
				return ResponseEntity.ok().body(teacherList);
			}
			else
			{
				// --- ERROR NO CSV INFO OR EMPTY ON SESSION ---
				String error = "no csv info";
				HorariosError horariosError = new HorariosError(400, error, null);
				log.error(error, horariosError);
				return ResponseEntity.status(500).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
		}

	}

	/**
	 * Method getListCourse
	 * 
	 * @param session
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/courses", produces = "application/json")
	public ResponseEntity<?> getListCourse(HttpSession session)
	{
		List<Course> listaCurso = new ArrayList<Course>();
		Course curso;
		Classroom classroom;
		List<Aula> listaAula = new ArrayList<Aula>();
		try
		{
			// --- GETTING STORED CENTRO ---
			if (session.getAttribute("storedCentro") != null && session.getAttribute("storedCentro") instanceof Centro)
			{
				Centro centro = (Centro) session.getAttribute("storedCentro");
				// -- GETTING LIST OF AULA IN CENTER ---
				listaAula = centro.getDatos().getAulas().getAula();

				// -- FOR EAHC AULA IN listAula ---
				for (int i = 0; i < listaAula.size(); i++)
				{
					if (listaAula.get(i).getAbreviatura().isEmpty() || listaAula.get(i).getAbreviatura() == null)
					{
						continue;
					}
					String nombreAula = listaAula.get(i).getNombre();

					String[] plantaAula = listaAula.get(i).getAbreviatura().split("\\.");

					String plantaNumero = "";
					String numeroAula = "";
					// -- THE VALUES WITH CHARACTERS ONLY HAVE 1 POSITION ---
					if (plantaAula.length > 1)
					{
						plantaNumero = plantaAula[0].trim();
						numeroAula = plantaAula[1].trim();
					}
					else
					{
						plantaNumero = plantaAula[0].trim();
						numeroAula = plantaAula[0].trim();
					}

					// -- IMPORTANT , CLASSROOM PLANTANUMERO AND NUMEROAULA , CHANGED TO STRING
					// BECAUSE SOME PARAMETERS CONTAINS CHARACTERS ---
					classroom = new Classroom(plantaNumero, numeroAula);
					curso = new Course(nombreAula, classroom);
					listaCurso.add(curso);
				}
			}
			else
			{
				// -- ERROR NO CENTRO STORED OR EMPTY---
				String error = "Error to read XML data centro or Empty";
				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}

		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
		return ResponseEntity.ok().body(listaCurso);
	}

	/**
	 * Method getClassroomTeacher
	 * 
	 * @param name
	 * @param lastname
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/classroom", produces = "application/json")
	public ResponseEntity<?> getClassroomTeacher(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastname, HttpSession session)
	{
		try
		{
			if (!name.isEmpty() && !name.isBlank() && !lastname.isBlank() && !lastname.isEmpty())
			{
				// --- GETTING THE STORED CENTRO DATA SESSION ---
				if (session.getAttribute("storedCentro") != null
						&& session.getAttribute("storedCentro") instanceof Centro)
				{
					Centro centro = (Centro) session.getAttribute("storedCentro");
					for (Profesor prof : centro.getDatos().getProfesores().getProfesor())
					{
						String profName = prof.getNombre().trim().toLowerCase();
						String profLastName = prof.getPrimerApellido().trim().toLowerCase() + " "
								+ prof.getSegundoApellido().trim().toLowerCase();

						log.info(prof.toString());
						if (profName.equalsIgnoreCase(name.trim()) && profLastName.equalsIgnoreCase(lastname.trim()))
						{
							log.info("EXISTE " + prof);

							// Getting the actual time
							String actualTime = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
							log.info(actualTime);

							Tramo profTramo = null;
							profTramo = gettingTramoActual(centro, actualTime, profTramo);

							// --- IF PROF TRAMO IS NOT NULL ---
							if (profTramo != null)
							{
								for (HorarioProf horarioProf : centro.getHorarios().getHorariosProfesores()
										.getHorarioProf())
								{
									if (prof.getNumIntPR().equalsIgnoreCase(horarioProf.getHorNumIntPR()))
									{
										log.info("ENCONTRADO HORARIO PROF-> " + horarioProf);

										Actividad profActividad = null;
										for (Actividad actividad : horarioProf.getActividad())
										{
											if (actividad.getTramo().trim()
													.equalsIgnoreCase(profTramo.getNumTr().trim()))
											{
												log.info("ENCONTRADO ACTIVIDAD -> " + actividad);
												profActividad = actividad;

												// --- LEAVING ACTIVIDAD FOR EACH ---
												break;
											}
										}
										if (profActividad == null)
										{
											log.info("EL TRAMO " + profTramo
													+ "\nNO EXISTE EN LAS ACTIVIDADES DEL PROFESOR " + prof);
											// --- ERROR ---
											String error = "EL TRAMO " + profTramo
													+ "\nNO EXISTE EN LAS ACTIVIDADES DEL PROFESOR " + prof;
											HorariosError horariosError = new HorariosError(500, error, null);
											log.info(error, horariosError);
											return ResponseEntity.status(400).body(horariosError);
										}

										// --- IF PROF ACTIVIAD IS NOT NULL ---
										if (profActividad != null)
										{
											// --- GETTING THE ACTUAL AULA FROM AND GENERATE CLASSROOM ---
											Aula profAula = null;
											for (Aula aula : centro.getDatos().getAulas().getAula())
											{
												if (aula.getNumIntAu().trim()
														.equalsIgnoreCase(profActividad.getAula().trim()))
												{
													log.info("AULA ENCONTRADA PARA LA ACTIVIDAD --> " + profActividad
															+ "\n" + aula);
													// --- SETTING THE AULA VALUE TO PROF AULA ---
													profAula = aula;

													// --- LEAVING AULA FOR EACH ---
													break;
												}
											}

											if (profAula != null)
											{
												log.info("AULA ACTUAL PROFESOR: " + prof + "\n" + profAula);
												String nombreAula = profAula.getNombre();

												String[] plantaAula = profAula.getAbreviatura().split("\\.");

												String plantaNumero = "";
												String numeroAula = "";

												// -- THE VALUES WITH CHARACTERS ONLY HAVE 1 POSITION ---
												if (plantaAula.length > 1)
												{
													plantaNumero = plantaAula[0].trim();
													numeroAula = plantaAula[1].trim();
												}
												else
												{
													plantaNumero = plantaAula[0].trim();
													numeroAula = plantaAula[0].trim();
													if (plantaNumero.isEmpty() || numeroAula.isEmpty())
													{
														plantaNumero = nombreAula;
														numeroAula = nombreAula;
													}
												}

												Classroom classroom = new Classroom(plantaNumero, numeroAula);
												log.info(classroom.toString());
												return ResponseEntity.ok().body(classroom);
											}
										}

										// --- LEAVING PROFTRAMO FOREACH ---
										break;
									}
								}
								// --- LEAVING PROF FOREACH ---
								break;
							}
							else
							{
								// --- ERROR ---
								LocalDateTime dateTime = LocalDateTime.now();
								String error = "Tramo no encontrado para fecha actual: " + dateTime + " ";
								HorariosError horariosError = new HorariosError(400, error, null);
								log.info(error, horariosError);
								return ResponseEntity.status(400).body(horariosError);
							}
						}
					}
				}
				else
				{
					// --- ERROR ---
					String error = "Error on storedcentro";
					HorariosError horariosError = new HorariosError(500, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}
			}
			// --- ERROR ---
			String error = "Error on parameters from header";
			HorariosError horariosError = new HorariosError(500, error, null);
			log.info(error, horariosError);
			return ResponseEntity.status(400).body(horariosError);
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method getClassroomTeacher
	 * 
	 * @param name
	 * @param lastname
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teacher/get/Classroom/tramo", produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> getClassroomTeacherSchedule(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastname, @RequestBody(required = true) Tramo profTramo,
			HttpSession session)
	{
		try
		{
			log.info(profTramo.toString());
			if (!name.isEmpty() && !name.isBlank() && !lastname.isBlank() && !lastname.isEmpty())
			{
				// --- GETTING THE STORED CENTRO DATA SESSION ---
				if (session.getAttribute("storedCentro") != null
						&& session.getAttribute("storedCentro") instanceof Centro)
				{
					Centro centro = (Centro) session.getAttribute("storedCentro");
					for (Profesor prof : centro.getDatos().getProfesores().getProfesor())
					{
						String profName = prof.getNombre().trim().toLowerCase();
						String profLastName = prof.getPrimerApellido().trim().toLowerCase() + " "
								+ prof.getSegundoApellido().trim().toLowerCase();

						log.info(prof.toString());
						if (profName.equalsIgnoreCase(name.trim()) && profLastName.equalsIgnoreCase(lastname.trim()))
						{
							log.info("EXISTE " + prof);

							// Getting the actual time
							String actualTime = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
							log.info(actualTime);

							// --- IF PROF TRAMO IS NOT NULL ---
							if (profTramo != null)
							{
								for (HorarioProf horarioProf : centro.getHorarios().getHorariosProfesores()
										.getHorarioProf())
								{
									if (prof.getNumIntPR().equalsIgnoreCase(horarioProf.getHorNumIntPR()))
									{
										log.info("ENCONTRADO HORARIO PROF-> " + horarioProf);

										Actividad profActividad = null;
										for (Actividad actividad : horarioProf.getActividad())
										{
											if (actividad.getTramo().trim()
													.equalsIgnoreCase(profTramo.getNumTr().trim()))
											{
												log.info("ENCONTRADO ACTIVIDAD -> " + actividad);
												profActividad = actividad;

												// --- LEAVING ACTIVIDAD FOR EACH ---
												break;
											}
										}
										if (profActividad == null)
										{
											log.info("EL TRAMO " + profTramo
													+ "\nNO EXISTE EN LAS ACTIVIDADES DEL PROFESOR " + prof);
											// --- ERROR ---
											String error = "EL TRAMO " + profTramo
													+ "\nNO EXISTE EN LAS ACTIVIDADES DEL PROFESOR " + prof;
											HorariosError horariosError = new HorariosError(500, error, null);
											log.info(error, horariosError);
											return ResponseEntity.status(400).body(horariosError);
										}

										// --- IF PROF ACTIVIAD IS NOT NULL ---
										if (profActividad != null)
										{
											// --- GETTING THE ACTUAL AULA FROM AND GENERATE CLASSROOM ---
											Aula profAula = null;
											for (Aula aula : centro.getDatos().getAulas().getAula())
											{
												if (aula.getNumIntAu().trim()
														.equalsIgnoreCase(profActividad.getAula().trim()))
												{
													log.info("AULA ENCONTRADA PARA LA ACTIVIDAD --> " + profActividad
															+ "\n" + aula);
													// --- SETTING THE AULA VALUE TO PROF AULA ---
													profAula = aula;

													// --- LEAVING AULA FOR EACH ---
													break;
												}
											}

											if (profAula != null)
											{
												log.info("AULA ACTUAL PROFESOR: " + prof + "\n" + profAula);
												String nombreAula = profAula.getNombre();

												String[] plantaAula = profAula.getAbreviatura().split("\\.");

												String plantaNumero = "";
												String numeroAula = "";

												// -- THE VALUES WITH CHARACTERS ONLY HAVE 1 POSITION ---
												if (plantaAula.length > 1)
												{
													plantaNumero = plantaAula[0].trim();
													numeroAula = plantaAula[1].trim();
												}
												else
												{
													plantaNumero = plantaAula[0].trim();
													numeroAula = plantaAula[0].trim();
													if (plantaNumero.isEmpty() || numeroAula.isEmpty())
													{
														plantaNumero = nombreAula;
														numeroAula = nombreAula;
													}
												}

												Classroom classroom = new Classroom(plantaNumero, numeroAula);
												log.info(classroom.toString());
												return ResponseEntity.ok().body(classroom);
											}
										}

										// --- LEAVING PROFTRAMO FOREACH ---
										break;
									}
								}
								// --- LEAVING PROF FOREACH ---
								break;
							}
							else
							{
								// --- ERROR ---
								String error = "Tramo introducido null" + profTramo;
								HorariosError horariosError = new HorariosError(400, error, null);
								log.info(error, horariosError);
								return ResponseEntity.status(400).body(horariosError);
							}
						}
					}
				}
				else
				{
					// --- ERROR ---
					String error = "Error on storedcentro";
					HorariosError horariosError = new HorariosError(500, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}
			}
			// --- ERROR ---
			String error = "Error on parameters from header";
			HorariosError horariosError = new HorariosError(500, error, null);
			log.info(error, horariosError);
			return ResponseEntity.status(400).body(horariosError);
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method getClassroomCourse
	 * 
	 * @param courseName
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/teachersubject", produces = "application/json")
	public ResponseEntity<?> getTeacherSubject(@RequestHeader(required = true) String courseName, HttpSession session)
	{
		try
		{
			if (!courseName.isBlank() && !courseName.isBlank())
			{
				if (session.getAttribute("storedCentro") != null
						&& session.getAttribute("storedCentro") instanceof Centro)
				{
					Centro centro = (Centro) session.getAttribute("storedCentro");

					// --- IF EXIST THE COURSE ---
					Grupo grup = null;
					for (Grupo grupo : centro.getDatos().getGrupos().getGrupo())
					{
						if (grupo.getNombre().trim().equalsIgnoreCase(courseName.trim()))
						{
							// --- EXIST THE COURSE ---
							grup = grupo;
						}
					}
					if (grup != null)
					{
						// --- GRUPO EXIST , NOW GET THE ACUTAL TRAMO ---
						Tramo acutalTramo = null;

						// Getting the actual time
						String actualTime = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
						log.info(actualTime);

						acutalTramo = this.gettingTramoActual(centro, actualTime, acutalTramo);

						// --- CHECKING IF THE TRAMO ACTUAL EXISTS ---
						if (acutalTramo != null)
						{
							// --- TRAMO ACTUAL EXISTS ---

							// --- NOW GETTING THE HORARIO GRUP , WITH THE SAME ID OF THE GROUP ---
							HorarioGrup horario = null;
							for (HorarioGrup horarioGrup : centro.getHorarios().getHorariosGrupos().getHorarioGrup())
							{
								// --- EQUAL IDS ---
								if (horarioGrup.getHorNumIntGr().trim().equalsIgnoreCase(grup.getNumIntGr().trim()))
								{
									// --- THE HORARIO GROUP OF THE GROUP ---
									horario = horarioGrup;
								}
							}

							// --- IF THE HORARIO GRUP EXIST ---
							if (horario != null)
							{
								// --- GETTING THE HORARIO GRUP ACTIVIDADES ----
								Actividad activ = null;
								for (Actividad actividad : horario.getActividad())
								{
									// --- GETTING THE ACTIVIDAD WITH THE SAME ID OF THE ACTUAL TRAMO ---
									if (actividad.getTramo().trim().equalsIgnoreCase(acutalTramo.getNumTr().trim()))
									{
										activ = actividad;
									}
								}

								// --- IF EXIST THIS ACTIVIDAD ---
								if (activ != null)
								{
									// --- NOW GET THE PROFESOR AND ASIGNATURA BY PROFESOR ID AND THE ASIGNATURA ID
									// ---

									// --- PROFESOR ---
									Profesor profesor = null;
									for (Profesor prof : centro.getDatos().getProfesores().getProfesor())
									{
										// --- EQUAL PROFESSOR ID --
										if (prof.getNumIntPR().trim().equalsIgnoreCase(activ.getProfesor().trim()))
										{
											profesor = prof;
										}
									}

									// --- ASIGNATURA ---
									Asignatura asignatura = null;
									for (Asignatura asig : centro.getDatos().getAsignaturas().getAsignatura())
									{
										// --- EQUAL ASIGNATURA ID --
										if (asig.getNumIntAs().trim().equalsIgnoreCase(activ.getAsignatura().trim()))
										{
											asignatura = asig;
										}
									}

									if (profesor != null && asignatura != null)
									{
										// --- THE FINAL PROFESSOR AND ASIGNATURA ---
										log.info("PROFESOR: " + profesor + "\n" + "ASIGNATURA: " + asignatura);
										TeacherMoment teacherMoment = new TeacherMoment();

										// --- TELEFONO - EMAIL - AND -ROL - IS FAKE AND HARDCODED, BECAUSE THE XML DONT
										// HAVE THIS INFO ---
										// --setting teacher---
										teacherMoment.setTeacher(new Teacher(profesor.getNombre().trim(),
												profesor.getPrimerApellido().trim() + " "
														+ profesor.getSegundoApellido().trim(),
												profesor.getNombre().trim() + "@email.com", "000-000-000",
												List.of(Rol.conserje)));

										// --- setting asignatura name ---
										teacherMoment.setSubject(asignatura.getNombre().trim());

										// --- RETURN THE THEACER MOMENT , WIOUTH CLASSROOM ---
										return ResponseEntity.ok().body(teacherMoment);

									}
									else
									{

										// --- ERROR ---
										String error = "PROFESOR O ASIGNATURA NO ENCONTRADOS O NULL " + profesor + "\n"
												+ asignatura;

										log.info(error);

										HorariosError horariosError = new HorariosError(400, error, null);
										log.info(error, horariosError);
										return ResponseEntity.status(400).body(horariosError);
									}

								}
								else
								{
									// --- ERROR ---
									String error = "ERROR , ACTIVIDAD NULL O NO ENCONTRADA";

									log.info(error);

									HorariosError horariosError = new HorariosError(400, error, null);
									log.info(error, horariosError);
									return ResponseEntity.status(400).body(horariosError);
								}

							}
							else
							{
								// --- ERROR ---
								String error = "ERROR , HORARIO GRUP NULL O NO ENCONTRADO";

								log.info(error);

								HorariosError horariosError = new HorariosError(400, error, null);
								log.info(error, horariosError);
								return ResponseEntity.status(400).body(horariosError);
							}
						}
						else
						{
							// --- ERROR ---
							String error = "ERROR , TRAMO NULL O NO EXISTE";

							log.info(error);

							HorariosError horariosError = new HorariosError(400, error, null);
							log.info(error, horariosError);
							return ResponseEntity.status(400).body(horariosError);
						}
					}
					else
					{
						// --- ERROR ---
						String error = "ERROR GRUPO NULL O NO ENCONTRADO ";

						log.info(error);

						HorariosError horariosError = new HorariosError(400, error, null);
						log.info(error, horariosError);
						return ResponseEntity.status(400).body(horariosError);
					}
				}
				else
				{
					// --- ERROR ---
					String error = "ERROR storedCentro not Found ";

					log.info(error);

					HorariosError horariosError = new HorariosError(400, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}
			}
			else
			{
				// --- ERROR ---
				String error = "ERROR , CURSO EN BLANCO O NO PERMITIDO";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method getClassroomCourse
	 * 
	 * @param courseName
	 * @param session
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/Classroomcourse", produces = "application/json")
	public ResponseEntity<?> getClassroomCourse(@RequestHeader(required = true) String courseName, HttpSession session)
	{
		try
		{
			// --- CHECKING IF THE COURSE NAME IS NOT BLANK AND NOT EMPTY ---
			if (!courseName.isBlank() && !courseName.isEmpty())
			{
				// --- CHECKING THE VALUE OF STOREDCENTRO FROM SESSION ---
				if (session.getAttribute("storedCentro") != null
						&& session.getAttribute("storedCentro") instanceof Centro)
				{
					// --- CASTING OBJECT TO CENTRO ---
					Centro centro = (Centro) session.getAttribute("storedCentro");

					// --- CHECKING IF THE GRUPO EXISTS ----
					Grupo grupo = null;
					for (Grupo grup : centro.getDatos().getGrupos().getGrupo())
					{
						if (grup.getNombre().trim().equalsIgnoreCase(courseName.trim()))
						{
							// --- GRUPO EXISTS (SET VALUE) ---
							grupo = grup;
						}
					}

					// --- IF THE GRUPO EXISTS (NOT NULL) ---
					if (grupo != null)
					{
						// --- CHECK IF THE AULA EXISTS ---
						Aula aula = null;
						for (Aula aul : centro.getDatos().getAulas().getAula())
						{
							// --- REPLACE º,'SPACE', - , FOR EMPTY , FROM GRUPO NOMBRE AND GRUPO ABRV ---
							// --- THIS IS FOR TRY TO GET THE MAX POSIBILITIES OF GET THE AULA FROM CURSO
							// ---

							String aulaName = grupo.getNombre().trim().toLowerCase().replace("º", "").replace(" ", "")
									.replace("-", "");
							String aulaAbr = grupo.getAbreviatura().trim().toLowerCase().replace("º", "")
									.replace(" ", "").replace("-", "");

							// --- CHECK IF COURSENAME EXISTS ON THE AULA NAME OR THE COURSE ABRV EXIST ON
							// THE AULA NAME ---
							if (aul.getNombre().trim().toLowerCase().replace("-", "").contains(aulaName)
									|| aul.getNombre().trim().toLowerCase().replace("-", "").contains(aulaAbr))
							{
								// -- IF EXISTS , SET THE VALUE OF AUL (FOREACH) ON AULA ---
								aula = aul;
							}
						}

						// --- IF THE AULA IS NOT NULL (EXISTS) ---
						if (aula != null)
						{
							String nombreAula = aula.getNombre();

							// --- SPLIT BY '.' ---
							String[] plantaAula = aula.getAbreviatura().split("\\.");

							String plantaNumero = "";
							String numeroAula = "";
							// -- THE VALUES WITH CHARACTERS ONLY HAVE 1 POSITION ---
							if (plantaAula.length > 1)
							{
								plantaNumero = plantaAula[0].trim();
								numeroAula = plantaAula[1].trim();
							}
							else
							{
								plantaNumero = plantaAula[0].trim();
								numeroAula = plantaAula[0].trim();
							}

							// -- IMPORTANT , CLASSROOM PLANTANUMERO AND NUMEROAULA , CHANGED TO STRING
							// BECAUSE SOME PARAMETERS CONTAINS CHARACTERS ---
							Classroom classroom = new Classroom(plantaNumero, numeroAula);

							// --- RETURN FINALLY THE CLASSROOM ---
							return ResponseEntity.ok(classroom);

						}
						else
						{
							// --- ERROR ---
							String error = "ERROR AULA NOT FOUND OR NULL";

							log.info(error);

							HorariosError horariosError = new HorariosError(400, error, null);
							log.info(error, horariosError);
							return ResponseEntity.status(400).body(horariosError);
						}

					}
					else
					{
						// --- ERROR ---
						String error = "ERROR GRUPO NOT FOUND OR NULL";

						log.info(error);

						HorariosError horariosError = new HorariosError(400, error, null);
						log.info(error, horariosError);
						return ResponseEntity.status(400).body(horariosError);
					}

				}
				else
				{
					// --- ERROR ---
					String error = "ERROR storedCentro not found or NUll";

					log.info(error);

					HorariosError horariosError = new HorariosError(400, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}

			}
			else
			{
				// --- ERROR ---
				String error = "ERROR HEADER COURSE NAME EMPTY OR BLANK";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method getListHours
	 * 
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/hours", produces = "application/json")
	public ResponseEntity<?> getListHours(HttpSession session)
	{
		try
		{
			// --- CHECKING THE VALUE OF STORED CENTRO ---
			if (session.getAttribute("storedCentro") != null && session.getAttribute("storedCentro") instanceof Centro)
			{
				// --- CASTING OBJECT TO STORED CENTRO ---
				Centro centro = (Centro) session.getAttribute("storedCentro");
				List<Hour> hourList = new ArrayList<Hour>();
				for (int i = 0; i < 7; i++)
				{
					// --- GETTING THE INFO OF EACH TRAMO, BUT ONLY THE FIRST 7 TRAMOS , BECAUSE THT
					// REPRESENT "LUNES" "PRIMERA-ULTIMA-HORA" ---
					Tramo tramo = centro.getDatos().getTramosHorarios().getTramo().get(i);

					// --- GETTING THE HOURNAME BY THE ID OF THE TRAMO 1-7 (1,2,3,R,4,5,6) ---
					String hourName = "";
					switch (tramo.getNumTr().trim())
					{
					case "1":
					{
						hourName = "primera";
						break;
					}
					case "2":
					{
						hourName = "segunda";
						break;
					}
					case "3":
					{
						hourName = "tercera";
						break;
					}
					case "4":
					{
						hourName = "recreo";
						break;
					}
					case "5":
					{
						hourName = "cuarta";
						break;
					}
					case "6":
					{
						hourName = "quinta";
						break;
					}
					case "7":
					{
						hourName = "sexta";
						break;
					}
					default:
					{
						// --- DEFAULT ---
						hourName = "Desconocido";
						break;
					}
					}
					// --- ADD THE INFO OF THE TRAMO ON HOUR OBJECT ---
					hourList.add(new Hour(hourName, tramo.getHoraInicio().trim(), tramo.getHoraFinal().trim()));
				}
				// --- RESPONSE WITH THE HOURLIST ---
				return ResponseEntity.ok(hourList);
			}
			else
			{
				// --- ERROR ---
				String error = "ERROR storedCentro NOT FOUND OR NULL";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}

	}

	/**
	 * Method getSchedulePdf
	 * 
	 * @param name
	 * @param lastname
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/horario/teacher/pdf", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getSchedulePdf(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastname)
	{
		try
		{
			if (!name.trim().isBlank() && !name.trim().isEmpty() && !lastname.trim().isBlank()
					&& !lastname.trim().isEmpty())
			{
				if (this.centroPdfs != null)
				{
					Centro centro = this.centroPdfs;

					// --- GETTING THE PROFESSOR AND CHECK IF EXISTS ---
					if (lastname.split(" ").length < 2)
					{
						// -- CATCH ANY ERROR ---
						String error = "ERROR NO HAY DOS APELLIDOS DEL PROFESOR O NO ENCONTRADOS EN HEADERS";
						HorariosError horariosError = new HorariosError(400, error, null);
						log.info(error, horariosError);
						return ResponseEntity.status(400).body(horariosError);
					}
					String profFirstLastName = lastname.trim().split(" ")[0];
					String profSecondLastName = lastname.trim().split(" ")[1];

					Profesor profesor = null;
					for (Profesor prof : centro.getDatos().getProfesores().getProfesor())
					{
						if (prof.getNombre().trim().equalsIgnoreCase(name.trim())
								&& prof.getPrimerApellido().trim().equalsIgnoreCase(profFirstLastName)
								&& prof.getSegundoApellido().trim().equalsIgnoreCase(profSecondLastName))
						{
							// --- PROFESSOR EXISTS , SET THE VALUE OF PROF IN PROFESOR ---
							profesor = prof;
							log.info("PROFESOR ENCONTRADO -> " + profesor.toString());
						}
					}

					if (profesor != null)
					{
						// --- PROFESOR EXISTS ---
						HorarioProf horarioProfesor = null;
						for (HorarioProf horarioProf : centro.getHorarios().getHorariosProfesores().getHorarioProf())
						{
							if (horarioProf.getHorNumIntPR().trim().equalsIgnoreCase(profesor.getNumIntPR().trim()))
							{
								// --- HORARIO PROFESOR EXISTS , SET THE VALUE ON HORARIO PROFESOR---
								horarioProfesor = horarioProf;
							}
						}

						if (horarioProfesor != null)
						{
							// --- HORARIO EXISTS ---
							// --- CREATING THE MAP WITH KEY STRING TRAMO DAY AND VALUE LIST OF ACTIVIDAD
							// ---
							Map<String, List<Actividad>> profesorMap = new HashMap<String, List<Actividad>>();

							// --- FOR EACH ACTIVIDAD , GET THE TRAMO DAY , AND PUT ON MAP WITH THE
							// ACTIVIDADES OF THIS DAY (LIST ACTIVIDAD) ---
							for (Actividad actividad : horarioProfesor.getActividad())
							{
								Tramo tramo = extractTramoFromCentroActividad(centro, actividad);

								// --- CHECKING IF THE TRAMO DAY EXISTS ---
								if (!profesorMap.containsKey(tramo.getNumeroDia().trim()))
								{
									// --- ADD THE NEW KEY AND VALUE ---
									List<Actividad> actividadList = new ArrayList<Actividad>();
									actividadList.add(actividad);
									Collections.sort(actividadList);

									profesorMap.put(tramo.getNumeroDia().trim(), actividadList);
								}
								else
								{
									// -- ADD THE VALUE TO THE ACTUAL VALUES ---
									List<Actividad> actividadList = profesorMap.get(tramo.getNumeroDia().trim());
									actividadList.add(actividad);
									Collections.sort(actividadList);
									profesorMap.put(tramo.getNumeroDia().trim(), actividadList);
								}
							}

							// --- CALLING TO APPLICATION PDF , TO GENERATE PDF ---
							ApplicationPdf pdf = new ApplicationPdf();
							try
							{
								// -- CALLING TO THE METHOD GET INFO PDF OF APLICATION PDF TO CREATE THE PDF ---
								pdf.getInfoPdf(centro, profesorMap, profesor);

								// --- GETTING THE PDF BY NAME URL ---
								File file = new File(
										profesor.getNombre().trim() + "_" + profesor.getPrimerApellido().trim() + "_"
												+ profesor.getSegundoApellido() + "_Horario.pdf");

								// --- SETTING THE HEADERS WITH THE NAME OF THE FILE TO DOWLOAD PDF ---
								HttpHeaders responseHeaders = new HttpHeaders();
								// --- SET THE HEADERS ---
								responseHeaders.set("Content-Disposition", "attachment; filename=" + file.getName());

								try
								{
									// --- CONVERT FILE TO BYTE[] ---
									byte[] bytesArray = Files.readAllBytes(file.toPath());

									// --- RETURN OK (200) WITH THE HEADERS AND THE BYTESARRAY ---
									return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
								}
								catch (IOException exception)
								{
									// --- ERROR ---
									String error = "ERROR GETTING THE BYTES OF PDF ";

									log.info(error);

									HorariosError horariosError = new HorariosError(500, error, exception);
									log.info(error, horariosError);
									return ResponseEntity.status(500).body(horariosError);
								}
							}
							catch (HorariosError exception)
							{
								// --- ERROR ---
								String error = "ERROR getting the info pdf ";

								log.info(error);

								HorariosError horariosError = new HorariosError(400, error, exception);
								log.info(error, horariosError);
								return ResponseEntity.status(400).body(horariosError);
							}

						}
						else
						{
							// --- ERROR ---
							String error = "ERROR HORARIO_PROFESOR NOT FOUNT OR NULL";

							log.info(error);

							HorariosError horariosError = new HorariosError(400, error, null);
							log.info(error, horariosError);
							return ResponseEntity.status(400).body(horariosError);
						}
					}
					else
					{
						// --- ERROR ---
						String error = "ERROR PROFESOR NOT FOUND OR NULL";

						log.info(error);

						HorariosError horariosError = new HorariosError(400, error, null);
						log.info(error, horariosError);
						return ResponseEntity.status(400).body(horariosError);
					}

				}
				else
				{
					// --- ERROR ---
					String error = "ERROR centroPdfs NULL OR NOT FOUND";

					log.info(error);

					HorariosError horariosError = new HorariosError(400, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}
			}
			else
			{
				// --- ERROR ---
				String error = "ERROR PARAMETROS HEADER NULL OR EMPTY, BLANK";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method extractTramoFromCentroActividad
	 * 
	 * @param centro
	 * @param actividad
	 * @param tramo
	 * @return
	 */
	private Tramo extractTramoFromCentroActividad(Centro centro, Actividad actividad)
	{
		for (Tramo tram : centro.getDatos().getTramosHorarios().getTramo())
		{
			// --- GETTING THE TRAMO ---
			if (actividad.getTramo().trim().equalsIgnoreCase(tram.getNumTr().trim()))
			{
				return tram;
			}
		}
		return null;
	}

	/**
	 * Method getSchedulePdf
	 * 
	 * @param name
	 * @param lastname
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/grupo/pdf", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getGroupSchedule(@RequestHeader(required = true) String grupo)
	{
		try
		{
			// --- CHEKING THE GRUPO ---
			if (grupo != null && !grupo.trim().isBlank() && !grupo.trim().isEmpty())
			{
				// --- CHECKING IF THE PDF CENTRO IS NULL ---
				if (this.centroPdfs != null)
				{
					// --- CHEKING IF GRUPO EXISTS ---
					Grupo grupoFinal = null;
					for (Grupo grup : this.centroPdfs.getDatos().getGrupos().getGrupo())
					{
						// --- RAPLACING "SPACE", " º " AND " - " FOR EMPTY , THT IS FOR GET MORE
						// SPECIFIC DATA ---
						String grupoParam = grupo.replace(" ", "").replace("-", "").replace("º", "");
						String grupName = grup.getNombre().replace(" ", "").replace("-", "").replace("º", "");
						String grupAbr = grup.getAbreviatura().replace(" ", "").replace("-", "").replace("º", "");

						if (grupName.trim().toLowerCase().contains(grupoParam.trim().toLowerCase())
								|| grupAbr.trim().toLowerCase().contains(grupoParam.trim().toLowerCase()))
						{
							grupoFinal = grup;
						}
					}

					// --- IF GRUPO EXISTS ---
					if (grupoFinal != null)
					{
						// --- GRUPO EXISTS ---

						// -- CHEKING HORARIO_GRUP FROM GRUPO_FINAL ---
						HorarioGrup horarioGrup = null;
						for (HorarioGrup horarioGrp : this.centroPdfs.getHorarios().getHorariosGrupos()
								.getHorarioGrup())
						{
							// -- GETTING THE HORARIO_GRUP OF THE GRUP ---
							if (horarioGrp.getHorNumIntGr().trim().equalsIgnoreCase(grupoFinal.getNumIntGr().trim()))
							{
								horarioGrup = horarioGrp;
							}
						}

						// --- IF EXISTS HORARIO_GRUP ---
						if (horarioGrup != null)
						{
							// --- GETTING THE ACTIVIDAD LIST OF THE GRUPO ---
							List<Actividad> actividadList = horarioGrup.getActividad();

							// --- ACTIVIDAD_LIST HV MORE THAN 0 ACTIVIDAD AN IS NOT NULL ---
							if (actividadList != null && actividadList.size() > 0)
							{
								// --- GENERATE THE MAP FOR TRAMO DAY , AND ACTIVIDAD LIST ---
								Map<String, List<Actividad>> grupoMap = new HashMap<String, List<Actividad>>();

								// --- CALSIFICATE EACH ACTIVIDAD ON THE SPECIFIC DAY ---
								for (Actividad actv : actividadList)
								{
									// --- GET THE TRAMO ---
									Tramo tramo = this.extractTramoFromCentroActividad(this.centroPdfs, actv);

									// --- IF THE MAP NOT CONTAINS THE TRAMO DAY NUMBER , ADD THE DAY NUMBER AND THE
									// ACTIVIDAD LIST ---
									if (!grupoMap.containsKey(tramo.getNumeroDia().trim()))
									{
										List<Actividad> temporalList = new ArrayList<Actividad>();
										temporalList.add(actv);
										grupoMap.put(tramo.getNumeroDia().trim(), temporalList);

									}
									else
									{
										// --- IF THE MAP ALRREADY CONTAINS THE TRAMO DAY , GET THE ACTIVIDAD LIST AND
										// ADD THE ACTV , FINALLY PUT THEN ON THE DAY ---
										List<Actividad> temporalList = grupoMap.get(tramo.getNumeroDia().trim());
										temporalList.add(actv);
										grupoMap.put(tramo.getNumeroDia().trim(), temporalList);
									}
								}

								// --- IF THE MAP IS NOT EMPTY , LAUNCH THE PDF GENERATION ---
								if (!grupoMap.isEmpty())
								{

									log.info(grupoMap.toString());

									try
									{
										ApplicationPdf applicationPdf = new ApplicationPdf();
										applicationPdf.getInfoPdfHorarioGrupoCentro(this.centroPdfs, grupoMap,
												grupo.trim());

										// --- GETTING THE PDF BY NAME URL ---
										File file = new File("Horario" + grupo + ".pdf");

										// --- SETTING THE HEADERS WITH THE NAME OF THE FILE TO DOWLOAD PDF ---
										HttpHeaders responseHeaders = new HttpHeaders();

										// --- REPLACE SPACES AND º BECAUSE THAT MADE CONFLICTS ON SAVE FILE ---
										String fileName = file.getName().replace("º", "").replace(" ", "_");
										// --- SET THE HEADERS ---
										responseHeaders.set("Content-Disposition", "attachment; filename=" + fileName);

										// --- CONVERT FILE TO BYTE[] ---
										byte[] bytesArray = Files.readAllBytes(file.toPath());

										// --- RETURN OK (200) WITH THE HEADERS AND THE BYTESARRAY ---
										return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
									}
									catch (HorariosError exception)
									{
										// --- ERROR ---
										String error = "ERROR getting the info pdf ";

										log.info(error);

										HorariosError horariosError = new HorariosError(400, error, exception);
										log.info(error, horariosError);
										return ResponseEntity.status(400).body(horariosError);
									}

								}
								else
								{
									// --- ERROR ---
									String error = "ERROR grupoMap IS EMPTY OR NOT FOUND";

									log.info(error);

									HorariosError horariosError = new HorariosError(400, error, null);
									log.info(error, horariosError);
									return ResponseEntity.status(400).body(horariosError);
								}
							}
							else
							{
								// --- ERROR ---
								String error = "ERROR actividadList HAVE 0 ACTIVIDAD OR IS NULL";

								log.info(error);

								HorariosError horariosError = new HorariosError(400, error, null);
								log.info(error, horariosError);
								return ResponseEntity.status(400).body(horariosError);
							}

						}
						else
						{
							// --- ERROR ---
							String error = "ERROR horarioGrup NULL OR NOT FOUND";

							log.info(error);

							HorariosError horariosError = new HorariosError(400, error, null);
							log.info(error, horariosError);
							return ResponseEntity.status(400).body(horariosError);
						}
					}
					else
					{
						// --- ERROR ---
						String error = "ERROR GRUPO_FINAL NULL OR NOT FOUND";

						log.info(error);

						HorariosError horariosError = new HorariosError(400, error, null);
						log.info(error, horariosError);
						return ResponseEntity.status(400).body(horariosError);
					}
				}
				else
				{
					// --- ERROR ---
					String error = "ERROR CENTRO_PDFS NULL OR NOT FOUND";

					log.info(error);

					HorariosError horariosError = new HorariosError(400, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}
			}
			else
			{
				// --- ERROR ---
				String error = "ERROR GRUPO PARAMETER ERROR";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.info(error, horariosError);
			return ResponseEntity.status(500).body(horariosError);
		}
	}

	/**
	 * Method getTeacherClassroom
	 * 
	 * @param name
	 * @param lastname
	 * @param session
	 * @return ResponseEntity
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/get/teacher/Classroom", produces = "application/json")
	public ResponseEntity<?> getTeacherClassroom(@RequestHeader(required = true) String name,
			@RequestHeader(required = true) String lastname, HttpSession session)
	{
		// -- LISTA ESTUDIANTES -- (FAKE) ---
		List<Student> listaEstudiantes = new ArrayList<Student>(
				List.of(new Student("David", "Martinez Flores", new Course("1ESOA", null)),
						new Student("Pablo", "Fernandez Garcia", new Course("2ESOB", null)),
						new Student("Manuel", "Belmonte Oliva", new Course("3ESOA", null)),
						new Student("Carlos", "Ruiz Araque", new Course("4ESOA", null))));

		try
		{
			// --- checking stored CENTRO ---
			if (session.getAttribute("storedCentro") != null && session.getAttribute("storedCentro") instanceof Centro)
			{
				Centro centro = (Centro) session.getAttribute("storedCentro");

				if (name != null && !name.trim().isBlank() && !name.trim().isEmpty())
				{
					// -- NOMBRE Y APELLIDOS CON CONTENIDO ---

					Student student = null;
					for (Student st : listaEstudiantes)
					{
						// -- CHECKING IF STUDENT EXISTS ---
						if (st.getName().trim().equalsIgnoreCase(name.trim())
								&& st.getLastName().trim().equalsIgnoreCase(lastname.trim()))
						{
							student = st;
						}

					}

					if (student != null)
					{
						// --- STUDENT EXISTS ---
						Grupo grupo = null;
						for (Grupo grp : centro.getDatos().getGrupos().getGrupo())
						{
							String nombreGrp = grp.getNombre().trim().replace("º", "").replace(" ", "").replace("-",
									"");
							String abrvGrp = grp.getAbreviatura().trim().replace("º", "").replace(" ", "").replace("-",
									"");

							log.info(student.getCourse().toString());
							String nombreGrupo = student.getCourse().getName().trim().replace("º", "").replace(" ", "")
									.replace("-", "");

							if (nombreGrp.toLowerCase().contains(nombreGrupo.toLowerCase())
									|| abrvGrp.toLowerCase().contains(nombreGrupo.toLowerCase()))
							{
								grupo = grp;
							}
						}

						if (grupo != null)
						{
							// --- GRUPO EXISTS ---

							HorarioGrup horarioGrup = null;
							for (HorarioGrup horarioGrp : centro.getHorarios().getHorariosGrupos().getHorarioGrup())
							{
								if (horarioGrp.getHorNumIntGr().trim().equalsIgnoreCase(grupo.getNumIntGr().trim()))
								{
									horarioGrup = horarioGrp;
								}
							}

							if (horarioGrup != null)
							{
								// --- HORARIO_GRUP EXISTS ---

								// Getting the actual time
								String actualTime = LocalDateTime.now().getHour() + ":"
										+ LocalDateTime.now().getMinute();

								Tramo tramoActual = null;

								tramoActual = this.gettingTramoActual(centro, actualTime, tramoActual);

								if (tramoActual != null)
								{
									// --- TRAMO ACTUAL EXISTS ---
									Actividad actividadActual = null;

									for (Actividad actv : horarioGrup.getActividad())
									{
										if (actv.getTramo().trim().equalsIgnoreCase(tramoActual.getNumTr().trim()))
										{
											actividadActual = actv;
										}
									}

									if (actividadActual != null)
									{
										log.info(actividadActual.toString());
										// --- ACTIVIDAD ACTUAL EXISTS ---
										TeacherMoment teacherMoment = new TeacherMoment();
										Teacher teacher = new Teacher();
										Classroom classroom = new Classroom();

										// -- GETTING TEACHER ---
										for (Profesor profesor : centro.getDatos().getProfesores().getProfesor())
										{
											if (profesor.getNumIntPR().trim()
													.equalsIgnoreCase(actividadActual.getProfesor().trim()))
											{
												teacher.setName(profesor.getNombre().trim());
												teacher.setLastName(profesor.getPrimerApellido().trim() + " "
														+ profesor.getSegundoApellido().trim());
											}
										}

										// --- GETTING THE CLASSROOM ---
										for (Aula aula : centro.getDatos().getAulas().getAula())
										{
											if (aula.getNumIntAu().trim()
													.equalsIgnoreCase(actividadActual.getAula().trim()))
											{
												String nombreAula = aula.getNombre();

												String[] plantaAula = aula.getAbreviatura().split("\\.");

												String plantaNumero = "";
												String numeroAula = "";
												// -- THE VALUES WITH CHARACTERS ONLY HAVE 1 POSITION ---
												if (plantaAula.length > 1)
												{
													plantaNumero = plantaAula[0].trim();
													numeroAula = plantaAula[1].trim();
												}
												else
												{
													plantaNumero = plantaAula[0].trim();
													numeroAula = plantaAula[0].trim();
												}

												classroom.setFloor(plantaNumero);
												classroom.setNumber(numeroAula);
											}
										}

										// --- BUILD THE TEACHER MOMENT ---
										teacherMoment.setClassroom(classroom);
										teacherMoment.setTeacher(teacher);

										log.info(teacherMoment.toString());

										// --- RETURN THE TEACHER MOMENT ---
										return ResponseEntity.ok(teacherMoment);
									}
									else
									{
										// --- ERROR ---
										String error = "ERROR ACTIVDAD ACTUAL NO EXISTENTE OR NULL";

										log.info(error);

										HorariosError horariosError = new HorariosError(400, error, null);
										log.info(error, horariosError);
										return ResponseEntity.status(400).body(horariosError);
									}

								}
								else
								{
									// --- ERROR ---
									String error = "ERROR TRAMO ACTUAL NO EXISTENTE OR NULL";

									log.info(error);

									HorariosError horariosError = new HorariosError(400, error, null);
									log.info(error, horariosError);
									return ResponseEntity.status(400).body(horariosError);
								}

							}
							else
							{
								// --- ERROR ---
								String error = "ERROR HORARIO GRUP NOT FOUND OR NULL";

								log.info(error);

								HorariosError horariosError = new HorariosError(400, error, null);
								log.info(error, horariosError);
								return ResponseEntity.status(400).body(horariosError);
							}

						}
						else
						{
							// --- ERROR ---
							String error = "GRUPO NOT FOUND OR NULL";

							log.info(error);

							HorariosError horariosError = new HorariosError(400, error, null);
							log.info(error, horariosError);
							return ResponseEntity.status(400).body(horariosError);
						}

					}
					else
					{
						// --- ERROR ---
						String error = "ERROR STUDENT NOT FOUND OR NULL";

						log.info(error);

						HorariosError horariosError = new HorariosError(400, error, null);
						log.info(error, horariosError);
						return ResponseEntity.status(400).body(horariosError);
					}

				}
				else
				{
					// --- ERROR ---
					String error = "ERROR DE PARAMETROS";

					log.info(error);

					HorariosError horariosError = new HorariosError(400, error, null);
					log.info(error, horariosError);
					return ResponseEntity.status(400).body(horariosError);
				}

			}
			else
			{
				// --- ERROR ---
				String error = "ERROR storedCentro NOT FOUND OR NULL";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, null);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		catch (Exception exception)
		{
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
		}

	}

	/**
	 * Method gettingTramoActual
	 * @param centro
	 * @param actualTime
	 * @param tramoActual
	 * @return
	 */
	private Tramo gettingTramoActual(Centro centro, String actualTime, Tramo tramoActual)
	{
		for (Tramo tramo : centro.getDatos().getTramosHorarios().getTramo())
		{
			int numTr = Integer.parseInt(tramo.getNumTr());

			// --- GETTING THE HORA,MINUTO , INICIO AND FIN ---
			int horaInicio = Integer.parseInt(tramo.getHoraInicio().split(":")[0].trim());
			int minutoInicio = Integer.parseInt(tramo.getHoraInicio().split(":")[1].trim());

			int horaFin = Integer.parseInt(tramo.getHoraFinal().split(":")[0].trim());
			int minutoFin = Integer.parseInt(tramo.getHoraFinal().split(":")[1].trim());

			// --- GETTING THE HORA, MINUTO ACTUAL ---
			int horaActual = Integer.parseInt(actualTime.split(":")[0].trim());
			int minutoActual = Integer.parseInt(actualTime.split(":")[1].trim());

			// --- USE CALENDAR INSTANCE FOR GET INTEGER WITH THE NUMBER OF THE DAY ON THE
			// WEEK ---
			Calendar calendar = Calendar.getInstance();
			// --- PARSIN CALENDAR DAY_OF_WEK TO NUMBER -1 (-1 BECAUSE THIS START ON
			// SUNDAY)--
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

			// --- IF DAY IS 0 , IS 7 , BACUSE IS SUNDAY ---
			if (dayOfWeek == 0)
			{
				dayOfWeek = 7;
			}
			if (dayOfWeek >= 6)
			{
				log.warn("DIA EXCEDIDO: (6:SABADO-7:DOMINGO) -> " + dayOfWeek);
			}

			// --- DAY OF TRAMO ---
			if (Integer.parseInt(tramo.getNumeroDia()) == dayOfWeek)
			{
				// --- IF HORA ACTUAL EQUALS HORA INICIO ---
				if (horaActual == horaInicio)
				{
					// --- CHEKING IF THE MINUTO ACTUAL IS GREATER THAN THE MINUTO INICIO AND
					// HORA ACTUAL LESS THAN HORA FIN ---
					if (minutoActual >= minutoInicio && horaActual <= horaFin)
					{
						// --- SETTING THE VALUE OF TRAMO INTO PROF TRAMO ---
						log.info("ENCONTRADO -> " + tramo);
						tramoActual = tramo;

					}
				}
				// --- IF HORA ACTUAL EQUALS HORA FIN ---
				else if (horaActual == horaFin)
				{
					// --- CHEKING IF THE MINUTO ACTUAL IS LESS THAN MINUTO FIN ---
					if (minutoActual <= minutoFin)
					{
						// --- SETTING THE VALUE OF TRAMO INTO PROF TRAMO ---
						log.info("ENCONTRADO -> " + tramo);
						tramoActual = tramo;

					}
				}
			}
		}
		return tramoActual;
	}
	
	/**
	 * Method getTeachersSchedule
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET , value = "/get/teachers/pdf" , produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getTeachersSchedule()
	{
		try {
		Map<Profesor,Map<String,List<Actividad>>> mapProfesors = new HashMap<Profesor,Map<String,List<Actividad>>>();
		if(this.centroPdfs!=null) 
		{
			// --- CENTRO PDF IS LOADED---
			for(Profesor profesor: this.centroPdfs.getDatos().getProfesores().getProfesor()) 
			{
				// --- FOR EACH PROFESOR ---
				HorarioProf horarioProf = null;
				for(HorarioProf horarioPrf : this.centroPdfs.getHorarios().getHorariosProfesores().getHorarioProf()) 
				{
					if(horarioPrf.getHorNumIntPR().trim().equalsIgnoreCase(profesor.getNumIntPR().trim())) 
					{
						horarioProf = horarioPrf;
					}
				}
				
				if(horarioProf!=null) 
				{
					// --- HORARIO PROF EXISTS ---
					
					// --- FOR EACH ACTIVIDAD ---
					Map<String,List<Actividad>> mapProfesor = new HashMap<String,List<Actividad>>();
					for(Actividad atcv : horarioProf.getActividad()) 
					{
						Tramo temporalTramo = this.extractTramoFromCentroActividad(centroPdfs, atcv);
						
						if(!mapProfesor.containsKey(temporalTramo.getNumeroDia().trim())) 
						{
							List<Actividad> temporalList = new ArrayList<Actividad>();
							temporalList.add(atcv);
							mapProfesor.put(temporalTramo.getNumeroDia().trim(), temporalList);
						}
						else 
						{
							List<Actividad> temporalList = mapProfesor.get(temporalTramo.getNumeroDia().trim());
							temporalList.add(atcv);
							mapProfesor.put(temporalTramo.getNumeroDia().trim(), temporalList);
						}
					}

					// --- ADD THE PROFESSOR WITH THE PROFESSOR MAP ---
					mapProfesors.put(profesor, mapProfesor);
				}
				else 
				{
					log.error("ERROR profesor "+profesor+" HORARIO PROF NOT FOUND OR NULL");
				}
			}
			
			try
			{
				// --- USING APPLICATION PDF TO GENERATE THE PDF , WITH ALL TEACHERS ---
				ApplicationPdf applicationPdf = new ApplicationPdf();
				applicationPdf.getAllTeachersPdfInfo(mapProfesors,this.centroPdfs);
				
				// --- GETTING THE PDF BY NAME URL ---
				File file = new File("All_Teachers_Horarios.pdf");

				// --- SETTING THE HEADERS WITH THE NAME OF THE FILE TO DOWLOAD PDF ---
				HttpHeaders responseHeaders = new HttpHeaders();
				// --- SET THE HEADERS ---
				responseHeaders.set("Content-Disposition", "attachment; filename=" + file.getName());

				try
				{
					// --- CONVERT FILE TO BYTE[] ---
					byte[] bytesArray = Files.readAllBytes(file.toPath());

					// --- RETURN OK (200) WITH THE HEADERS AND THE BYTESARRAY ---
					return ResponseEntity.ok().headers(responseHeaders).body(bytesArray);
				}
				catch (IOException exception)
				{
					// --- ERROR ---
					String error = "ERROR GETTING THE BYTES OF PDF ";

					log.info(error);

					HorariosError horariosError = new HorariosError(500, error, exception);
					log.info(error, horariosError);
					return ResponseEntity.status(500).body(horariosError);
				}
			}
			catch (HorariosError exception)
			{
				// --- ERROR ---
				String error = "ERROR getting the info pdf ";

				log.info(error);

				HorariosError horariosError = new HorariosError(400, error, exception);
				log.info(error, horariosError);
				return ResponseEntity.status(400).body(horariosError);
			}
		}
		else 
		{
			// --- ERROR ---
			String error = "ERROR centroPdfs IS NULL OR NOT FOUND";

			log.info(error);

			HorariosError horariosError = new HorariosError(400, error, null);
			log.info(error, horariosError);
			return ResponseEntity.status(400).body(horariosError);
		}
		}catch (Exception exception)
		{
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error, exception);
			log.error(error, exception);
			return ResponseEntity.status(500).body(horariosError);
		}
	}
}
