package es.reaktor.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaktorDTO
{

    private MotherboardDTO motherboardDTO;

    private CpuDTO cpuDTO;

    private List<GraphicCardDTO> graphicCardDTO;

    private List<HardDiskDTO> hardDiskDTO;

    private List<NetworkCardDTO> networkCardDTO;

    private List<PartitionDTO> partitionDTO;

    private List<RamDTO> ramDTO;

    private List<SoundCardDTO> soundCardDTO;

    private List<MalwareDTO> malwareDTO;



}
