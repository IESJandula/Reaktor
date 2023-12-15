package es.iesjandula.horarios.constants;
import java.util.LinkedList;
import java.util.List;
import es.iesjandula.horarios.models.Alumno;
/**
 * @author Pablo Ruiz Canovas
 */
public final class Constantes 
{
	/**CARGADO DE ALUMNOS*/
	public static List<Alumno> cargarAlumnos()
	{
		List<Alumno> alumnos = new LinkedList<Alumno>();
		alumnos.add(new Alumno("Pablo", "Ruiz Canovas","2DAM"));
		alumnos.add(new Alumno("Javier", "Martinez Megias","2DAM"));
		alumnos.add(new Alumno("Eduardo", "Moreno Algaba","2DAM"));
		alumnos.add(new Alumno("Oliver", "Rufian Baudet","1BACH"));
		alumnos.add(new Alumno("Alberto", "Soto Pascual","2BACH"));
		alumnos.add(new Alumno("Lucia", "Diaz Gutierrez","4ESO"));
		alumnos.add(new Alumno("Fernando", "Torres Arias","3ESO"));
		alumnos.add(new Alumno("Maria", "Gomez Perez","1ESO"));
		return alumnos;
	}
}
