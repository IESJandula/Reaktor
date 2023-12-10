package es.reaktor.models.horarios;

public class HorarioAula
{
	private HorarioAulaActividad[] actividad;
	private String horNumIntAu;
	private String totUn;
	private String totAC;

	public HorarioAulaActividad[] getActividad()
	{
		return actividad;
	}

	public void setActividad(HorarioAulaActividad[] value)
	{
		this.actividad = value;
	}

	public String getHorNumIntAu()
	{
		return horNumIntAu;
	}

	public void setHorNumIntAu(String value)
	{
		this.horNumIntAu = value;
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