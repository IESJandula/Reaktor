package es.reaktor.models.horarios;

public class Profesores
{
	private Profesor[] profesor;
	private String totPR;

	public Profesor[] getProfesor()
	{
		return profesor;
	}

	public void setProfesor(Profesor[] value)
	{
		this.profesor = value;
	}

	public String getTotPR()
	{
		return totPR;
	}

	public void setTotPR(String value)
	{
		this.totPR = value;
	}
}