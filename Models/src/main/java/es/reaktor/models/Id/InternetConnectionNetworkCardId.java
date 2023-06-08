package es.reaktor.models.Id;

import es.reaktor.models.InternetConnection;
import es.reaktor.models.NetworkCard;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Embeddable
@Getter
@Setter
public class InternetConnectionNetworkCardId implements Serializable
{

    @Serial
    private static final long serialVersionUID = 6657317898848841410L;

    @ManyToOne
    private NetworkCard idNetworkCard;

    @ManyToOne
    private InternetConnection idInternetConnection;

    public InternetConnectionNetworkCardId()
    {

    }
}
