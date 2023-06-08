package es.reaktor.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CpuDTO
{
    private String id;

    private Integer cores;

    private Long frequency;

    private Integer threads;

}
