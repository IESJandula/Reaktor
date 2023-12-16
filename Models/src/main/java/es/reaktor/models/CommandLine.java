package es.reaktor.models;

import java.util.List;

public class CommandLine 
{

		private List<String> commands;
		
		
		/**
		 * 
		 */
		public CommandLine() {
			
		}
		
		/**
		 * 
		 * @param commands
		 */
		public CommandLine(List<String> commands) {
			super();
			this.commands = commands;
		}
		
		/**
		 * 
		 * @return list of comands
		 */
		public List<String> getCommands() {
			return commands;
		}
		
		/**
		 * 
		 * @param commands
		 */
		public void setCommands(List<String> commands) {
			this.commands = commands;
		}
		
		
}
