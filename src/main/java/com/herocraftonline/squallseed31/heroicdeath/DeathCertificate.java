package com.herocraftonline.squallseed31.heroicdeath;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.material.MaterialData;

public class DeathCertificate {
	private String defender;
	private String attacker;
	private MaterialData murderWeapon;
	private DamageCause cause;
	private String message;
	private Date timestamp;
	private Location location;
	
	public DeathCertificate(String defender, String attacker,
			MaterialData murderWeapon, DamageCause cause, String message,
			Date timestamp, Location location) {
		this.defender = defender;
		this.attacker = attacker;
		this.murderWeapon = murderWeapon;
		this.cause = cause;
		this.message = message;
		this.timestamp = timestamp;
		this.location = location;
	}
	
	public DeathCertificate(Player p) {
		this(HeroicDeath.getPlayerName(p),"",null,DamageCause.CUSTOM,null,new Date(),p.getLocation());
	}
	
	public DeathCertificate(Player p, DamageCause cause) {
		this(HeroicDeath.getPlayerName(p),"",null,cause,null,new Date(),p.getLocation());
	}
	
	public DeathCertificate(Player p, String attacker, DamageCause cause) {
		this(HeroicDeath.getPlayerName(p),attacker,null,cause,null,new Date(),p.getLocation());
	}

	public DeathCertificate(Player p, String attacker, MaterialData murderWeapon, DamageCause cause, String message) {
		this(HeroicDeath.getPlayerName(p),attacker,murderWeapon,cause,message,new Date(),p.getLocation());
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	public Location getLocation() {
		return location;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getFormatTime() {
		try {
			SimpleDateFormat format = new SimpleDateFormat(HeroicDeath.timestampFormat);
			return format.format(timestamp);	
		} catch (IllegalArgumentException e) {
			HeroicDeath.log.severe("[HeroicDeath] Couldn't use provided timestamp format, using default.");
			e.printStackTrace();
			HeroicDeath.timestampFormat = "MM/dd/yyyy HH:mm:ss z";
			SimpleDateFormat format = new SimpleDateFormat(HeroicDeath.timestampFormat);
			return format.format(timestamp);	
		}
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setCause(DamageCause cause) {
		this.cause = cause;
	}
	public DamageCause getCause() {
		return cause;
	}
	public void setMurderWeapon(MaterialData murderWeapon) {
		this.murderWeapon = murderWeapon;
	}
	public MaterialData getMurderWeapon() {
		return murderWeapon;
	}
	public void setAttacker(String attacker) {
		this.attacker = attacker;
	}
	public String getAttacker() {
		return attacker;
	}
	public void setDefender(String defender) {
		this.defender = defender;
	}
	public String getDefender() {
		return defender;
	}
	
	@Override
	public String toString() {
		double x = Math.round(location.getX());
		double y = Math.round(location.getY());
		double z = Math.round(location.getZ());
		String loc = location.getWorld().getName() + " (" + x + "," + y + "," + z + ")";
		String mWeapon = "";
		if (murderWeapon != null) {
			byte mData = murderWeapon.getData();
			int mType = murderWeapon.getItemTypeId();
			mWeapon = mType + "x" + mData;
		}
		String dCause = cause.name();
		String time = getFormatTime();
		time = time.replaceAll("\\|", ":");
		String fMsg = message.replaceAll("(?i)\u00A7[0-F]", "");
		fMsg = fMsg.replaceAll("\\|", ":");
		return defender + "|" + attacker + "|" + mWeapon + "|" + dCause + "|" + loc + "|" + time + "|" + fMsg;
	}
}
