package es.reaktor.reaktorclient.windows;

import es.reaktor.models.Cpu;
import es.reaktor.models.Id.CpuId;
import es.reaktor.models.Motherboard;
import org.springframework.stereotype.Service;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

@Service
public class WindowsCpu
{


    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;


    public WindowsCpu()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    public Cpu getCpu(Motherboard motherboard)
    {
        Cpu cpu = new Cpu();
        cpu.setCores(this.getCores());
        cpu.setFrequency(this.getFrequency());
        cpu.setThreads(this.getThreads());
        cpu.setId(this.getCpuId(motherboard));
        return cpu;
    }

    private CpuId getCpuId(Motherboard motherboard)
    {
        return new CpuId(this.windowsHardwareAbstractionLayer.getProcessor().getProcessorIdentifier().getProcessorID(), motherboard);
    }

    private Integer getThreads()
    {
        return this.windowsHardwareAbstractionLayer.getProcessor().getLogicalProcessorCount();
    }

    private long getFrequency()
    {

        return this.windowsHardwareAbstractionLayer.getProcessor().getProcessorIdentifier().getVendorFreq()/1000/1000;
    }

    private Integer getCores()
    {
        return this.windowsHardwareAbstractionLayer.getProcessor().getPhysicalProcessorCount();
    }

}
