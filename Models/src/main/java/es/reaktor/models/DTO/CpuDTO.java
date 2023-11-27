package es.reaktor.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CpuDTO
{
    /** Attribute id*/
    private String id;

    /** Attribute cores*/
    private Integer cores;

    /** Attribute frequency*/
    private Long frequency;

    /** Attribute threads*/
    private Integer threads;

}
