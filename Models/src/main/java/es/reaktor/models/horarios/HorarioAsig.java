package es.reaktor.models.horarios;

public class HorarioAsig
{
	private ACTIVIDADUnion actividad;
	private String horNumIntAs;
	private String totUn;
	private String totAC;

	public ACTIVIDADUnion getActividad()
	{
		return actividad;
	}

	public void setActividad(ACTIVIDADUnion value)
	{
		this.actividad = value;
	}

	public String getHorNumIntAs()
	{
		return horNumIntAs;
	}

	public void setHorNumIntAs(String value)
	{
		this.horNumIntAs = value;
	}

	public String getTotUn()
	{
		return totUn;
	}

	public void setTotUn(String value)
	{
		this.totUn = value;
	}

	public String getTotAC()
	{
		return totAC;
	}

	public void setTotAC(String value)
	{
		this.totAC = value;
	}
}