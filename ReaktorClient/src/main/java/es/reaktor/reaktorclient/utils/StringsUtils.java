package es.reaktor.reaktorclient.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StringsUtils
{
    public String getLine(String id, int index)
    {
        Scanner scanner = new Scanner(id);
        int i = 0;
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if (i == index)
            {
                return line.trim();
            }
            i++;
        }
        return Constants.UNKNOWN;
    }
}
