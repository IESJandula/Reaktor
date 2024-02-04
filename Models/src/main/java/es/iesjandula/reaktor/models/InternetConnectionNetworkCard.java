package es.iesjandula.reaktor.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import es.iesjandula.reaktor.models.Id.InternetConnectionNetworkCardId;

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

    /** Attribute id*/
    @EmbeddedId
    private InternetConnectionNetworkCardId id;

    /** Attribute ip_address*/
    @Column(name = "ip_address")
    private String ip_address;

    /** Attribute last_connection*/
    @Column(name = "last_connection")
    private Date last_connection;

}
