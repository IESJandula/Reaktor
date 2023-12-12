package es.reaktor.horarios.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import es.reaktor.horarios.models.Classroom;
import es.reaktor.horarios.models.Course;
import es.reaktor.horarios.models.Rol;
import es.reaktor.horarios.models.Student;
import es.reaktor.horarios.models.Teacher;
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
					Element horariosAulasElement = (Element) horariosElement.getElementsByTagName("HORARIOS_AULAS").item(0);
	
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
					log.info("File :"+xmlFile.getName()+" load-Done");
				}
				catch (ParserConfigurationException exception)
				{
					String error = "Parser Configuration Exception";
					log.error(error,exception);
					HorariosError horariosException = new HorariosError(404,exception.getLocalizedMessage(),exception);
					return ResponseEntity.status(404).body(horariosException.toMap());
	
				}
				catch (SAXException exception)
				{
					String error = "SAX Exception";
					log.error(error,exception);
					HorariosError horariosException = new HorariosError(404,exception.getLocalizedMessage(),exception);
					return ResponseEntity.status(404).body(horariosException.toMap());
				}
				catch (IOException exception)
				{
					String error = "In Out Exception";
					log.error(error,exception);
					HorariosError horariosException = new HorariosError(404,exception.getLocalizedMessage(),exception);
					return ResponseEntity.status(404).body(horariosException.toMap());
				}
	
				// --- SESSION ---------
				session.setAttribute("storedCentro", centro);
				log.info("UserVisits: " + centro);
				// --- SESSION RESPONSE_ENTITY ---------
				return ResponseEntity.ok(session.getAttribute("storedCentro"));
			}
			else
			{
				// NO ES UN XML
				String error = "The file is not a XML file";
				log.error(error);
				HorariosError horariosException = new HorariosError(500,error,new Exception());
				return ResponseEntity.status(404).body(horariosException.toMap());
			}
		}
		catch(Exception except)
		{
			// SERVER ERROR
			String error = "Server Error";
			log.error(error,except);
			HorariosError horariosException = new HorariosError(500,except.getLocalizedMessage(),except);
			return ResponseEntity.status(500).body(horariosException.toMap());
		}
	}

	
	/**
	 * Method getRoles , returns ResponseEntity with the teacher roles
	 * @param email
	 * @param session
	 * @return ResponseEntity
	 */ 
	@RequestMapping(method= RequestMethod.GET ,value = "/get/roles" ,produces="application/json")
	public ResponseEntity<?> getRoles(@RequestHeader(required=true) String email,HttpSession session)
	{
		try 
		{
			// --- VALIDATING CENTRO ---
			if(session.getAttribute("storedCentro")!=null && session.getAttribute("storedCentro") instanceof Centro) 
			{
				// --- GETTING CENTRO FROM SESSION ---
				Centro centro = (Centro) session.getAttribute("storedCentro");
				if(!email.trim().isEmpty()) 
				{
					List<Teacher> teacherList = new ArrayList<Teacher>();
					
					// --- PARSING PROFESORES TO TEACHERS ---
					// NEED CHANGE IT FOR GET THE CSV TEACHERS (CARLOS_ENDPO_2)
					for(Profesor profesor : centro.getDatos().getProfesores().getProfesor()) 
					{
						Teacher newTeacherInstance = new Teacher();
						newTeacherInstance.setName(profesor.getNombre().trim());
						newTeacherInstance.setLastName((profesor.getPrimerApellido()+","+profesor.getSegundoApellido()).trim());
						// --- GENERATING RANDOM EMAILS ---
						newTeacherInstance.setEmail("exampleEmail@example.com"+newTeacherInstance.getName().charAt(0)+""+newTeacherInstance.getLastName().charAt(0));
						newTeacherInstance.setRoles(List.of(Rol.administrador,Rol.conserje,Rol.docente));
						teacherList.add(newTeacherInstance);
						log.info(newTeacherInstance.toString());
					}
					
					// --- GETTING THE TEACHER WITH THE SPECIFIC EMAIL ---
					for(Teacher teacher:teacherList) 
					{
						if(teacher.getEmail().equals(email)) 
						{
							return ResponseEntity.ok().body(teacher);
						}
					}
				}
				else 
				{
					// --- EMAIL NOT VALID ---
					String error = "Email is not valid";
					HorariosError horariosError = new HorariosError(400, error,null);
					return ResponseEntity.status(400).body(horariosError);
				}
			}
	
			String error = "XML data is not loadaed or not found";
			HorariosError horariosError = new HorariosError(400, error,null);
			return ResponseEntity.status(400).body(horariosError);
		}
		catch(Exception exception)
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error,exception);
			return ResponseEntity.status(500).body(horariosError);	
		}
	}
	
	/**
	 * Method getListStudentsAlphabetically
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/get/sortstudents",produces = "application/json")
	public ResponseEntity<?> getListStudentsAlphabetically()
	{
		try 
		{
			// --- CREATING FAKE STUDEN LIST ---
			List<Student> studentList = new ArrayList<Student>();
			for(int i = 100 ; i>studentList.size();i--) 
			{
				Student student = new Student();
				student.setCourse(new Course("Course"+i,new Classroom(i,i)));
				student.setName("Alumno1"+(int)(Math.random()*100+1));
				student.setLastName("PrimerApp"+(int)(Math.random()*100+1));
				studentList.add(student);
			}
			// -- IF ALL ITS DONE , RETURN THE LIST SORTED ---
			Collections.sort(studentList);
			return ResponseEntity.ok().body(studentList);
		}
		catch(Exception exception) 
		{
			// -- CATCH ANY ERROR ---
			String error = "Server Error";
			HorariosError horariosError = new HorariosError(500, error,exception);
			return ResponseEntity.status(500).body(horariosError);
		}
	}
	/**
	 * Method gettingValuesOfHorarioProf
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
			newProfesor.setNombre(nombreCompletoSpit[nombreCompletoSpit.length-1].trim());
			newProfesor.setPrimerApellido(apellidosSplit[0].trim());
			newProfesor.setSegundoApellido(apellidosSplit[1].trim());

			profesoresList.add(newProfesor);
		}
	}

	/**
	 * Method gettingValuesOfAula
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
}
