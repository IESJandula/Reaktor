package es.reaktor.models.horarios;

public class Datos
{
	private Asignaturas asignaturas;
	private Grupos grupos;
	private Aulas aulas;
	private Profesores profesores;
	private TramosHorarios tramosHorarios;

	public Asignaturas getAsignaturas()
	{
		return asignaturas;
	}

	public void setAsignaturas(Asignaturas value)
	{
		this.asignaturas = value;
	}

	public Grupos getGrupos()
	{
		return grupos;
	}

	public void setGrupos(Grupos value)
	{
		this.grupos = value;
	}

	public Aulas getAulas()
	{
		return aulas;
	}

	public void setAulas(Aulas value)
	{
		this.aulas = value;
	}

	public Profesores getProfesores()
	{
		return profesores;
	}

	public void setProfesores(Profesores value)
	{
		this.profesores = value;
	}

	public TramosHorarios getTramosHorarios()
	{
		return tramosHorarios;
	}

	public void setTramosHorarios(TramosHorarios value)
	{
		this.tramosHorarios = value;
	}
}