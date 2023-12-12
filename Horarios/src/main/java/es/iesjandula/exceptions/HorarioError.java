package es.iesjandula.exceptions;

public class HorarioError extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4994392585697881401L;
	private int codigo;
    private String mensaje;

    public HorarioError(int codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getMensaje() {
        return mensaje;
    }

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
}
