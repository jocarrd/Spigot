package Eventos;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import MiPrimerPlugin.MiPrimerPlugin;
import net.md_5.bungee.api.ChatColor;

public class spawnEntrar implements Listener {

	private MiPrimerPlugin plugin;

	public spawnEntrar(MiPrimerPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void entrar(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		FileConfiguration config = plugin.getConfig();
		
		if(config.contains(("Config.Spawn.x"))){

		double x = Double.valueOf(config.getString("Config.Spawn.x"));
		double y = Double.valueOf(config.getString("Config.Spawn.y"));
		double z = Double.valueOf(config.getString("Config.Spawn.z"));
		float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
		float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
		World world = plugin.getServer().getWorld(config.getString("Config.Spawn.world"));

		Location l = new Location(world, x, y, z, yaw, pitch);
		jugador.teleport(l);
		}
		

		String path = "Config.mensaje-bienvenida";

		if (config.getString(path).contentEquals("true")) {
			List<String> mensaje = config.getStringList("Config.mensaje-bienvenida-texto");
			for (String d : mensaje) {
				jugador.sendMessage(ChatColor.RED+d);
			}

		}

	}
}
