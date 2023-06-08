package es.reaktor.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Reaktor {

    protected Motherboard motherboard;

    protected List<Malware> malware;

    protected Cpu cpu;

    protected List<GraphicCard> graphicCard;

    protected List<HardDisk> hardDisk;

    private InternetConnection internetConnection;

    protected List<NetworkCard> networkCard;

    protected List<Partition> partition;

    protected List<Ram> ram;

    protected List<SoundCard> soundCard;

    public Reaktor()
    {

    }

    public Reaktor(Motherboard motherboard, List<Malware> malware, Cpu cpu, List<GraphicCard> graphicCard, List<HardDisk> hardDisk, List<NetworkCard> networkCard, List<Partition> partition, List<Ram> ram, List<SoundCard> soundCard)
    {
        this.motherboard = motherboard;
        this.malware = malware;
        this.cpu = cpu;
        this.graphicCard = graphicCard;
        this.hardDisk = hardDisk;
        this.networkCard = networkCard;
        this.partition = partition;
        this.ram = ram;
        this.soundCard = soundCard;
    }


}
