package es.reaktor.models.horarios;

public class Centro
{
	private Datos datos;
	private Horarios horarios;
	private String nombreCentro;
	private String autor;
	private String fecha;

	public Datos getDatos()
	{
		return datos;
	}

	public void setDatos(Datos value)
	{
		this.datos = value;
	}

	public Horarios getHorarios()
	{
		return horarios;
	}

	public void setHorarios(Horarios value)
	{
		this.horarios = value;
	}

	public String getNombreCentro()
	{
		return nombreCentro;
	}

	public void setNombreCentro(String value)
	{
		this.nombreCentro = value;
	}

	public String getAutor()
	{
		return autor;
	}

	public void setAutor(String value)
	{
		this.autor = value;
	}

	public String getFecha()
	{
		return fecha;
	}

	public void setFecha(String value)
	{
		this.fecha = value;
	}
}
