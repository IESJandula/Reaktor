package es.iesjandula.reaktor.monitoring_client.windows;

import es.iesjandula.reaktor.models.Motherboard;
import es.iesjandula.reaktor.models.SoundCard;
import es.iesjandula.reaktor.models.Id.SoundCardId;
import es.iesjandula.reaktor.monitoring_client.utils.CommandExecutor;
import es.iesjandula.reaktor.monitoring_client.utils.Constants;
import es.iesjandula.reaktor.monitoring_client.utils.StringsUtils;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ConstantsErrors;
import es.iesjandula.reaktor.monitoring_client.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WindowsSoundCard
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
    private WindowsHardwareAbstractionLayer windowsHardwareAbstractionLayer;

    /**
     * - Constructor BY Default -
     * this constructor is used to initialize the class
     */
    public WindowsSoundCard()
    {
        this.windowsHardwareAbstractionLayer = new WindowsHardwareAbstractionLayer();
    }

    /**
     * - Method -
     * This method is used to get the sound card of the computer and return of the list with my sound card
     * @return List of my sound card
     */
    public List<SoundCard> getSoundsCard(Motherboard motherboard)
    {
        // We create the list of sound cards
        List<SoundCard> soundCardList = new ArrayList<>();

        int i = 0;

        for (oshi.hardware.SoundCard soundCardForOshi : windowsHardwareAbstractionLayer.getSoundCards())
        {
            // We create my sound card
            SoundCard soundCard = new SoundCard();

            // set the id of my sound card
            soundCard.setId(this.getIdSoundCard(soundCardForOshi, motherboard, i));

            // set the driver of my sound card
            soundCard.setDriver(this.getDriverSoundsCard(soundCardForOshi));

            // set the model of my sound card
            soundCard.setModel(this.getModelSoundsCard(soundCardForOshi));

            // We add my graphic card to the list
            soundCardList.add(soundCard);
            i++;
        }

        return soundCardList;
    }

    private SoundCardId getIdSoundCard(oshi.hardware.SoundCard soundCardForOshi, Motherboard motherboard, int index)
    {
        String idSoundCard;

        try
        {
            // We get the id of my graphic card
            idSoundCard = this.commandExecutor.executeCommand(CommandConstantsWindows.GET_ID_SOUND_CARD_WINDOWS).replace(Constants.OUTPUT_ID_SOUND_CARD_CONSOLE, Constants.EMPTY_STRING).trim();

            // We clean of de result of the command
            idSoundCard = stringsUtils.getLine(idSoundCard.replaceAll("\n\n", "\n").trim(), index);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            // We set the id of my graphic card to unknown in case of exception
            idSoundCard = Constants.UNKNOWN;
            log.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_MODEL_MOTHERBOARD, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        // We create the id of my graphic card
        SoundCardId soundCardId = new SoundCardId(idSoundCard, motherboard);

        return soundCardId;
    }

    private String getDriverSoundsCard(oshi.hardware.SoundCard soundCard)
    {
        return soundCard.getDriverVersion();
    }

    public String getModelSoundsCard(oshi.hardware.SoundCard soundCard)
    {
        return soundCard.getName();
    }


}
