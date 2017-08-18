package ro.coderdojo.sarmalute;

import java.util.EventListener;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import ro.coderdojo.sarmalute.PasteCommand;
import ro.coderdojo.sarmalute.EventsListener;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
		EventsListener listener = new EventsListener();
		getServer().getPluginManager().registerEvents(listener , this);
		
		this.getCommand("paste").setExecutor(new PasteCommand(listener));
    }

}
