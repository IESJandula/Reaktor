package es.reaktor.models.horarios;

public class Horarios
{
	private HorariosAsignaturas horariosAsignaturas;
	private HorariosGrupos horariosGrupos;
	private HorariosAulas horariosAulas;
	private HorariosProfesores horariosProfesores;

	public HorariosAsignaturas getHorariosAsignaturas()
	{
		return horariosAsignaturas;
	}

	public void setHorariosAsignaturas(HorariosAsignaturas value)
	{
		this.horariosAsignaturas = value;
	}

	public HorariosGrupos getHorariosGrupos()
	{
		return horariosGrupos;
	}

	public void setHorariosGrupos(HorariosGrupos value)
	{
		this.horariosGrupos = value;
	}

	public HorariosAulas getHorariosAulas()
	{
		return horariosAulas;
	}

	public void setHorariosAulas(HorariosAulas value)
	{
		this.horariosAulas = value;
	}

	public HorariosProfesores getHorariosProfesores()
	{
		return horariosProfesores;
	}

	public void setHorariosProfesores(HorariosProfesores value)
	{
		this.horariosProfesores = value;
	}
}