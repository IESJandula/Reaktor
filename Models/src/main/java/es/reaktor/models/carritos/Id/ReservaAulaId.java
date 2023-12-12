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
public class ReservaAulaId implements Serializable
{
    @Serial
    private static final long serialVersionUID = 2380276541647676998L;

    private Long idProfesor;

    private Long idAula;

    private Date fecha;

    public void setIdProfesor(Long idProfesor)
    {
        this.idProfesor = idProfesor;
    }

    public void setIdAula(Long idAula)
    {
        this.idAula = idAula;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

}
