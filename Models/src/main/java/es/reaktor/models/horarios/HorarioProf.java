package es.reaktor.models.horarios;

public class HorarioProf
{
	private HorarioGrupActividad[] actividad;
	private String horNumIntPR;
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

	public String getHorNumIntPR()
	{
		return horNumIntPR;
	}

	public void setHorNumIntPR(String value)
	{
		this.horNumIntPR = value;
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
}
