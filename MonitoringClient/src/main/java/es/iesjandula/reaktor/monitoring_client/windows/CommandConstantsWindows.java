package es.iesjandula.reaktor.monitoring_client.windows;

public final class CommandConstantsWindows
{

    /**
     * - COMMANDS FOR MOTHERBOARD -
     * these commands are used to get the information of the motherboard
     */
    public static final String GET_MOTHERBOARD_WINDOWS = "wmic baseboard get";
    public static final String GET_MODEL_MOTHERBOARD_WINDOWS = GET_MOTHERBOARD_WINDOWS + " product";

    /**
     * - COMMANDS FOR SOUND CARD -
     * these commands are used to get the information of the sound card
     */
    public static final String GET_SOUND_CARD_WINDOWS = "wmic sounddev get";
    public static final String GET_ID_SOUND_CARD_WINDOWS = GET_SOUND_CARD_WINDOWS + " DeviceID";

    /**
     * - COMMANDS FOR RAM -
     * these commands are used to get the information of the ram
     */
    public static final String GET_RAM_WINDOWS = "wmic memorychip get";
    public static final String GET_ID_RAM_WINDOWS = GET_RAM_WINDOWS + " PartNumber";
    public static final String GET_ID_RAM_WINDOWS_BANK = GET_ID_RAM_WINDOWS + ", BankLabel";

    /**
     * - COMMANDS FOR GRAPHICS CARD -
     * these commands are used to get the information of the graphics card
     */
    public static final String GET_GRAPHIC_CARD_WINDOWS = "wmic path win32_VideoController get";
    public static final String GET_ID_GRAPHIC_CARD_WINDOWS = GET_GRAPHIC_CARD_WINDOWS + " PNPDeviceID";

}
