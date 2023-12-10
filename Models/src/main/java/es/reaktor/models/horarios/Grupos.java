package es.reaktor.models.horarios;

public class Grupos
{
	private Grupo[] grupo;
	private String totGr;

	public Grupo[] getGrupo()
	{
		return grupo;
	}

	public void setGrupo(Grupo[] value)
	{
		this.grupo = value;
	}

	public String getTotGr()
	{
		return totGr;
	}

	public void setTotGr(String value)
	{
		this.totGr = value;
	}
}