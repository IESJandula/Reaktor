package es.reaktor.reaktor.reaktor_actions;

import es.reaktor.models.*;
import es.reaktor.models.DTO.*;
import es.reaktor.models.Id.MotherboardMalwareId;
import es.reaktor.reaktor.repository.*;
import es.reaktor.reaktor.services.MotherboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReaktorService
{
    /**
     * Attribute iMotherboardMalwareRepository
     */
    @Autowired
    private MotherboardMalwareRepository motherboardMalwareRepository;

    /**
     * Attribute iMotherboardRepository
     */
    @Autowired
    private MotherboardRepository motherboardRepository;

    /**
     * Attribute iCpuRepository
     */
    @Autowired
    private CpuRepository cpuRepository;

    /**
     * Attribute iGraphicCardRepository
     */
    @Autowired
    private GraphicCardRepository graphicCardRepository;

    /**
     * Attribute iHardDiskRepository
     */
    @Autowired
    private HardDiskRepository hardDiskRepository;

    /**
     * Attribute iNetworkCardRepository
     */
    @Autowired
    private NetworkCardRepository networkCardRepository;

    /**
     * Attribute iRamRepository
     */
    @Autowired
    private RamRepository ramRepository;

    /**
     * Attribute iSoundCardRepository
     */
    @Autowired
    private SoundCardRepository soundCardRepository;

    /**
     * Attribute iPartitionRepository
     */
    @Autowired
    private PartitionRepository partitionRepository;

    /**
     * Attribute iMalwareRepository
     */
    @Autowired
    private MalwareRepository malwareRepository;

    @Autowired
    private MotherboardService motherboardService;


    /**
     * Method getSimpleComputerDTO
     *
     * @return
     */
    public SimpleComputerDTO getSimpleComputerDTO(String computerId)
    {

        Motherboard motherboard = this.motherboardRepository.findById(computerId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Motherboard with serial number " + computerId + " does not exist"
                ));

        Long malwareCount = this.motherboardMalwareRepository.countByMotherboardMalwareId_MotherBoardSerialNumber(motherboard.getMotherBoardSerialNumber());

        return new SimpleComputerDTO(motherboard.getMotherBoardSerialNumber(), malwareCount, motherboard.getClassroom(), motherboard.getTeacher(), motherboard.getComputerOn(), motherboard.getIsAdmin(), motherboard.getComputerNumber());
    }


    public List<String> getAllIds()
    {
        return this.motherboardService.getAllMotherboardIds();
    }


    /**
     * Method getInformationReaktor
     *
     * @param idComputer
     * @return
     */
    public ReaktorDTO getInformationReaktor(String idComputer)
    {
        CpuDTO cpu = this.convertToCpuDTO(this.cpuRepository.findCpuById_Motherboard_MotherBoardSerialNumber(idComputer));

        MotherboardDTO motherboard = this.convertToMotherboardDTO(this.motherboardRepository.findByMotherBoardSerialNumber(idComputer));

        List<GraphicCardDTO> graphicCardList = this.graphicCardRepository.findAll().stream().filter(graphicCard -> Objects.equals(graphicCard.getId().getMotherboard().getMotherBoardSerialNumber(), idComputer)).map(this::convertToGraphicCardDTO).toList();

        List<HardDiskDTO> hardDiskList = this.hardDiskRepository.findAll().stream().filter(hardDisk -> Objects.equals(hardDisk.getId().getMotherboard().getMotherBoardSerialNumber(), idComputer)).map(this::convertToHardDiskDTO).toList();

        List<NetworkCardDTO> networkCardList = this.networkCardRepository.findAll().stream().filter(networkCard -> Objects.equals(networkCard.getId().getMotherboard().getMotherBoardSerialNumber(), idComputer)).map(this::convertToNetworkCardDTO).toList();

        List<RamDTO> ramList = this.ramRepository.findAll().stream().filter(ram -> Objects.equals(ram.getId().getMotherboard().getMotherBoardSerialNumber(), idComputer)).map(this::convertToRamDTO).toList();

        List<SoundCardDTO> soundCardList = this.soundCardRepository.findAll().stream().filter(soundCard -> Objects.equals(soundCard.getId().getMotherboard().getMotherBoardSerialNumber(), idComputer)).map(this::convertToSoundCardDTO).toList();

        List<PartitionDTO> partitionList = this.partitionRepository.findAll().stream().filter(partition -> Objects.equals(partition.getId().getHardDisk().getId().getMotherboard().getMotherBoardSerialNumber(), idComputer)).map(this::convertToPartitionDTO).toList();

        List<MalwareDTO> malwareList = this.motherboardMalwareRepository.findAll().stream().filter(motherboardMalware -> Objects.equals(motherboardMalware.getMotherBoardSerialNumber().getMotherBoardSerialNumber(), idComputer)).map(MotherboardMalware::getName).map(this::convertToMalwareDTO).toList();

        return new ReaktorDTO(motherboard, cpu, graphicCardList, hardDiskList, networkCardList, partitionList, ramList, soundCardList, malwareList);
    }

    /**
     * Method convertToMalwareDTO
     *
     * @param malware
     * @return
     */
    private MalwareDTO convertToMalwareDTO(Malware malware)
    {
        return new MalwareDTO(malware.getName(), malware.getDescription());
    }


    /**
     * Method convertToPartitionDTO
     *
     * @param partition
     * @return
     */
    private PartitionDTO convertToPartitionDTO(Partition partition)
    {
        return new PartitionDTO(partition.getId().getIdPartition(), partition.getSize(), partition.getLetter(), partition.getOperatingSystem());
    }


    /**
     * Method convertToSoundCardDTO
     *
     * @param soundCard
     * @return
     */
    private SoundCardDTO convertToSoundCardDTO(SoundCard soundCard)
    {
        return new SoundCardDTO(soundCard.getId().getIdSoundCard(), soundCard.getModel(), soundCard.getDriver());
    }

    /**
     * Method convertToRamDTO
     *
     * @param ram
     * @return
     */
    private RamDTO convertToRamDTO(Ram ram)
    {
        return new RamDTO(ram.getId().getSerialNumberRam(), ram.getSize(), ram.getOccupiedSlots(), ram.getModel(), ram.getType(), ram.getSize());
    }

    /**
     * Method convertToNetworkCardDTO
     *
     * @param networkCard
     * @return
     */
    private NetworkCardDTO convertToNetworkCardDTO(NetworkCard networkCard)
    {
        return new NetworkCardDTO(networkCard.getId().getIdNetworkCard(), networkCard.getMacAddress(), networkCard.getRj45IsConnected(), networkCard.getModel(), networkCard.getIsWireless());
    }

    /**
     * Method convertToHardDiskDTO
     *
     * @param hardDisk
     * @return
     */
    private HardDiskDTO convertToHardDiskDTO(HardDisk hardDisk)
    {
        return new HardDiskDTO(hardDisk.getId().getSerialNumberHardDisk(), hardDisk.getSize(), hardDisk.getModel());
    }

    /**
     * Method convertToGraphicCardDTO
     *
     * @param graphicCard
     * @return
     */
    private GraphicCardDTO convertToGraphicCardDTO(GraphicCard graphicCard)
    {
        return new GraphicCardDTO(graphicCard.getId().getIdGraphicCard(), graphicCard.getModel());
    }

    /**
     * Method convertToMotherboardDTO
     *
     * @param motherboard
     * @return
     */
    private MotherboardDTO convertToMotherboardDTO(Motherboard motherboard)
    {
        return new MotherboardDTO(motherboard.getMotherBoardSerialNumber(), motherboard.getModel(), motherboard.getClassroom(), motherboard.getTrolley(), motherboard.getAndaluciaId(), motherboard.getComputerNumber(), motherboard.getTeacher(), motherboard.getComputerSerialNumber(), motherboard.getLastConnection(), motherboard.getLastUpdateComputerOn(), motherboard.getComputerOn());
    }

    /**
     * Method convertToCpuDTO
     *
     * @param cpu
     * @return
     */
    private CpuDTO convertToCpuDTO(Cpu cpu)
    {
        return new CpuDTO(cpu.getId().getIdCpu(), cpu.getCores(), cpu.getFrequency(), cpu.getThreads());
    }


    /**
     * Method deleteMalware
     *
     * @param idMalware
     */
    public void deleteMalware(String idMalware)
    {
        Malware malwareRemove = this.malwareRepository.findById(idMalware).orElseThrow(() -> new IllegalArgumentException("Invalid malware Id:" + idMalware));

        List<String> motherboardMalwareIdList = this.motherboardMalwareRepository.findIdMotherboardOfMalware(idMalware);

        for (String id : motherboardMalwareIdList)
        {
            Motherboard motherboard = this.motherboardRepository.findByMotherBoardSerialNumber(id);
            motherboard.getMalware().remove(new MotherboardMalware(new MotherboardMalwareId(idMalware, id), malwareRemove, motherboard));
        }

        this.malwareRepository.delete(malwareRemove);
    }


    /**
     * Method getMalwareWeb
     *
     * @return
     */
    public List<MalwareDTOWeb> getMalwareWeb()
    {

        List<Object[]> malwareWeb = this.malwareRepository.malwareWeb();

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
