package es.reaktor.models.horarios;

public class Tramo
{
	private String numTr;
	private String numeroDia;
	private String horaInicio;
	private String horaFinal;

	public String getNumTr()
	{
		return numTr;
	}

	public void setNumTr(String value)
	{
		this.numTr = value;
	}

	public String getNumeroDia()
	{
		return numeroDia;
	}

	public void setNumeroDia(String value)
	{
		this.numeroDia = value;
	}

	public String getHoraInicio()
	{
		return horaInicio;
	}

	public void setHoraInicio(String value)
	{
		this.horaInicio = value;
	}

	public String getHoraFinal()
	{
		return horaFinal;
	}

	public void setHoraFinal(String value)
	{
		this.horaFinal = value;
	}
}