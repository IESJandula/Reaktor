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
public class ReservaCarritoPcsId implements Serializable
{
    @Serial
    private static final long serialVersionUID = 7608046726625324010L;

    private Long idProfesor;

    private Long idCarritoPcs;

    private Date fecha;

    public void setIdProfesor(Long idProfesor)
    {
        this.idProfesor = idProfesor;
    }

    public void setIdCarritoPcs(Long idCarritoPcs)
    {
        this.idCarritoPcs = idCarritoPcs;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }
}
