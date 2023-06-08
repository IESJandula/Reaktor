package es.reaktor.reaktor.reaktor_actions;

import es.reaktor.models.*;
import es.reaktor.models.DTO.*;
import es.reaktor.models.Id.MotherboardMalwareId;
import es.reaktor.reaktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReaktorService
{
    @Autowired
    private IMotherboardMalwareRepository iMotherboardMalwareRepository;

    @Autowired
    private IMotherboardRepository iMotherboardRepository;

    @Autowired
    private ICpuRepository iCpuRepository;

    @Autowired
    private IGraphicCardRepository iGraphicCardRepository;

    @Autowired
    private IHardDiskRepository iHardDiskRepository;

    @Autowired
    private INetworkCardRepository iNetworkCardRepository;

    @Autowired
    private IRamRepository iRamRepository;

    @Autowired
    private ISoundCardRepository iSoundCardRepository;

    @Autowired
    private IPartitionRepository iPartitionRepository;

    @Autowired
    private IMalwareRepository iMalwareRepository;



    public List<SimpleComputerDTO> getSimpleComputerDTO()
    {
        List<SimpleComputerDTO> simpleComputerDTOS = new ArrayList<>();

        List<Motherboard> motherboardList = this.iMotherboardRepository.findAll();

        for (Motherboard motherboard : motherboardList)
        {
            Long malwareCount = this.iMotherboardMalwareRepository.countByMotherboardMalwareId_SerialNumber(motherboard.getSerialNumber());

            simpleComputerDTOS.add(new SimpleComputerDTO(motherboard.getSerialNumber(), malwareCount, motherboard.getClassroom(), motherboard.getProfessor(), motherboard.getComputerOn(), motherboard.getIsAdmin()));
        }
        return simpleComputerDTOS;
    }

    public ReaktorDTO getInformationReaktor(String idComputer)
    {
        CpuDTO cpu = this.convertToCpuDTO(this.iCpuRepository.findCpuById_Motherboard_SerialNumber(idComputer));

        MotherboardDTO motherboard = this.convertToMotherboardDTO(this.iMotherboardRepository.findBySerialNumber(idComputer));

        List<GraphicCardDTO> graphicCardList = this.iGraphicCardRepository.findAll()
                .stream().filter(graphicCard -> Objects.equals(graphicCard.getId().getMotherboard().getSerialNumber(), idComputer))
                .map(this::convertToGraphicCardDTO).toList();

        List<HardDiskDTO> hardDiskList = this.iHardDiskRepository.findAll()
                .stream().filter(hardDisk -> Objects.equals(hardDisk.getId().getMotherboard().getSerialNumber(), idComputer))
                .map(this::convertToHardDiskDTO).toList();

        List<NetworkCardDTO> networkCardList = this.iNetworkCardRepository.findAll()
                .stream().filter(networkCard -> Objects.equals(networkCard.getId().getMotherboard().getSerialNumber(), idComputer))
                .map(this::convertToNetworkCardDTO).toList();

        List<RamDTO> ramList = this.iRamRepository.findAll()
                .stream().filter(ram -> Objects.equals(ram.getId().getMotherboard().getSerialNumber(), idComputer))
                .map(this::convertToRamDTO).toList();

        List<SoundCardDTO> soundCardList = this.iSoundCardRepository.findAll()
                .stream().filter(soundCard -> Objects.equals(soundCard.getId().getMotherboard().getSerialNumber(), idComputer))
                .map(this::convertToSoundCardDTO).toList();

        List<PartitionDTO> partitionList = this.iPartitionRepository.findAll()
                .stream().filter(partition -> Objects.equals(partition.getId().getHardDisk().getId().getMotherboard().getSerialNumber(), idComputer))
                .map(this::convertToPartitionDTO).toList();

        List<MalwareDTO> malwareList = this.iMotherboardMalwareRepository.findAll()
                .stream().filter(motherboardMalware -> Objects.equals(motherboardMalware.getSerialNumber().getSerialNumber(), idComputer))
                .map(MotherboardMalware::getName)
                .map(this::convertToMalwareDTO).toList();

        return new ReaktorDTO(motherboard,cpu,graphicCardList,hardDiskList,networkCardList,partitionList,ramList,soundCardList,malwareList);
    }

    private MalwareDTO convertToMalwareDTO(Malware malware)
    {
        return new MalwareDTO(malware.getName(),malware.getDescription());
    }


    private PartitionDTO convertToPartitionDTO(Partition partition)
    {
        return new PartitionDTO(partition.getId().getIdPartition(),partition.getSize(),partition.getLetter(),partition.getOperatingSystem());
    }


    private SoundCardDTO convertToSoundCardDTO(SoundCard soundCard)
    {
        return new SoundCardDTO(soundCard.getId().getIdSoundCard(),soundCard.getModel(),soundCard.getDriver());
    }

    private RamDTO convertToRamDTO(Ram ram)
    {
        return new RamDTO(ram.getId().getSerialNumberRam(),ram.getSize(),ram.getOccupiedSlots(),ram.getModel(),ram.getType(),ram.getSize());
    }

    private NetworkCardDTO convertToNetworkCardDTO(NetworkCard networkCard)
    {
        return new NetworkCardDTO(networkCard.getId().getIdNetworkCard(),networkCard.getMacAddress(),networkCard.getRj45IsConnected(),networkCard.getModel(),networkCard.getIsWireless());
    }

    private HardDiskDTO convertToHardDiskDTO(HardDisk hardDisk)
    {
        return new HardDiskDTO(hardDisk.getId().getSerialNumberHardDisk(),hardDisk.getSize(),hardDisk.getModel());
    }

    private GraphicCardDTO convertToGraphicCardDTO(GraphicCard graphicCard)
    {
        return new GraphicCardDTO(graphicCard.getId().getIdGraphicCard(), graphicCard.getModel());
    }

    private MotherboardDTO convertToMotherboardDTO(Motherboard motherboard)
    {
       return new MotherboardDTO(motherboard.getSerialNumber(),motherboard.getModel(),motherboard.getClassroom(),motherboard.getDescription(),motherboard.getProfessor(),motherboard.getLastConnection(),motherboard.getLastUpdateComputerOn(),motherboard.getComputerOn());
    }

    private CpuDTO convertToCpuDTO(Cpu cpu)
    {
        return new CpuDTO(cpu.getId().getIdCpu(), cpu.getCores(), cpu.getFrequency(), cpu.getThreads());
    }


    public void deleteMalware(String idMalware)
    {
        Malware malwareRemove = this.iMalwareRepository.findById(idMalware).orElseThrow(
                () -> new IllegalArgumentException("Invalid malware Id:" + idMalware)
        );

        List<String> motherboardMalwareIdList = this.iMotherboardMalwareRepository.findIdMotherboardOfMalware(idMalware);

        for (String id : motherboardMalwareIdList)
        {
            Motherboard motherboard = this.iMotherboardRepository.findBySerialNumber(id);
            motherboard.getMalware().remove(new MotherboardMalware(new MotherboardMalwareId(idMalware, id), malwareRemove, motherboard));
        }

        this.iMalwareRepository.delete(malwareRemove);
    }


    public List<MalwareDTOWeb> getMalwareWeb()
    {

        List<Object[]> malwareWeb = this.iMalwareRepository.malwareWeb();

        List<MalwareDTOWeb> malwareDTOWebList = new ArrayList<>();

        for (Object malware : malwareWeb)
        {
            MalwareDTOWeb malwareDTOWeb = new MalwareDTOWeb();
            malwareDTOWeb.setName((String) ((Object[]) malware)[0]);
            malwareDTOWeb.setDescription((String) ((Object[]) malware)[1]);
            malwareDTOWeb.setNumOccurrences((Long) ((Object[]) malware)[2]);
            malwareDTOWebList.add(malwareDTOWeb);
        }

        return malwareDTOWebList;
    }
}
