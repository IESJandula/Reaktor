package es.monitoringserver.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaktorDTO
{

    /** Attribute motherboardDTO*/
    private MotherboardDTO motherboardDTO;

    /** Attribute cpuDTO*/
    private CpuDTO cpuDTO;

    /** Attribute graphicCardDTO*/
    private List<GraphicCardDTO> graphicCardDTO;

    /** Attribute hardDiskDTO*/
    private List<HardDiskDTO> hardDiskDTO;

    /** Attribute networkCardDTO*/
    private List<NetworkCardDTO> networkCardDTO;

    /** Attribute partitionDTO*/
    private List<PartitionDTO> partitionDTO;

    /** Attribute ramDTO*/
    private List<RamDTO> ramDTO;

    /** Attribute soundCardDTO*/
    private List<SoundCardDTO> soundCardDTO;

    /** Attribute malwareDTO*/
    private List<MalwareDTO> malwareDTO;



}
