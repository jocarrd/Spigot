package Eventos;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import MiPrimerPlugin.MiPrimerPlugin;

public class ZombieKills implements Listener {
	private MiPrimerPlugin plugin;

	public ZombieKills(MiPrimerPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void matarZombies(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		EntityType entidad = event.getEntityType();
		if (killer != null && killer.getType().equals(EntityType.PLAYER) && entidad.equals(EntityType.ZOMBIE)) {
			FileConfiguration config = plugin.getConfig();

			config.set("Players." + killer.getUniqueId() + ".name", killer.getName());

			if (config.contains("Players." + killer.getUniqueId() + ".zombiekills")) {
				int cantidadzombies = Integer
						.valueOf(config.getString("Players." + killer.getUniqueId() + ".zombiekills"));
				config.set("Players." + killer.getUniqueId() + ".zombiekills", cantidadzombies + 1);
			} else {
				config.set("Players." + killer.getUniqueId() + ".zombiekills", 1);
			}
			plugin.saveConfig();
		}
	}
}
