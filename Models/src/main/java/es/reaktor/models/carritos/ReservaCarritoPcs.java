package es.reaktor.models.carritos;

import es.reaktor.models.carritos.Id.ReservaCarritoPcsId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Entity
@Table(name = "reserva_carrito_pc")
@NoArgsConstructor
@Component
public class ReservaCarritoPcs
{

    @EmbeddedId
    private ReservaCarritoPcsId reservaCarrtitoPcsId;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    @MapsId("idProfesor")
    private Profesor idProfesor;

    @ManyToOne
    @JoinColumn(name = "id_carrito_pcs")
    @MapsId("idCarritoPcs")
    private CarritoPc idCarritoPcs;

    @Column(length = 50)
    private String ubicacionPrestamo;

    public void setReservaCarrtitoPcsId(ReservaCarritoPcsId reservaCarrtitoPcsId)
    {
        this.reservaCarrtitoPcsId = reservaCarrtitoPcsId;
    }

    public void setIdProfesor(Profesor idProfesor)
    {
        this.idProfesor = idProfesor;
    }

    public void setIdCarritoPcs(CarritoPc idCarritoPcs)
    {
        this.idCarritoPcs = idCarritoPcs;
    }

    public void setUbicacionPrestamo(String ubicacionPrestamo)
    {
        this.ubicacionPrestamo = ubicacionPrestamo;
    }

}
