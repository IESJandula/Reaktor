package es.iesjandula.reaktor.monitoring_client.windows;

import es.iesjandula.reaktor.models.Motherboard;
import es.iesjandula.reaktor.models.NetworkCard;
import es.iesjandula.reaktor.models.Id.NetworkCardId;
import es.iesjandula.reaktor.monitoring_client.utils.CommandExecutor;
import es.iesjandula.reaktor.monitoring_client.utils.StringsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.hardware.NetworkIF;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;

@Service
public class WindowsNetworkCard
{
    @Autowired
    private StringsUtils stringsUtils;

    /**
     * - Attribute -
     * this class is used to execute the commands of the computer
     */
    @Autowired
    private CommandExecutor commandExecutor;

    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;

    /**
     * - Constructor BY Default -
     * this constructor is used to initialize the class
     */
    public WindowsNetworkCard()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    /**
     * - Method -
     * This method is used to get the network card of the computer and return of the list with my network card
     * @return List of my network card
     */
    public List<NetworkCard> getNetworkCard(Motherboard motherboard)
    {
        // We create the list of network cards
        List<NetworkCard> networkCardList = new ArrayList<>();

        int i = 0;

        for (NetworkIF networkCardForOshi : windowsHardwareAbstractionLayer.getNetworkIFs())
        {
            // We create my network card
            NetworkCard networkCard = new NetworkCard();

            // set the id of my network card
            networkCard.setId(this.getIdNetworkCard(networkCardForOshi, motherboard));

            // set the model of my network card
            networkCard.setModel(this.getModelNetworkCard(networkCardForOshi));

            networkCard.setRj45IsConnected(this.getRj45NetworkCard(networkCardForOshi));

            networkCard.setMacAddress(this.getMacAddressNetworkCard(networkCardForOshi));

            networkCard.setIsWireless(this.getWirelessNetworkCard(networkCardForOshi));

            // We add my network card to the list
            networkCardList.add(networkCard);

            i++;
        }

        return networkCardList;
    }

    private NetworkCardId getIdNetworkCard(NetworkIF networkCard, Motherboard motherboard)
    {

        // We create the id of my network card
        String idNetworkCard;

        // We get the id of my network card
        idNetworkCard = networkCard.getMacaddr();

        // We create the id of my network card
        NetworkCardId networkCardId = new NetworkCardId(idNetworkCard, motherboard);

        return networkCardId;
    }

    private Boolean getWirelessNetworkCard(NetworkIF networkCard)
    {
        return true;
    }

    /**
     * - Method -
     * - OBTAINED BY OSHI -
     * This method is used to get the Mac Address of the network card
     * @param networkCard - network card of the computer obtained of Oshi
     * @return Mac Address of my network card
     */
    private String getMacAddressNetworkCard(NetworkIF networkCard)
    {
        return networkCard.getMacaddr();
    }

    /**
     * - Method -
     * - OBTAINED BY OSHI -
     * This method is used to get if the network card has connected the rj45
     * @param networkCard - network card of the computer obtained of Oshi
     * @return if the rj45 connector is use in my network card
     */
    private Boolean getRj45NetworkCard(NetworkIF networkCard)
    {
        return networkCard.isConnectorPresent();
    }

    /**
     * - Method -
     * - OBTAINED BY OSHI -
     * This method is used to get the model of the network card
     * @param networkCard - network card of the computer obtained of Oshi
     * @return model of my network card
     */
    public String getModelNetworkCard(NetworkIF networkCard)
    {
        return networkCard.getDisplayName();
    }
}
