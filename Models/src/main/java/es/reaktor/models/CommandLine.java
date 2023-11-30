package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandLine
{
	/**
     * - ATTRIBUTES -
     * This attributes have a list of commands
     */
	private String[] commands;
}
