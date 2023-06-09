package es.reaktor.models.DTO;

import es.reaktor.models.Id.SoundCardId;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoundCardDTO
{
    private String id;

    private String model;

    private String driver;

}
