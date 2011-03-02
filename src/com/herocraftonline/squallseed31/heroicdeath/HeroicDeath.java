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
    pm.registerEvent(Event.Type.ENTITY_DAMAGED, this.listener, Event.Priority.Monitor, this);
    pm.registerEvent(Event.Type.ENTITY_DEATH, this.listener, Event.Priority.Monitor, this);

    HeroicDeath.messageColor = getConfigColor("colors.message", "RED");
    HeroicDeath.nameColor = getConfigColor("colors.name", "DARK_AQUA");
    HeroicDeath.itemColor = getConfigColor("colors.item", "GOLD");
    HeroicDeath.logData = this.config.getBoolean("log.data", true);
    HeroicDeath.logMessages = this.config.getBoolean("log.messages", true);
    HeroicDeath.timestampFormat = this.config.getString("log.time.format", "MM/dd/yyyy HH:mm:ss z");
    HeroicDeath.timestampMessages = this.config.getBoolean("log.time.stamp", true);
    this.eventsOnly = this.config.getBoolean("events.only", false);
    HeroicDeath.dLog = this.config.getString("log.files.data", "death_data.log");
    HeroicDeath.mLog = this.config.getString("log.files.messages", "death_messages.log");
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
	  this.getServer().getPluginManager().callEvent(new HeroicDeathEvent(dc));
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
	  this.config.save();
  }
  
  public static void debug(String message) {
	  if (HeroicDeath.debugging) {
		  log.info(message);
	  }
  }
}