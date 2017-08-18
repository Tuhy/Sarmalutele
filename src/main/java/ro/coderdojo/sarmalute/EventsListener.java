package ro.coderdojo.sarmalute;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

public final class EventsListener implements Listener {

	Map<Player, List<Location>> points = new HashMap<>();
	Map<Player, BlockCopyData[][][]> clipboard = new HashMap<>();

	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("Welcome" + ChatColor.AQUA + player.getName() + ChatColor.WHITE + "! Felicitări pentru primul mod de Minecraft!");
	}

	@EventHandler
	public void savepoints(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD) && event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (points.get(player) == null) {
				points.put(player, new ArrayList());
			}
			List<Location> list = points.get(player);

			if (list.size() == 2) {
				list.removeAll(list);
			}

			Block block = event.getClickedBlock();
			list.add(block.getLocation());

			if (list.size() == 2) {
				copy(event.getPlayer());
			}

		}

	}

	private void copy(Player player) {
//		System.out.println("copy");
		int length = Math.abs(Math.abs(points.get(player).get(0).getBlockX()) - Math.abs(points.get(player).get(1).getBlockX()));
		int width = Math.abs(Math.abs(points.get(player).get(0).getBlockY() - Math.abs(points.get(player).get(1).getBlockY())));
		int height = Math.abs(Math.abs(points.get(player).get(0).getBlockZ() - Math.abs(points.get(player).get(1).getBlockZ())));

		int initialX = points.get(player).get(0).getBlockX();
		int initialY = points.get(player).get(0).getBlockY();
		int initialZ = points.get(player).get(0).getBlockZ();

		int xIncrement = initialX < points.get(player).get(1).getBlockX() ? 1 : -1;
		int yIncrement = initialY < points.get(player).get(1).getBlockY() ? 1 : -1;
		int zIncrement = initialZ < points.get(player).get(1).getBlockZ() ? 1 : -1;

		System.out.println("L:" + length + " l:" + width + " h:" + height);
		player.sendMessage(ChatColor.RED + "Ai copiat o prisma: " + "L:" + ChatColor.WHITE + length + ChatColor.RED + " l:" + ChatColor.WHITE + width + ChatColor.RED + " h:" + ChatColor.WHITE + height);
		clipboard.put(player, new BlockCopyData[length + 1][width + 1][height + 1]);
		int xLoop = 0;
		int yLoop = 0;
		int zLoop = 0;
		for (int x = initialX; (x >= initialX - length) && (x <= initialX + length); x = x + xIncrement) {
			yLoop = 0;
			for (int y = initialY; (y >= initialY - width) && (y <= initialY + width); y = y + yIncrement) {
				zLoop = 0;
				for (int z = initialZ; (z >= initialZ - height) && (z <= initialZ + height); z = z + zIncrement) {
					Location loc = new Location(points.get(player).get(0).getWorld(), x, y, z);
					Block block = loc.getBlock();

					clipboard.get(player)[xLoop][yLoop][zLoop] = new BlockCopyData();
					clipboard.get(player)[xLoop][yLoop][zLoop].material = block.getType();
					clipboard.get(player)[xLoop][yLoop][zLoop].data = block.getData();
					zLoop++;
				}
				yLoop++;
			}
			xLoop++;
		}

	}

	public static class BlockCopyData {

		public Material material;
		public byte data;
	}

}
