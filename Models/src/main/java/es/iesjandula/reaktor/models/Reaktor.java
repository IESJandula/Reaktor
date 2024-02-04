package es.iesjandula.reaktor.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Reaktor {

    /** Attribute motherboard*/
    protected Motherboard motherboard;

    /** Attribute malware*/
    protected List<Malware> malware;

    /** Attribute cpu*/
    protected Cpu cpu;

    /** Attribute graphicCard*/
    protected List<GraphicCard> graphicCard;

    /** Attribute hardDisk*/
    protected List<HardDisk> hardDisk;

    /** Attribute internetConnection*/
    private InternetConnection internetConnection;

    /** Attribute networkCard*/
    protected List<NetworkCard> networkCard;

    /** Attribute partition*/
    protected List<Partition> partition;

    /** Attribute ram*/
    protected List<Ram> ram;

    /** Attribute soundCard*/
    protected List<SoundCard> soundCard;

    /**
     * Constructor for create new Reaktor
     */
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
