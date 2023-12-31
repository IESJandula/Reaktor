package es.iesjandula.horarios.constants;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.iesjandula.horarios.models.Alumno;
/**
 * @author Pablo Ruiz Canovas
 */
public final class Constantes 
{
	/**CARGADO DE ALUMNOS*/
	public static final Alumno [] cargarAlumnos()
	{
		Alumno [] alumnos = new Alumno [8];
		alumnos [0] = new Alumno("Pablo", "Ruiz Canovas","2DAM");
		alumnos [1] = new Alumno("Javier", "Martinez Megias","2DAM");
		alumnos [2] = new Alumno("Eduardo", "Moreno Algaba","2DAM");
		alumnos [3] = new Alumno("Oliver", "Rufian Baudet","1BACH");
		alumnos [4] = new Alumno("Alberto", "Soto Pascual","2BACH");
		alumnos [5] = new Alumno("Lucia", "Diaz Gutierrez","4ESO");
		alumnos [6] = new Alumno("Fernando", "Torres Arias","3ESO");
		alumnos [7] = new Alumno("Maria", "Gomez Perez","1ESO");
		return alumnos;
	}
	
	/**CARGADO DE HORAS */
	public static final Map<String,List<String>> cargarHoras()
	{
		Map<String,List<String>> mapa = new HashMap<String,List<String>>();
		List<String> datos = new LinkedList<String>();
		datos.add("primera");
		datos.add("segunda");
		datos.add("tercera");
		datos.add("recreo");
		datos.add("cuarta");
		datos.add("quinta");
		datos.add("sexta");
		mapa.put("hora",datos);
		datos = new LinkedList<String>();
		datos.add("8:15");
		datos.add("9:15");
		datos.add("10:15");
		datos.add("11:15");
		datos.add("11:45");
		datos.add("12:45");
		datos.add("13:45");
		mapa.put("comienzo",datos);
		datos = new LinkedList<String>();
		datos.add("9:15");
		datos.add("10:15");
		datos.add("11:15");
		datos.add("11:45");
		datos.add("12:45");
		datos.add("13:45");
		datos.add("14:45");
		mapa.put("fin",datos);
		
		return mapa;
	}
}
