package es.iesjandula.horarios.constants;
import es.iesjandula.horarios.models.Alumno;
/**
 * @author Pablo Ruiz Canovas
 */
public final class Constantes 
{
	/**CARGADO DE ALUMNOS*/
	public static Alumno [] cargarAlumnos()
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
}
