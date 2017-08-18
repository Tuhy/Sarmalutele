package ro.coderdojo.sarmalute;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.coderdojo.sarmalute.EventsListener.BlockCopyData;


public class PasteCommand implements CommandExecutor {

	EventsListener listener;

	public PasteCommand(EventsListener listener) {
		this.listener = listener;
	}
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		Player player = (Player) commandSender;
		
		BlockCopyData[][][] clipboard = listener.clipboard.get(player);
		
		if(clipboard == null) {
					((Player)commandSender).sendMessage("intai copiaza");
		}
		
		Block target = player.getTargetBlock(null, 100);
		for(int x=0; x<clipboard.length; x++){
			for(int y=0; y<clipboard[0].length; y++){
				for(int z=0; z<clipboard[0][0].length; z++){
					if(clipboard[x][y][z].material == Material.AIR) {
						continue;
					}
					target.getLocation().add(x,y,z).getBlock().setType(clipboard[x][y][z].material);
					target.getLocation().add(x,y,z).getBlock().setData(clipboard[x][y][z].data);
				}
			}
		}
		
		return true;
	}
}
