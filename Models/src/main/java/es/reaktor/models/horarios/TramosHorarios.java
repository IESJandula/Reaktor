package es.reaktor.models.horarios;

public class TramosHorarios
{
	private Tramo[] tramo;
	private String totTr;

	public Tramo[] getTramo()
	{
		return tramo;
	}

	public void setTramo(Tramo[] value)
	{
		this.tramo = value;
	}

	public String getTotTr()
	{
		return totTr;
	}

	public void setTotTr(String value)
	{
		this.totTr = value;
	}
}
