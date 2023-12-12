package es.reaktor.models.carritos.Id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReservaCarritoTabletsId implements Serializable
{
    @Serial
    private static final long serialVersionUID = -5697279251313847888L;

    private Long idProfesor;

    private Long idCarritoTablets;

    private Date fecha;

    public void setIdProfesor(Long idProfesor)
    {
        this.idProfesor = idProfesor;
    }

    public void setIdCarritoTablets(Long idCarritoTablets)
    {
        this.idCarritoTablets = idCarritoTablets;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }
}
