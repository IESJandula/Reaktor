package es.iesjandula.horarios.constants;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import es.iesjandula.horarios.models.Puntos;
/**
 * @author Pablo Ruiz Canovas
 */
public final class Constantes 
{
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
	
	/**CARGADO DE PUNTOS */
	public static final List<Puntos> cargarPuntos()
	{
		List<Puntos> puntos = new LinkedList<Puntos>();
		puntos.add(new Puntos(-5,"Interrumpir la clase"));
		puntos.add(new Puntos(-5,"No mantener silecio cuando es necesario"));
		puntos.add(new Puntos(-5,"Mal comportamiento"));
		puntos.add(new Puntos(-5,"Correr por los pasillos"));
		puntos.add(new Puntos(-10,"Comer en clase"));
		puntos.add(new Puntos(-10,"Estar por los pasillos en horario de clase sin autorizacion"));
		puntos.add(new Puntos(-10,"Crear un mal ambiente entre compañeros"));
		puntos.add(new Puntos(-10,"Insultar, menospreciar a un compañero"));
		puntos.add(new Puntos(-20,"Insultar, menospreciar a un profesor"));
		puntos.add(new Puntos(-25,"Pelearse con otro estudiante"));
		puntos.add(new Puntos(-25,"Incitacion al odio"));
		puntos.add(new Puntos(-35,"Copiarse en un examen"));
		puntos.add(new Puntos(-45,"Uso del movil en clase"));
		puntos.add(new Puntos(-55,"Acoso o Bullying a otro estudiante"));
		puntos.add(new Puntos(-55,"Comportamiento homofobico"));
		puntos.add(new Puntos(-75,"Agredir a un profesor"));
		puntos.add(new Puntos(0,"No realiza la tarea en el aula de reflexion"));
		puntos.add(new Puntos(1,"Buen comportamiento en el aula de reflexion"));
		puntos.add(new Puntos(5,"Buen comportamiento"));
		puntos.add(new Puntos(5,"Ayuda en clase y muestra una buena actitud"));
		puntos.add(new Puntos(10,"Ayuda a mejorar la convivencia en el centro"));
		return puntos;
	}
	
	/**CARGADO DE HORAS */
	public static final List<String> cargarHorasInicio()
	{
		List<String> horas = new LinkedList<>();
		horas.add(" 8:15/ 9:15");
		horas.add(" 9:15/10:15");
		horas.add("10:15/11:15");
		horas.add("11:15/11:45");
		horas.add("11:45/12:45");
		horas.add("12:45/13:45");
		horas.add("13:45/14:45");
		return horas;
	}
}
