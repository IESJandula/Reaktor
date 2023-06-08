package es.reaktor.models;

import es.reaktor.models.Id.NetworkCardId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Neil Hdez
 * @version 1.0.0
 * @since 04/02/2023
 */
@Entity
@Table(name = "network_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetworkCard
{

    @EmbeddedId
    private NetworkCardId id;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "rj45_is_connected")
    private Boolean rj45IsConnected;

    @Column(name = "model")
    private String model;

    @Column(name = "is_wireless")
    private Boolean isWireless;

}
