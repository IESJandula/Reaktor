package es.reaktor.models.carritos;

import es.reaktor.models.carritos.Id.ReservaCarritoTabletsId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Entity
@Table(name = "reserva_carrito_tablets")
@NoArgsConstructor
@Component
public class ReservaCarritoTablets
{

    @EmbeddedId
    private ReservaCarritoTabletsId reservaCarritoTabletsId;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    @MapsId("idProfesor")
    private Profesor idProfesor;

    @ManyToOne
    @JoinColumn(name = "id_carrito_tablets")
    @MapsId("idCarritoTablets")
    private CarritoTablets idCarritoTablets;

    @Column
    private String ubicacionPrestamo;

    public void setReservaCarritoTabletsId(ReservaCarritoTabletsId reservaCarritoTabletsId)
    {
        this.reservaCarritoTabletsId = reservaCarritoTabletsId;
    }

    public void setIdProfesor(Profesor idProfesor)
    {
        this.idProfesor = idProfesor;
    }

    public void setIdCarritoTablets(CarritoTablets idCarritoTablets)
    {
        this.idCarritoTablets = idCarritoTablets;
    }

    public void setUbicacionPrestamo(String ubicacionPrestamo)
    {
        this.ubicacionPrestamo = ubicacionPrestamo;
    }

}
