package MiPrimerPlugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Comandos.comandoPrincipal;
import Eventos.ZombieKills;
import Eventos.spawnEntrar;
import net.md_5.bungee.api.ChatColor;

public class MiPrimerPlugin extends JavaPlugin {

	public String rutaConfig;
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = pdffile.getName();

	public void onEnable() {
		Bukkit.getConsoleSender()
				.sendMessage("---------------------------------------------------------------------------------");
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.BLUE + nombre + " " + "El plugin ha sido activado(version:" + version + ")");
		Bukkit.getConsoleSender()
				.sendMessage("---------------------------------------------------------------------------------");
		this.registerConfig();
		this.registrarComandos();
		this.registrarEventos();
		
	}

	public void onDisable() {
		Bukkit.getConsoleSender()
				.sendMessage("---------------------------------------------------------------------------------");
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.BLUE + nombre + " " + "El plugin ha sido desactivado(version:" + version + ")");
		Bukkit.getConsoleSender()
				.sendMessage("---------------------------------------------------------------------------------");

	}

	public void registrarComandos() {
		this.getCommand("principal").setExecutor(new comandoPrincipal(this));
	}

	public void registrarEventos() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new spawnEntrar(this), this);
		pm.registerEvents(new ZombieKills(this), this);
	}

	public void registerConfig() {
		File config = new File(this.getDataFolder(), "config.yml");
		this.rutaConfig = config.getPath();
		if (!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		}

	}

}
