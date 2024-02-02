package es.monitoringserver.models;

import java.util.List;

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
	private List<String> commands;
}
