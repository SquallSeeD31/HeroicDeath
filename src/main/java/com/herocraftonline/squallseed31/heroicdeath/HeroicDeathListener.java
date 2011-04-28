package com.herocraftonline.squallseed31.heroicdeath;

import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player; 
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener; 
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HeroicDeathListener extends EntityListener { 
 private final HeroicDeath plugin; 
 protected Map<String, DeathCertificate> deathRecords = new HashMap<String, DeathCertificate>();

 private Random RN = new Random();
 
 public HeroicDeathListener(HeroicDeath instance) { 
	 plugin = instance; 
 }

 public String getMessage(ArrayList<String> ArrayString, DeathCertificate dc)
 {
	 String attackerName = dc.getAttacker();
	 String murderWeapon = null;
	 if (dc.getMurderWeapon() != null)
		 murderWeapon = HeroicDeath.Items.getItem(dc.getMurderWeapon());
	 if (ArrayString.size() == 0)
	     return dc.getDefender() + " died";
   if (ArrayString.size() > 1)
   {
     int num = this.RN.nextInt(ArrayString.size());
     String temp = (String)ArrayString.get(num);
     
     String temp2 = temp.replaceAll("%d", HeroicDeath.nameColor + dc.getDefender() + HeroicDeath.messageColor);
     String temp3 = temp2.replaceAll("%a", HeroicDeath.nameColor + attackerName + HeroicDeath.messageColor);
     temp = temp3.replaceAll("%i", HeroicDeath.itemColor + murderWeapon + HeroicDeath.messageColor);

     return temp;
   }

   String temp = (String)ArrayString.get(0);

   String temp2 = temp.replaceAll("%d", HeroicDeath.nameColor + dc.getDefender() + HeroicDeath.messageColor);
   String temp3 = temp2.replaceAll("%a", HeroicDeath.nameColor + attackerName + HeroicDeath.messageColor);
   temp = temp3.replaceAll("%i", HeroicDeath.itemColor + murderWeapon + HeroicDeath.messageColor);
   return temp;
 }
 
 public MaterialData getMurderWeapon(Player Attacker) {
	 ItemStack item = Attacker.getItemInHand();
	 int typeID = item.getTypeId();
	 Short mData = item.getDurability();
	 Byte matData = 0;
	 if (mData < 256)
		 matData = mData.byteValue();
	 MaterialData mItem = new MaterialData(typeID, matData);
	 return mItem;
 }
 
 public String getAttackerName(Entity damager) {
	 String attackerName = plugin.mobUnknown;
	 if (damager instanceof Player) {
		 Player attacker = (Player)damager;
		 attackerName = attacker.getDisplayName();
	 } else if (damager instanceof PigZombie) {
		 attackerName = plugin.mobPigZombie;
	 } else if (damager instanceof Giant) {
		 attackerName = plugin.mobGiant;
	 } else if (damager instanceof Zombie) {
		 attackerName = plugin.mobZombie;
	 } else if (damager instanceof Skeleton) {
		 attackerName = plugin.mobSkeleton;
	 } else if (damager instanceof Spider) {
		 attackerName = plugin.mobSpider;
	 } else if (damager instanceof Creeper) {
		 attackerName = plugin.mobCreeper;
	 } else if (damager instanceof Ghast) {
		 attackerName = plugin.mobGhast;
	 } else if (damager instanceof Slime) {
		 attackerName = plugin.mobSlime;
	 } else if (damager instanceof Wolf) {
		 attackerName = plugin.mobWolf;
	 } else {
		 attackerName = plugin.mobMonster;
	 }
	 return attackerName;
 }
 
 public void onEntityDeath(EntityDeathEvent event) {
	 Player player;
	 if (!(event.getEntity() instanceof Player))
		 return;
	 else
		 player = (Player)event.getEntity();
	 String name = player.getName();
	 DeathCertificate dc = deathRecords.remove(name);
	 if (dc == null)
		 dc = new DeathCertificate(player);
	 String killString = dc.getMessage();
	 if (killString == null) {
		 killString = getMessage(HeroicDeath.DeathMessages.OtherMessages, dc);
	 	 dc.setMessage(killString);
	 }
	 HeroicDeathEvent hde = new HeroicDeathEvent(dc);
	 plugin.getServer().getPluginManager().callEvent(hde);
	 if(!plugin.getEventsOnly()){
		plugin.getServer().broadcastMessage(HeroicDeath.messageColor + dc.getMessage() + " ");
	 }
	 HeroicDeath.log.info("[HeroicDeath]" + dc.getMessage().replaceAll("(?i)\u00A7[0-F]", ""));
	 plugin.recordDeath(dc);
 }
 
 public void onEntityDamage(EntityDamageEvent event)
 {
	 if (event.isCancelled()) {
		 return;
	 }
	 Player player;
	 if (!(event.getEntity() instanceof Player)) {
		 return;
	 } else {
		 try {
			 player = (Player)event.getEntity();
		 } catch (ClassCastException e)
		 {
			 HeroicDeath.log.severe("Cannot cast entity as player: " + e);
			 return;
		 }
	 }
	 String name = player.getName();
	 int damage = event.getDamage();
	 int oldHealth = player.getHealth();
	 int newHealth = oldHealth - damage;
	 HeroicDeath.debug("Player damaged: " + name + " [" + oldHealth + "-" + damage + "=" + newHealth + "] Cause: " + event.getCause().toString());	 
	 if (newHealth <= 0) {
		 String killString = name + " died.";
		 DeathCertificate dc = new DeathCertificate(player, event.getCause());
		 Entity damager = null;
		 Block damageBlock = null;
		 String blockName = null;
		 if (event instanceof EntityDamageByProjectileEvent) {
			 EntityDamageByProjectileEvent subEvent = (EntityDamageByProjectileEvent)event;
			 damager = subEvent.getDamager();
		 } else if (event instanceof EntityDamageByEntityEvent) {
			 EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent)event;
			 damager = subEvent.getDamager();
		 } else if (event instanceof EntityDamageByBlockEvent) {
			 EntityDamageByBlockEvent subEvent = (EntityDamageByBlockEvent)event;
			 damageBlock = subEvent.getDamager();
			 try {
				 blockName = damageBlock.getType().toString();
			 } catch (NullPointerException e) {
				 blockName = "Unknown";
			 }
		 }
		 switch (event.getCause()) {
		 	case ENTITY_ATTACK:
		 		 if (damager == null) {
					 dc.setAttacker("Dispenser");
					 killString = getMessage(HeroicDeath.DeathMessages.DispenserMessages, dc);
				 } else if (damager instanceof Player) {
					 Player attacker = (Player)damager;
					 dc.setAttacker(attacker.getDisplayName());
					 dc.setMurderWeapon(getMurderWeapon(attacker));
					 killString = getMessage(HeroicDeath.DeathMessages.PVPMessages, dc);
				 } else {
					 dc.setAttacker(getAttackerName(damager));
					 if (dc.getAttacker().equalsIgnoreCase(plugin.mobCreeper) && !HeroicDeath.DeathMessages.CreeperExplosionMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.CreeperExplosionMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobGhast) && !HeroicDeath.DeathMessages.GhastMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.GhastMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobSlime) && !HeroicDeath.DeathMessages.SlimeMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.SlimeMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobZombie) && !HeroicDeath.DeathMessages.ZombieMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.ZombieMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobPigZombie) && !HeroicDeath.DeathMessages.PigZombieMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.PigZombieMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobSpider) && !HeroicDeath.DeathMessages.SpiderMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.SpiderMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobSkeleton) && !HeroicDeath.DeathMessages.SkeletonMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.SkeletonMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobGiant) && !HeroicDeath.DeathMessages.GiantMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.GiantMessages, dc);
					 else if (dc.getAttacker().equalsIgnoreCase(plugin.mobWolf) && !HeroicDeath.DeathMessages.WolfMessages.isEmpty())
						 killString = getMessage(HeroicDeath.DeathMessages.WolfMessages, dc);
					 else
						 killString = getMessage(HeroicDeath.DeathMessages.MonsterMessages, dc);
				 }
		 		break;
		 	case BLOCK_EXPLOSION:
		 		killString = getMessage(HeroicDeath.DeathMessages.ExplosionMessages, dc);
		 		break;
		 	case ENTITY_EXPLOSION:
		 		if (damager instanceof TNTPrimed)
		 			killString = getMessage(HeroicDeath.DeathMessages.ExplosionMessages, dc);
		 		else if (damager instanceof Fireball) {
		 			dc.setAttacker(plugin.mobGhast);
		 			killString = getMessage(HeroicDeath.DeathMessages.GhastMessages, dc);
		 		}
		 		else {
		 			dc.setAttacker(plugin.mobCreeper);
		 			killString = getMessage(HeroicDeath.DeathMessages.CreeperExplosionMessages, dc);
		 		}
		 		break;
		 	case CONTACT:
				 dc.setAttacker(blockName);
				 if (blockName != "CACTUS")
					 HeroicDeath.log.info(name + "was damaged by non-cactus block: " + blockName);
				 killString = getMessage(HeroicDeath.DeathMessages.CactusMessages, dc);
				 break;
			case FALL:
				killString = getMessage(HeroicDeath.DeathMessages.FallMessages, dc);
				break;
			case FIRE_TICK:
			case FIRE:
			  killString = getMessage(HeroicDeath.DeathMessages.FireMessages, dc);
			  break;
			case DROWNING:
				killString = getMessage(HeroicDeath.DeathMessages.DrownMessages, dc);
				break;
			case LAVA:
				killString = getMessage(HeroicDeath.DeathMessages.LavaMessages, dc);
				break;
			case SUFFOCATION:
				killString = getMessage(HeroicDeath.DeathMessages.SuffocationMessages, dc);
				break;
			case VOID:
				killString = getMessage(HeroicDeath.DeathMessages.VoidMessages, dc);
				break;
			case LIGHTNING:
				killString = getMessage(HeroicDeath.DeathMessages.LightningMessages, dc);
				break;
			default:
			{
				killString = getMessage(HeroicDeath.DeathMessages.OtherMessages, dc);
			}
		 }
		 dc.setMessage(killString);
		 deathRecords.put(name, dc);
	 }
	 
   return;
 }
}