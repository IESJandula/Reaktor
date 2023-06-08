package es.reaktor.reaktorclient.windows;

import es.reaktor.models.HardDisk;
import es.reaktor.models.Id.PartitionId;
import es.reaktor.models.Partition;
import org.springframework.stereotype.Service;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class WindowsPartitionHardDisk
{

    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;

    public WindowsPartitionHardDisk()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    public List<Partition> getPartitionHardDisk(List<HardDisk> hardDiskList)
    {

        List<Partition> partitionHardDiskList = new ArrayList<>();

        // We get obtain the list of disk stores and order
        List<HWDiskStore> diskStoreListForOshi = this.windowsHardwareAbstractionLayer.getDiskStores();

        // Order the list for the equals attributes
        diskStoreListForOshi.sort(Comparator.comparing(HWDiskStore::getName));
        hardDiskList.sort(Comparator.comparing(o -> o.getId().getSerialNumberHardDisk()));

        for (int i = 0; i < diskStoreListForOshi.size(); i++)
        {
            for (HWPartition partition : diskStoreListForOshi.get(i).getPartitions())
            {
                Partition partitionHardDisk = new Partition();
                partitionHardDisk.setSize(this.getSize(partition));
                partitionHardDisk.setLetter(this.getLetter(partition));
                partitionHardDisk.setOperatingSystem(this.getOperatingSystem(partitionHardDisk.getLetter()));
                partitionHardDisk.setId(this.getIdPartitionHardDisk(partition, hardDiskList.get(i)));
                partitionHardDiskList.add(partitionHardDisk);
            }
        }
        return partitionHardDiskList;
    }

    private String getOperatingSystem(Character letter)
    {
        File file = new File(letter + ":\\" + "Windows");
        if (file.exists())
        {
            return "Windows";
        }

        file = new File(File.separator + "Volumes");
        if (file.exists())
        {
            return "Mac";
        }

        file = new File(File.separator + "mnt");
        if (file.exists())
        {
            return "Linux";
        }

        return "System File";
    }

    private PartitionId getIdPartitionHardDisk(HWPartition partition, HardDisk hardDisk)
    {
        PartitionId partitionId = new PartitionId(partition.getUuid(), hardDisk);
        return partitionId;
    }

    private Character getLetter(HWPartition partition)
    {
        return partition.getMountPoint().trim().charAt(0);
    }

    private Long getSize(HWPartition partition)
    {
        return partition.getSize()/1024/1024/1024;
    }

}
