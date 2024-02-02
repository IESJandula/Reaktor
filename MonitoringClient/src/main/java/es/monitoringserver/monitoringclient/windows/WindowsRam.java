package es.monitoringserver.monitoringclient.windows;

import es.monitoringserver.models.Motherboard;
import es.monitoringserver.models.Ram;
import es.monitoringserver.models.Id.RamId;
import es.monitoringserver.monitoringclient.utils.CommandExecutor;
import es.monitoringserver.monitoringclient.utils.Constants;
import es.monitoringserver.monitoringclient.utils.StringsUtils;
import es.monitoringserver.monitoringclient.utils.exceptions.ConstantsErrors;
import es.monitoringserver.monitoringclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * - Class -
 * This class is used to get the information of the ram of the computer
 */
@Service
@Slf4j
public class WindowsRam
{

    /**
     * - Attribute -
     * this class is used to execute the commands of the computer
     */
    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private StringsUtils stringsUtils;

    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;

    public WindowsRam()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    public List<Ram> getRam(Motherboard motherboard)
    {

        List<Ram> ramList = new ArrayList<>();
        int i = 0;

        for (PhysicalMemory ramForOshi : this.windowsHardwareAbstractionLayer.getMemory().getPhysicalMemory())
        {
            Ram ram = new Ram();
            ram.setId(this.getIdRam(motherboard, i));
            ram.setOccupiedSlots(this.getOccupiedSlots(ramForOshi));
            ram.setModel(this.getModel(ramForOshi));
            ram.setSize(this.getRamTotal(ramForOshi));
            ram.setSpeed(this.getSpeed(ramForOshi));
            ram.setType(this.getType(ramForOshi));
            ramList.add(ram);
            i++;
        }
        return ramList;

    }

    private RamId getIdRam(Motherboard motherboard, int index)
    {
        // We create the id of my ram
        String idRam;

        try
        {
            // We get the id of my ram
            idRam = this.commandExecutor.executeCommand(CommandConstantsWindows.GET_ID_RAM_WINDOWS_BANK).replace(Constants.OUTPUT_RAM_ID_CONSOLE, Constants.EMPTY_STRING).trim();
            idRam = this.stringsUtils.getLine(idRam.replaceAll("\n\n", "\n").trim(), index);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            // We set the id of my ram to unknown in case of exception
            idRam = Constants.UNKNOWN + " " + UUID.randomUUID();
            log.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_MODEL_MOTHERBOARD, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        // We create the id of my graphic card
        RamId ramId = new RamId(idRam, motherboard);

        return ramId;
    }



    private String getType(PhysicalMemory ramForOshi)
    {
        return ramForOshi.getMemoryType();
    }

    private Long getSpeed(PhysicalMemory ramForOshi)
    {
        return ramForOshi.getClockSpeed()/1000/1000;
    }

    private String getModel(PhysicalMemory ramForOshi)
    {
        return ramForOshi.getManufacturer();
    }

    private String getOccupiedSlots(PhysicalMemory ramForOshi)
    {
        return ramForOshi.getBankLabel();
    }

    public long getRamTotal(PhysicalMemory ramForOshi)
    {
        return ramForOshi.getCapacity()/1024/1024/1024;
    }



}