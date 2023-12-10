package es.reaktor.models.horarios;

public class Asignaturas
{
	private Asignatura[] asignatura;
	private String totAs;

	public Asignatura[] getAsignatura()
	{
		return asignatura;
	}

	public void setAsignatura(Asignatura[] value)
	{
		this.asignatura = value;
	}

	public String getTotAs()
	{
		return totAs;
	}

	public void setTotAs(String value)
	{
		this.totAs = value;
	}
}