package es.reaktor.models;

import es.reaktor.models.Id.InternetConnectionNetworkCardId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Entity
@Table(name = "internet_connection_network_card")
@Getter
@Setter
public class InternetConnectionNetworkCard
{

    @EmbeddedId
    private InternetConnectionNetworkCardId id;

    @Column(name = "ip_address")
    private String ip_address;

    @Column(name = "last_connection")
    private Date last_connection;

}
