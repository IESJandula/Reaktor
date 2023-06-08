package es.reaktorc.reaktorclient;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import es.reaktor.reaktorclient.utils.ActionsArguments;
import es.reaktor.reaktorclient.utils.CheckerArguments;
import es.reaktor.reaktorclient.utils.CommandExecutor;
import es.reaktor.reaktorclient.utils.Constants;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import es.reaktor.reaktorclient.windows.CommandConstantsWindows;
import es.reaktor.reaktorclient.windows.WindowsMotherboard;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ReaktorClientApplicationTests
{
//
//    @Test
//    void testArguments()
//    {
//        CheckerArguments checkerArguments = new CheckerArguments();
//
//        List<String[]> list = new ArrayList<>();
//
//        list.add("-h".split(" "));
//        list.add("-a 2.11".split(" "));
//        list.add("-p Cayetano".split(" "));
//        list.add("-a 2.11 -p Cayetano".split(" "));
//        list.add("-a 2.11 -p Cayetano -d Second row, right-hand side second computer from the centre".split(" "));
//
//        for (String[] args : list)
//        {
//            Assert.assertTrue(checkerArguments.checkArguments(args));
//        }
//    }
//
//    @Test
//    void actionsArguments()
//    {
//        ActionsArguments actionsArguments = new ActionsArguments();
//
//        String command = "-a 2.11 -p Cayetano -d Second row, right-hand side second computer from the centre";
//
//        try
//        {
//            actionsArguments.actionArguments(command.split(" "));
//        } catch (ReaktorClientException reaktorClientException)
//        {
//            reaktorClientException.printStackTrace();
//        }
//
//    }
//
//    @Test
//    void getModelWindows()
//    {
//
//        String model;
//        CommandExecutor commandExecutor = new CommandExecutor();
//
//        try
//        {
//            model = commandExecutor.executeCommand(CommandConstantsWindows.GET_MODEL_MOTHERBOARD_WINDOWS).replace(Constants.OUTPUT_MODEL_CONSOLE, "").trim();
//
//        }
//        catch (ReaktorClientException reaktorClientException)
//        {
//            model = ConstantsErrors.ERROR_GETTING_HARDWARE_MODEL_MOTHERBOARD;
//            reaktorClientException.printStackTrace();
//        }
//
//        System.out.print(model);
//
//    }

}
