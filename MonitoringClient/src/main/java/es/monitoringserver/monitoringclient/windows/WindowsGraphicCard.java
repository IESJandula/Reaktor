package es.monitoringserver.monitoringclient.windows;

import es.monitoringserver.models.GraphicCard;
import es.monitoringserver.models.Motherboard;
import es.monitoringserver.models.Id.GraphicCardId;
import es.monitoringserver.monitoringclient.utils.CommandExecutor;
import es.monitoringserver.monitoringclient.utils.Constants;
import es.monitoringserver.monitoringclient.utils.StringsUtils;
import es.monitoringserver.monitoringclient.utils.exceptions.ConstantsErrors;
import es.monitoringserver.monitoringclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.hardware.GraphicsCard;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * - Class -
 * This class is used to get the information of the graphic card of the computer
 */
@Service
@Slf4j
public class WindowsGraphicCard
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
    public WindowsGraphicCard()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    /**
     * - Method -
     * This method is used to get the graphic card of the computer and return of the list with my graphic card
     * @return List of my graphic card
     */
    public List<GraphicCard> getGraphicCard(Motherboard motherboard)
    {
        // We create the list of graphic cards
        List<GraphicCard> graphicCardList = new ArrayList<>();

        int i = 0;

        for (GraphicsCard graphicsCardForOshi : windowsHardwareAbstractionLayer.getGraphicsCards())
        {
            // We create my graphic card
            GraphicCard graphicCard = new GraphicCard();

            // set the id of my graphic card
            graphicCard.setId(this.getIdGraphicCard(motherboard, i));

            // set the model of my graphic card
            graphicCard.setModel(this.getModelGraphicCard(graphicsCardForOshi));

            // We add my graphic card to the list
            graphicCardList.add(graphicCard);

            i++;
        }

        return graphicCardList;
    }

    /**
     * - Method -
     * - OBTAINED BY COMMAND LINE -
     * This method is used to get the id of the graphic card with command line
     * @return id of my graphic card
     */
    private GraphicCardId getIdGraphicCard(Motherboard motherboard, int index)
    {
        // We create the id of my graphic card
        String idGraphicCard;

        try
        {
            // We get the id of my graphic card
            idGraphicCard = this.commandExecutor.executeCommand(CommandConstantsWindows.GET_ID_GRAPHIC_CARD_WINDOWS).replace(Constants.OUTPUT_GRAPHIC_CARD_ID_CONSOLE, Constants.EMPTY_STRING).trim();

            idGraphicCard = this.stringsUtils.getLine(idGraphicCard.replaceAll("\n\n", "\n").trim(), index);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            // We set the id of my graphic card to unknown in case of exception
            idGraphicCard = Constants.UNKNOWN;
            log.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_MODEL_MOTHERBOARD, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        // We create the id of my graphic card
        GraphicCardId graphicCardId = new GraphicCardId(idGraphicCard, motherboard);

        return graphicCardId;
    }

    /**
     * - Method -
     * - OBTAINED BY OSHI -
     * This method is used to get the model of the graphic card
     * @param graphicsCard - graphic card of the computer obtained of Oshi
     * @return model of my graphic card
     */
    public String getModelGraphicCard(GraphicsCard graphicsCard)
    {
        return graphicsCard.getName();
    }

}
