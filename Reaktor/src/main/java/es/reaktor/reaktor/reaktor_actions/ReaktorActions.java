package es.reaktor.reaktor.reaktor_actions;

import es.reaktor.models.*;
import es.reaktor.models.Id.MotherboardMalwareId;
import es.reaktor.reaktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ReaktorActions
{

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private GraphicCardRepository graphicCardRepository;

    @Autowired
    private HardDiskRepository hardDiskRepository;

    @Autowired
    private InternetConnectionNetworkCardRepository internetConnectionNetworkCardRepository;

    @Autowired
    private InternetConnectionRepository internetConnectionRepository;

    @Autowired
    private MalwareRepository malwareRepository;

    @Autowired
    private MotherboardMalwareRepository motherboardMalwareRepository;

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private NetworkCardRepository networkCardRepository;

    @Autowired
    private PartitionRepository partitionRepository;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private SoundCardRepository soundCardRepository;

    /**
     * This method is used to save the Pc information
     * @param reaktor the Pc information
     */
    public void saveReaktor(Reaktor reaktor)
    {

        if (reaktor.getMotherboard() != null)
        {
            this.saveMotherboard(reaktor.getMotherboard());
        }

        if (reaktor.getCpu() != null)
        {
            this.saveCpu(reaktor.getCpu());
        }

        if (reaktor.getGraphicCard() != null)
        {
            this.saveGraphicCard(reaktor.getGraphicCard());
        }

        if (reaktor.getRam() != null)
        {
            this.saveRam(reaktor.getRam());
        }

        if (reaktor.getSoundCard() != null)
        {
            this.saveSoundCard(reaktor.getSoundCard());
        }

        if (reaktor.getNetworkCard() != null)
        {
            this.saveNetworkCard(reaktor.getNetworkCard());
        }

        if (reaktor.getHardDisk() != null)
        {
            this.saveHardDisk(reaktor.getHardDisk());
        }

        if (reaktor.getPartition() != null)
        {
            this.savePartition(reaktor.getPartition());
        }

        if (reaktor.getInternetConnection() != null)
        {
            this.saveInternetConnection(reaktor.getInternetConnection());
        }

    }

    private void savePartition(List<Partition> partition)
    {
        this.partitionRepository.saveAllAndFlush(partition);
    }

    private void saveNetworkCard(List<NetworkCard> networkCard)
    {
        this.networkCardRepository.saveAllAndFlush(networkCard);
    }

    private void saveSoundCard(List<SoundCard> soundCard)
    {
        this.soundCardRepository.saveAllAndFlush(soundCard);
    }

    private void saveRam(List<Ram> ram)
    {
        this.ramRepository.saveAllAndFlush(ram);
    }

    private void saveInternetConnection(InternetConnection internetConnection)
    {
        this.internetConnectionRepository.saveAndFlush(internetConnection);
    }

    private void saveHardDisk(List<HardDisk> hardDisk)
    {
        this.hardDiskRepository.saveAllAndFlush(hardDisk);
    }

    private void saveGraphicCard(List<GraphicCard> graphicCard)
    {
        this.graphicCardRepository.saveAllAndFlush(graphicCard);
    }

    private void saveMotherboard(Motherboard motherboard)
    {
        this.motherboardRepository.saveAndFlush(motherboard);
    }

    private void saveCpu(Cpu cpu)
    {
        this.cpuRepository.saveAndFlush(cpu);
    }

    public void removeMalwareFromMotherboard(String motherBoardSerialNumber)
    {
        this.motherboardMalwareRepository.deleteByMotherboardMalwareId_SerialNumber(motherBoardSerialNumber);
    }

    @Transactional
    public void insertMalwareMotherboard(String motherBoardSerialNumber, List<Malware> malwareList)
    {
        this.removeMalwareFromMotherboard(motherBoardSerialNumber);
        Motherboard motherboard = this.motherboardRepository.findByMotherBoardSerialNumber(motherBoardSerialNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motherboard with serial number " + motherBoardSerialNumber + " does not exist")
        );

        for (Malware malware : malwareList)
        {
            this.motherboardMalwareRepository.saveAndFlush(new MotherboardMalware(new MotherboardMalwareId(malware.getName(), motherBoardSerialNumber), malware, motherboard));
        }
    }


}
