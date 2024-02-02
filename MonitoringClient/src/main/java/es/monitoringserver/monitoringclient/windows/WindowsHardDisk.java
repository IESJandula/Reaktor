package es.monitoringserver.monitoringclient.windows;

import org.springframework.stereotype.Service;

import es.monitoringserver.models.HardDisk;
import es.monitoringserver.models.Motherboard;
import es.monitoringserver.models.Id.HardDiskId;
import oshi.hardware.HWDiskStore;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;

@Service
public class WindowsHardDisk
{

    private WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;


    /**
     * - Constructor BY Default -
     * this constructor is used to initialize the class
     */
    public WindowsHardDisk()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    /**
     * - Method -
     * This method is used to get the graphic card of the computer and return of the list with my graphic card
     * @return List of my graphic card
     */
    public List<HardDisk> getHardDisk(Motherboard motherboard)
    {
        // We create the list of graphic cards
        List<HardDisk> hardDiskList = new ArrayList<>();

        for (int i = 0; i < windowsHardwareAbstractionLayer.getDiskStores().size(); i++)
        {
            HardDisk hardDisk = null;

            // We check if the size of the disk is not 0
            if (this.windowsHardwareAbstractionLayer.getDiskStores().get(i).getSize() != 0)
            {
                // We create my graphic card
                hardDisk = new HardDisk();

                // set the id of my graphic card
                hardDisk.setId(this.getIdHardDisk(this.windowsHardwareAbstractionLayer.getDiskStores().get(i), motherboard));

                // set the model of my graphic card
                hardDisk.setModel(this.getModelHardDisk(this.windowsHardwareAbstractionLayer.getDiskStores().get(i)));

                // set the model of my graphic card
                hardDisk.setSize(this.getSizeHardDisk(this.windowsHardwareAbstractionLayer.getDiskStores().get(i)));

                // We add my graphic card to the list
                hardDiskList.add(hardDisk);
            }


        }

        return hardDiskList;
    }

    private HardDiskId getIdHardDisk(HWDiskStore osFileStore, Motherboard motherboard)
    {
        HardDiskId hardDiskId = new HardDiskId(osFileStore.getName(), motherboard);
        return hardDiskId;
    }

    private Long getSizeHardDisk(HWDiskStore hardDisk)
    {
        return hardDisk.getSize()/1024/1024/1024;
    }

    private String getModelHardDisk(HWDiskStore hardDisk)
    {
        return hardDisk.getModel();
    }


}

