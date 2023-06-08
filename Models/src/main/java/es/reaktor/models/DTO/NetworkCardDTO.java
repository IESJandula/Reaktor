package es.reaktor.models.DTO;

import es.reaktor.models.Id.NetworkCardId;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkCardDTO
{
    private String id;

    private String macAddress;

    private Boolean rj45IsConnected;

    private String model;

    private Boolean isWireless;
}
