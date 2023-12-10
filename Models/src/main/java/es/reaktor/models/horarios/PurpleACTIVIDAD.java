package es.reaktor.models.horarios;

public class PurpleACTIVIDAD
{
	private GruposActividad gruposActividad;
	private String numAct;
	private String numUn;
	private String tramo;
	private String aula;
	private String profesor;

	public GruposActividad getGruposActividad()
	{
		return gruposActividad;
	}

	public void setGruposActividad(GruposActividad value)
	{
		this.gruposActividad = value;
	}

	public String getNumAct()
	{
		return numAct;
	}

	public void setNumAct(String value)
	{
		this.numAct = value;
	}

	public String getNumUn()
	{
		return numUn;
	}

	public void setNumUn(String value)
	{
		this.numUn = value;
	}

	public String getTramo()
	{
		return tramo;
	}

	public void setTramo(String value)
	{
		this.tramo = value;
	}

	public String getAula()
	{
		return aula;
	}

	public void setAula(String value)
	{
		this.aula = value;
	}

	public String getProfesor()
	{
		return profesor;
	}

	public void setProfesor(String value)
	{
		this.profesor = value;
	}
}