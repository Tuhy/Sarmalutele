package ro.coderdojo.sarmalute;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CoderDojoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		commandSender.sendMessage("Ai reușit să creezi o comandă în Minecraft cu Spigot");
		return false;
	}
}
