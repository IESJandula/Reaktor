package es.reaktor.models.horarios;

public class HorarioGrup
{
	private HorarioGrupActividad[] actividad;
	private String horNumIntGr;
	private String totUn;
	private String totAC;

	public HorarioGrupActividad[] getActividad()
	{
		return actividad;
	}

	public void setActividad(HorarioGrupActividad[] value)
	{
		this.actividad = value;
	}

	public String getHorNumIntGr()
	{
		return horNumIntGr;
	}

	public void setHorNumIntGr(String value)
	{
		this.horNumIntGr = value;
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