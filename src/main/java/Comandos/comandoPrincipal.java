package Comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import MiPrimerPlugin.MiPrimerPlugin;

public class comandoPrincipal implements CommandExecutor {
	private MiPrimerPlugin plugin;

	public comandoPrincipal(MiPrimerPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] arg3) {
		
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(plugin.nombre + "No puedes ejecutar mensajes desde la consola");
			return false;
		} else {
			Player jugador = (Player) sender;
			
			if (arg3.length > 0) {

				if (arg3[0].equalsIgnoreCase("setSpawn")) {

					Location l = jugador.getLocation();
					double x = l.getX();
					double y = l.getY();
					double z = l.getZ();
					String world = l.getWorld().getName();
					float pitch = l.getPitch();
					float yaw = l.getYaw();
					FileConfiguration config = plugin.getConfig();
					config.set("Config.spawn.x", x);
					config.set("Config.spawn.y", y);
					config.set("Config.spawn.z", z);
					config.set("Config.spawn.yaw", yaw);
					config.set("Config.spawn.pitch", pitch);
					config.set("Config.spawn.world", world);
					plugin.saveConfig();
					jugador.sendMessage(ChatColor.LIGHT_PURPLE + "El spawn del servidor ha sido cambiado");
					return true;

				} else if (arg3[0].equals("reload")) {

					plugin.reloadConfig();
					jugador.sendMessage(ChatColor.LIGHT_PURPLE + "La configuracion del Pluigin ha sido actualizada");
				}

				else if (arg3[0].equals("spawn")) {
					FileConfiguration config = plugin.getConfig();

					if (config.contains("Config.spawn")) {

						double x = Double.valueOf(config.getString("Config.spawn.x"));
						double y = Double.valueOf(config.getString("Config.spawn.y"));
						double z = Double.valueOf(config.getString("Config.spawn.z"));
						World world = plugin.getServer().getWorld(config.getString("Config.spawn.world"));
						float pitch = Float.valueOf(config.getString("Config.spawn.pitch"));
						float yaw = Float.valueOf(config.getString("Config.spawn.yaw"));
						Location l = new Location(world, x, y, z, yaw, pitch);
						jugador.teleport(l);
					} else {
						jugador.sendMessage(ChatColor.LIGHT_PURPLE + "No hay un Spawn establecido");
					}
				}

				else if (arg3[0].equals("kills")) {
					FileConfiguration config = plugin.getConfig();

					if (config.contains("Players")) {

						if (config.contains("Players." + jugador.getUniqueId() + ".zombiekills")) {
							jugador.sendMessage("Zombie kills: " + ChatColor.LIGHT_PURPLE
									+ config.getString("Players." + jugador.getUniqueId() + ".zombiekills"));
						} else {
							jugador.sendMessage(ChatColor.LIGHT_PURPLE + "El jugador no ha matado ningun zombie");
						}
					} else {
						jugador.sendMessage(ChatColor.LIGHT_PURPLE + "El jugador no ha matado ningun zombie");
					}
				}

				else if (arg3[0].equals("report")) {
					FileConfiguration config = plugin.getConfig();
					if (arg3.length == 1) {
						jugador.sendMessage(ChatColor.LIGHT_PURPLE + "Para poder reportar escribe: "
								+ ChatColor.LIGHT_PURPLE + "/Main report <usuario>");
					} else {

						String usuario = arg3[1];
						if (Bukkit.getPlayer(usuario) != null) {
							if (config.contains("Config.usuarios-reportados")) {
								List<String> reportados = config.getStringList("Config.usuarios-reportados");
								if (reportados.contains(usuario)) {
									jugador.sendMessage(ChatColor.LIGHT_PURPLE + " ERROR : El usuario : " + usuario
											+ " ha sido reportado anteriormente");
								} else {
									reportados.add(usuario);
									config.set("Config.usuarios-reportados", reportados);
									plugin.saveConfig();
									jugador.sendMessage(ChatColor.LIGHT_PURPLE + "El usuario : " + usuario
											+ " ha sido reportado correctamente");
								}

							} else {
								List<String> reportados = new ArrayList<String>();
								reportados.add(usuario);
								config.set("Config.usuarios-reportados", reportados);
								plugin.saveConfig();
								jugador.sendMessage(ChatColor.LIGHT_PURPLE + "El usuario : " + usuario
										+ " ha sido reportado correctamente");

							}

						} else {
							jugador.sendMessage(ChatColor.LIGHT_PURPLE + "El jugador no esta conectado");
						}
					}
				}
			}else {
				jugador.sendMessage(ChatColor.LIGHT_PURPLE + "Comando incorrecto");
				
			}
			
		}
		return true;
	}

}
