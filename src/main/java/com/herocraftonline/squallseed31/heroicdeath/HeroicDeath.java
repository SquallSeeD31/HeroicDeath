package com.herocraftonline.squallseed31.heroicdeath;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class HeroicDeath extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	
	private final HeroicDeathListener listener = new HeroicDeathListener(this);
	
	public PluginDescriptionFile pdfFile;
	public String name;
	public String version;
	
	public File dataFolder;
	public static boolean logData;
	public static boolean logMessages;
	public static boolean timestampMessages;
	private boolean eventsOnly;
	public File dataLog;
	public File messageLog;
	private static String mLog;
	private static String dLog;

	public static HeroicDeathMessages DeathMessages = new HeroicDeathMessages();
	public static HeroicDeathItems Items = new HeroicDeathItems();
	
	public Configuration config;
	public static String messageColor;
	public static String nameColor;
	public static String itemColor;
	public String mobUnknown;
	public String mobMonster;
	public String mobPigZombie;
	public String mobZombie;
	public String mobSkeleton;
	public String mobSpider;
	public String mobCreeper;
	public String mobGhast;
	public String mobSlime;
	public String mobGiant;
	public String mobWolf;

	public static String timestampFormat;
	
	//Set debugging true to see debug messages
	public static final Boolean debugging = false;

  public void onEnable()
  {
	this.config = getConfiguration();
	pdfFile = getDescription();
	name = pdfFile.getName();
	version = pdfFile.getVersion();
	dataFolder = getDataFolder();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvent(Event.Type.ENTITY_DAMAGE, this.listener, Event.Priority.Monitor, this);
    pm.registerEvent(Event.Type.ENTITY_DEATH, this.listener, Event.Priority.Monitor, this);

    messageColor = getConfigColor("colors.message", "RED");
    nameColor = getConfigColor("colors.name", "DARK_AQUA");
    itemColor = getConfigColor("colors.item", "GOLD");
    logData = this.config.getBoolean("log.data", true);
    logMessages = this.config.getBoolean("log.messages", true);
    timestampFormat = this.config.getString("log.time.format", "MM/dd/yyyy HH:mm:ss z");
    timestampMessages = this.config.getBoolean("log.time.stamp", true);
    this.eventsOnly = this.config.getBoolean("events.only", false);
    dLog = this.config.getString("log.files.data", "death_data.log");
    mLog = this.config.getString("log.files.messages", "death_messages.log");
    mobUnknown = this.config.getString("monsters.unknown", "Unknown");
    mobMonster = this.config.getString("monsters.monster", "Monster");
    mobPigZombie = this.config.getString("monsters.pigzombie", "PigZombie");
    mobZombie = this.config.getString("monsters.zombie", "Zombie");
    mobSkeleton = this.config.getString("monsters.skeleton", "Skeleton");
    mobSpider = this.config.getString("monsters.spider", "Spider");
    mobCreeper = this.config.getString("monsters.creeper", "Creeper");
    mobGhast = this.config.getString("monsters.ghast", "Ghast");
    mobSlime = this.config.getString("monsters.slime", "Slime");
    mobGiant = this.config.getString("monsters.giant", "Giant");
    mobWolf = this.config.getString("monsters.wolf", "Wolf");

    saveConfig();
    try {
    	if (logData)
    		this.dataLog = new File(dataFolder, dLog);
    	if (logMessages)
    		this.messageLog = new File(dataFolder, mLog);
    } catch (Exception e){
    	log.severe("[" + name + "] Error opening logfiles; bad filename?");
    	if (logData)
    		this.dataLog = new File(dataFolder, "death_data.log");
    	if (logMessages)
    		this.messageLog = new File(dataFolder, "death_messages.log");
    }
    initFiles();
    DeathMessages.load(dataFolder);
    Items.load(dataFolder);
    
    String strEnable = "[" + name + "] " + version + " enabled.";
    log.info(strEnable);
 }
  
  public boolean getEventsOnly()
  {
	  return this.eventsOnly;
  }

  private void initFiles() {
	  if (logData && !this.dataLog.exists()) {
		  try {
			    this.dataLog.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.dataLog,true));
				writer.write("#HeroicDeath Data Log - This file stores serialized data on player deaths in the following order:");
				writer.newLine();
				writer.write("#Victim|Killer|Murder Weapon|Cause of Death|Location|Timestamp|Death Message");
				writer.newLine();
				writer.write("#Murder weapon is in the format TypeIDxData, so Red Wool would be 35x14");
				writer.newLine();
				writer.write("#Location is in the format (x, y, z), and represents where the victim died.");
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				log.severe("[" + name + "] Error writing data log: ");
				e.printStackTrace();
			}
	  }
	  if (logMessages && !this.messageLog.exists()) {
		  try {
			    this.messageLog.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.messageLog,true));
				writer.write("#HeroicDeath Message Log - This file stores player death messages as reported to the server");
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				log.severe("[" + name + "] Error writing message log: ");
				e.printStackTrace();
			}
	  }
  }
  public void onDisable()
  {
	    String strDisable = "[" + name + "] " + version + " disabled.";
	    log.info(strDisable);
  }
  
  public String getConfigColor(String property, String def) {
	  String propColor = this.config.getString(property, def);
	  ChatColor returnColor = null;
	  try {
		returnColor = ChatColor.valueOf(propColor);
	  } catch (Exception e) {
		log.info("HeroicDeath: Improper color definition in config.yml, using default.");
		returnColor = ChatColor.valueOf(def);
	  }
	  return returnColor.toString();
  }
  
  public void recordDeath(DeathCertificate dc) {
	  if (logData) {
		  try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.dataLog,true));
			writer.write(dc.toString());
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			log.severe("[" + name + "] Error writing data log: ");
			e.printStackTrace();
		}
	  }
	  if (logMessages) {
		  String message = dc.getMessage().replaceAll("(?i)\u00A7[0-F]", "");
		  if (timestampMessages)
			  message = "[" + dc.getFormatTime() + "] " + message;
		  try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.messageLog,true));
				writer.write(message);
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				log.severe("[" + name + "] Error writing message log: ");
				e.printStackTrace();
			}
	  }
  }
  
  private void saveConfig() {
	  this.config.setProperty("colors.message", this.config.getString("colors.message", "RED"));
	  this.config.setProperty("colors.name", this.config.getString("colors.name", "DARK_AQUA"));
	  this.config.setProperty("colors.item", this.config.getString("colors.item", "GOLD"));
	  this.config.setProperty("log.data", logData);
	  this.config.setProperty("log.messages", logMessages);
	  this.config.setProperty("log.time.format", timestampFormat);
	  this.config.setProperty("log.files.data", dLog);
	  this.config.setProperty("log.files.messages", mLog);
	  this.config.setProperty("events.only", eventsOnly);
	  this.config.setProperty("monsters.unknown", mobUnknown);
	  this.config.setProperty("monsters.monster", mobMonster);
	  this.config.setProperty("monsters.pigzombie", mobPigZombie);
	  this.config.setProperty("monsters.zombie", mobZombie);
	  this.config.setProperty("monsters.skeleton", mobSkeleton);
	  this.config.setProperty("monsters.spider", mobSpider);
	  this.config.setProperty("monsters.creeper", mobCreeper);
	  this.config.setProperty("monsters.ghast", mobGhast);
	  this.config.setProperty("monsters.slime", mobSlime);
	  this.config.setProperty("monsters.giant", mobGiant);
	  this.config.setProperty("monsters.wolf", mobWolf);

	  this.config.save();
  }
  
  public static void debug(String message) {
	  if (HeroicDeath.debugging) {
		  log.info(message);
	  }
  }
}