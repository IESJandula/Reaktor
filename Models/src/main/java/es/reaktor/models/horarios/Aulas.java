package es.reaktor.models.horarios;

public class Aulas
{
	private Aula[] aula;
	private String totAu;

	public Aula[] getAula()
	{
		return aula;
	}

	public void setAula(Aula[] value)
	{
		this.aula = value;
	}

	public String getTotAu()
	{
		return totAu;
	}

	public void setTotAu(String value)
	{
		this.totAu = value;
	}
}