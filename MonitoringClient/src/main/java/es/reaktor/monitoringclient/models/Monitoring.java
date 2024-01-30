package es.reaktor.reaktorclient.models;

import es.reaktor.models.*;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.reaktor.reaktorclient.windows.*;

import java.util.List;


@Service
@Data
public class Reaktor
{

    @Autowired
    private WindowsRam windowsRam;

    @Autowired
    private WindowsMotherboard windowsMotherboard;

    @Autowired
    private WindowsGraphicCard windowsGraphicCard;

    @Autowired
    private WindowsSoundCard windowsSoundCard;

    @Autowired
    private WindowsHardDisk windowsHardDisk;

    @Autowired
    private WindowsCpu windowsCpu;

    @Autowired
    private WindowsNetworkCard windowsNetworkCard;

    @Autowired
    private WindowsPartitionHardDisk windowsPartitionHardDisk;

    @Autowired
    private WindowsMalware windowsMalware;


    /**
     * Attribute - Motherboard
     */
    private Motherboard motherboard;

    /**
     * Attribute - Malware
     */
    private List<Malware> malware;

    /**
     * Attribute - Cpu
     */
    private Cpu cpu;

    /**
     * Attribute - Graphic Card
     */
    private List<GraphicCard> graphicCard;

    /**
     * Attribute - Hard Disk
     */
    private List<HardDisk> hardDisk;

    /**
     * Attribute - Internet Connection
     */
    private InternetConnection internetConnection;

    /**
     * Attribute - Network Card
     */
    private List<NetworkCard> networkCard;

    /**
     * Attribute - partition
     */
    private List<Partition> partition;

    /**
     * Attribute - ram
     */
    private List<Ram> ram;

    /**
     * Attribute - Sound Card
     */
    private List<SoundCard> soundCard;

    /**
     * Constructor by default
     */
    public Reaktor()
    {

    }

    @PostConstruct
    public void createReaktorInformation()
    {
        // We get the information of the motherboard
        this.motherboard = this.windowsMotherboard.getMotherboard();

        if (!this.motherboard.getIsAdmin())
        {
            // We get the information of the malware
            this.malware = this.windowsMalware.getMalwareListInThisPc();
        }

        // We get the information of the sound card
        this.soundCard = this.windowsSoundCard.getSoundsCard(this.motherboard);

        // We get the information of the graphic card
        this.graphicCard = this.windowsGraphicCard.getGraphicCard(this.motherboard);

        // We get the information of the graphic card
        this.hardDisk = this.windowsHardDisk.getHardDisk(this.motherboard);

        // We get the information of the RAM
        this.ram = this.windowsRam.getRam(this.motherboard);

        // We get the information of the CPU
        this.cpu = this.windowsCpu.getCpu(this.motherboard);

        // We get the information of the network card
        this.networkCard = this.windowsNetworkCard.getNetworkCard(this.motherboard);

        // We get the information of the partition
        this.partition = this.windowsPartitionHardDisk.getPartitionHardDisk(this.hardDisk);

    }


    /**
     * Getter and setter
     */
    public Motherboard getMotherboard()
    {
        return motherboard;
    }

    public void setMotherboard(Motherboard motherboard)
    {
        this.motherboard = motherboard;
    }

    public List<Malware> getMalware()
    {
        return malware;
    }

    public void setMalware(List<Malware> malware)
    {
        this.malware = malware;
    }

    public Cpu getCpu()
    {
        return cpu;
    }

    public void setCpu(Cpu cpu)
    {
        this.cpu = cpu;
    }

    public List<GraphicCard> getGraphicCard()
    {
        return graphicCard;
    }

    public void setGraphicCard(List<GraphicCard> graphicCard)
    {
        this.graphicCard = graphicCard;
    }

    public List<HardDisk> getHardDisk()
    {
        return hardDisk;
    }

    public void setHardDisk(List<HardDisk> hardDisk)
    {
        this.hardDisk = hardDisk;
    }

    public InternetConnection getInternetConnection()
    {
        return internetConnection;
    }

    public void setInternetConnection(InternetConnection internetConnection)
    {
        this.internetConnection = internetConnection;
    }

    public List<NetworkCard> getNetworkCard()
    {
        return networkCard;
    }

    public void setNetworkCard(List<NetworkCard> networkCard)
    {
        this.networkCard = networkCard;
    }

    public List<Partition> getPartition()
    {
        return partition;
    }

    public void setPartition(List<Partition> partition)
    {
        this.partition = partition;
    }

    public List<Ram> getRam()
    {
        return ram;
    }

    public void setRam(List<Ram> ram)
    {
        this.ram = ram;
    }

    public List<SoundCard> getSoundCard()
    {
        return soundCard;
    }

    public void setSoundCard(List<SoundCard> soundCard)
    {
        this.soundCard = soundCard;
    }

    @Override
    public String toString()
    {
        return "Reaktor{" +
                "\n\nmotherboard=" + motherboard +
                "\n\n, malware=" + malware +
                "\n\n, cpu=" + cpu +
                "\n\n, graphicCard=" + graphicCard +
                "\n\n, hardDisk=" + hardDisk +
                "\n\n, internetConnection=" + internetConnection +
                "\n\n, networkCard=" + networkCard +
                "\n\n, partition=" + partition +
                "\n\n, ram=" + ram +
                "\n\n, soundCard=" + soundCard +
                '}';
    }
}