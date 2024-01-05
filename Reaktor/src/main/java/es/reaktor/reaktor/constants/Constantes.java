package es.reaktor.reaktor.constants;

import java.util.LinkedList;
import java.util.List;

import es.reaktor.reaktor.models.CommandLine;
import es.reaktor.reaktor.models.ComponentCpu;
import es.reaktor.reaktor.models.ComponentHardDisk;
import es.reaktor.reaktor.models.ComponentRam;
import es.reaktor.reaktor.models.Computer;
import es.reaktor.reaktor.models.HardwareComponent;
import es.reaktor.reaktor.models.Location;
import es.reaktor.reaktor.models.MonitorizationLog;
import es.reaktor.reaktor.models.Software;

public final class Constantes 
{
	private static final String WINDOWS = "windows";
	/**CARGADO HARDWARE UNICO DE ESTA CLASE */
	private static final List<HardwareComponent> cargarHardware()
	{
		List<HardwareComponent> hardware = new LinkedList<HardwareComponent>();
		hardware.add(new ComponentRam("ValueRAM Kingston",2,8));
		hardware.add(new ComponentHardDisk("SanDisk",1,"HDD",240));
		hardware.add(new ComponentCpu("AMD Ryzen 9",1,12));
		return hardware;
	}
	/**CARGADO SOFTWARE UNICO DE ESTA CLASE */
	private static final List<Software> cargarSoftware()
	{
		List<Software> software = new LinkedList<Software>();
		software.add(new Software("Notepad ++"));
		software.add(new Software("Python"));
		software.add(new Software("Google Chrome"));
		software.add(new Software("Microsoft EDGE"));
		return software;
	}
	/**CARGADO LISTA PC */
	public static final List<Computer> cargarOrdenadores()
	{
		List<Computer> pcs = new LinkedList<Computer>();
		Location loc = new Location("1",1,"1");
		pcs.add(new Computer("123456-DFG","AND-1111","1D",WINDOWS,"Paco Benitez",loc,Constantes.cargarHardware(),Constantes.cargarSoftware(),new CommandLine(),new MonitorizationLog()));
		pcs.add(new Computer("123457-DFE","AND-1112","2D",WINDOWS,"Paco Benitez",loc,Constantes.cargarHardware(),Constantes.cargarSoftware(),new CommandLine(),new MonitorizationLog()));
		loc = new Location("2",1,"1");
		pcs.add(new Computer("654321-FGD","AND-1113","1I",WINDOWS,"Vicente Serrano",loc,Constantes.cargarHardware(),Constantes.cargarSoftware(),new CommandLine(),new MonitorizationLog()));
		pcs.add(new Computer("654322-FGE","AND-1114","2I",WINDOWS,"Vicente Serrano",loc,Constantes.cargarHardware(),Constantes.cargarSoftware(),new CommandLine(),new MonitorizationLog()));
		loc = new Location ("2",2,"1");
		pcs.add(new Computer("123564-BFG","AND-1115","4I",WINDOWS,"Manuel Amaro",loc,Constantes.cargarHardware(),Constantes.cargarSoftware(),new CommandLine(),new MonitorizationLog()));
		pcs.add(new Computer("465321-GBF","AND-1116","5I",WINDOWS,"Manuel Amaro",loc,Constantes.cargarHardware(),Constantes.cargarSoftware(),new CommandLine(),new MonitorizationLog()));

		return pcs;
	}
}
