package com.herocraftonline.squallseed31.heroicdeath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class HeroicDeathMessages
{
  public ArrayList<String> DrownMessages = new ArrayList<String>();
  public ArrayList<String> CactusMessages = new ArrayList<String>();
  public ArrayList<String> FireMessages = new ArrayList<String>();
  public ArrayList<String> ExplosionMessages = new ArrayList<String>();
  public ArrayList<String> CreeperExplosionMessages = new ArrayList<String>();
  public ArrayList<String> FallMessages = new ArrayList<String>();
  public ArrayList<String> PVPMessages = new ArrayList<String>();
  public ArrayList<String> VoidMessages = new ArrayList<String>();
  public ArrayList<String> MonsterMessages = new ArrayList<String>();
  public ArrayList<String> GhastMessages = new ArrayList<String>();
  public ArrayList<String> SlimeMessages = new ArrayList<String>();
  public ArrayList<String> ZombieMessages = new ArrayList<String>();
  public ArrayList<String> PigZombieMessages = new ArrayList<String>();
  public ArrayList<String> SpiderMessages = new ArrayList<String>();
  public ArrayList<String> SkeletonMessages = new ArrayList<String>();
  public ArrayList<String> GiantMessages = new ArrayList<String>();
  public ArrayList<String> WolfMessages = new ArrayList<String>();
  public ArrayList<String> LavaMessages = new ArrayList<String>();
  public ArrayList<String> SuffocationMessages = new ArrayList<String>();
  public ArrayList<String> DispenserMessages = new ArrayList<String>();
  public ArrayList<String> LightningMessages = new ArrayList<String>();
  public ArrayList<String> SuicideMessages = new ArrayList<String>();
  public ArrayList<String> OtherMessages = new ArrayList<String>();
  private String location = "heroicdeath.messages";

  public void load(File dataFolder)
  {
    parseFile(dataFolder);
	/* Messages hard-coded for now.
	 * When I add my own persistence, these will be treated as defaults.
	 * Can add custom lines for each type of mob in the future, if this is desired behavior.
	 * Usage:
	 *  - %d for defender (victim)
	 *  - %a for attacker (PvP Player or Monster)
	 *  - %i for item wielded in PvP kill
	 *  - %w for world the death occurred in
	 */
    if (this.DrownMessages.size() == 0)
      this.DrownMessages.add("%d drowned");
    if (this.CactusMessages.size() == 0)
      this.CactusMessages.add("%d poked a cactus, but the cactus poked back.");
    if (this.FireMessages.size() == 0)
      this.FireMessages.add("%d burned to death");
    if (this.ExplosionMessages.size() == 0)
      this.ExplosionMessages.add("%d exploded");
    if (this.CreeperExplosionMessages.size() == 0)
      this.CreeperExplosionMessages.add("%d was creeper bombed");
    if (this.FallMessages.size() == 0)
      this.FallMessages.add("%d fell to their death");
    if (this.PVPMessages.size() == 0)
      this.PVPMessages.add("%a killed %d wielding %i");
    if (this.VoidMessages.size() == 0)
      this.VoidMessages.add("%d fell into the Gap");
    if (this.MonsterMessages.size() == 0)
      this.MonsterMessages.add("%d was killed by an angry %a");
    if (this.LavaMessages.size() == 0)
      this.LavaMessages.add("%d was killed by lava");
    if (this.SuffocationMessages.size() == 0)
    	this.SuffocationMessages.add("%d suffocated.");
    if (this.DispenserMessages.size() == 0)
    	this.DispenserMessages.add("%d got shot by a dispenser.");
    if (this.LightningMessages.size() == 0)
    	this.LightningMessages.add("%d got electrocuted.");
    if (this.SuicideMessages.size() == 0)
    	this.SuicideMessages.add("%d committed suicide.");
    if (this.OtherMessages.size() == 0)
      this.OtherMessages.add("%d died from unknown causes");
  }

  private void parseFile(File dataFolder)
  {
    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dataFolder, this.location))));
      HeroicDeathMessages.ParseType currentParse = HeroicDeathMessages.ParseType.NONE;
      String thisLine;
      while ((thisLine = reader.readLine()) != null)
      {
        if ((thisLine.contains("#")) || (thisLine.equals("")))
          continue;
        if (thisLine.toLowerCase().equals(":drown"))
          currentParse = HeroicDeathMessages.ParseType.Drown;
        else if (thisLine.toLowerCase().equals(":cactus"))
          currentParse = HeroicDeathMessages.ParseType.Cactus;
        else if (thisLine.toLowerCase().equals(":fire"))
          currentParse = HeroicDeathMessages.ParseType.Fire;
        else if (thisLine.toLowerCase().equals(":explosion"))
          currentParse = HeroicDeathMessages.ParseType.Explosion;
        else if (thisLine.toLowerCase().equals(":creeper"))
          currentParse = HeroicDeathMessages.ParseType.Creeper;
        else if (thisLine.toLowerCase().equals(":fall"))
          currentParse = HeroicDeathMessages.ParseType.Fall;
        else if (thisLine.toLowerCase().equals(":pvp"))
          currentParse = HeroicDeathMessages.ParseType.PVP;
        else if (thisLine.toLowerCase().equals(":void"))
          currentParse = HeroicDeathMessages.ParseType.Void;
        else if (thisLine.toLowerCase().equals(":monsters"))
          currentParse = HeroicDeathMessages.ParseType.Monster;
        else if (thisLine.toLowerCase().equals(":ghast"))
            currentParse = HeroicDeathMessages.ParseType.Ghast;
        else if (thisLine.toLowerCase().equals(":slime"))
            currentParse = HeroicDeathMessages.ParseType.Slime;
        else if (thisLine.toLowerCase().equals(":zombie"))
            currentParse = HeroicDeathMessages.ParseType.Zombie;
        else if (thisLine.toLowerCase().equals(":pigzombie"))
            currentParse = HeroicDeathMessages.ParseType.PigZombie;
        else if (thisLine.toLowerCase().equals(":spider"))
            currentParse = HeroicDeathMessages.ParseType.Spider;
        else if (thisLine.toLowerCase().equals(":skeleton"))
            currentParse = HeroicDeathMessages.ParseType.Skeleton;
        else if (thisLine.toLowerCase().equals(":giant"))
            currentParse = HeroicDeathMessages.ParseType.Giant;
        else if (thisLine.toLowerCase().equals(":wolf"))
        	currentParse = HeroicDeathMessages.ParseType.Wolf;
        else if (thisLine.toLowerCase().equals(":lava"))
          currentParse = HeroicDeathMessages.ParseType.Lava;
        else if (thisLine.toLowerCase().equals(":other"))
          currentParse = HeroicDeathMessages.ParseType.Other;
        else if (thisLine.toLowerCase().equals(":dispenser"))
        	currentParse = HeroicDeathMessages.ParseType.Dispenser;
        else if (thisLine.toLowerCase().equals(":lightning"))
        	currentParse = HeroicDeathMessages.ParseType.Lightning;
        else if (thisLine.toLowerCase().equals(":suicide"))
        	currentParse = HeroicDeathMessages.ParseType.Suicide;
        else if (thisLine.toLowerCase().equals(":suffocation")) {
          currentParse = HeroicDeathMessages.ParseType.Suffocation;
        }
        else {
          switch (currentParse)
          {
          case NONE:
            break;
          case Drown:
            this.DrownMessages.add(thisLine);
            break;
          case Cactus:
            this.CactusMessages.add(thisLine);
            break;
          case Fire:
            this.FireMessages.add(thisLine);
            break;
          case Explosion:
            this.ExplosionMessages.add(thisLine);
            break;
          case Creeper:
            this.CreeperExplosionMessages.add(thisLine);
            break;
          case Fall:
            this.FallMessages.add(thisLine);
            break;
          case PVP:
            this.PVPMessages.add(thisLine);
            break;
          case Void:
        	this.VoidMessages.add(thisLine);
        	break;
          case Monster:
            this.MonsterMessages.add(thisLine);
            break;
          case Lava:
            this.LavaMessages.add(thisLine);
            break;
          case Other:
            this.OtherMessages.add(thisLine);
            break;
          case Ghast:
        	this.GhastMessages.add(thisLine);
        	break;
          case Wolf:
        	this.WolfMessages.add(thisLine);
        	break;
          case Slime:
        	  this.SlimeMessages.add(thisLine);
        	  break;
          case Zombie:
        	  this.ZombieMessages.add(thisLine);
        	  break;
          case PigZombie:
        	  this.PigZombieMessages.add(thisLine);
        	  break;
          case Spider:
        	  this.SpiderMessages.add(thisLine);
        	  break;
          case Skeleton:
        	  this.SkeletonMessages.add(thisLine);
        	  break;
          case Giant:
        	  this.GiantMessages.add(thisLine);
        	  break;
          case Dispenser:
        	this.DispenserMessages.add(thisLine);
        	break;
          case Lightning:
        	this.LightningMessages.add(thisLine);
        	break;
          case Suicide:
        	this.SuicideMessages.add(thisLine);
        	break;
          case Suffocation:
        	this.SuffocationMessages.add(thisLine);
        	break;
          }
        }

      }

    }
    catch (IOException e)
    {
      writeFile(dataFolder);
    }
  }

  private void writeFile(File dataFolder)
  {
	File messageFile = new File(dataFolder, this.location);
	FileWriter writer = null;
    try
    {
      writer = new FileWriter(messageFile);
      writer.write("#This file contains custom messages for death events.\r\n");
      writer.write("#You can specify as many messages as you want (within reason) for each death type.\r\n");
      writer.write("#Just be sure each message is on a new line under the appropriate label.\r\n");
      writer.write("#When a player dies, a random message from the appropriate label is chosen.\r\n");
      writer.write("#Accepted variables:\r\n");
      writer.write("# - %d holds the name of the dead player.\r\n");
      writer.write("# - %a holds the name of the attacking entity (player or monster) for PVP and Monster deaths.\r\n");
      writer.write("# - %i holds the name of the attacking player's current item for PVP deaths.\r\n");
      writer.write("# - %w holds the world the death occurred in\r\n");
      writer.write("#Custom item names may be defined in the 'heroicdeath.items' file.\r\n");
      writer.write(":Drown\r\n");
      writer.write("%d drowned\r\n");
      writer.write("%d is swimming with the fishes\r\n");
      writer.write("%d took a long walk off a short pier\r\n");
      writer.write(":Cactus\r\n");
      writer.write("%d died to a cactus\r\n");
      writer.write("%d poked a cactus, but the cactus poked back\r\n");
      writer.write(":Fire\r\n");
      writer.write("%d burned to death\r\n");
      writer.write("%d forgot how to stop, drop and roll\r\n");
      writer.write(":Explosion\r\n");
      writer.write("%d apparently has an explosive personality\r\n");
      writer.write("%d exploded\r\n");
      writer.write(":Creeper\r\n");
      writer.write("%d was creeper bombed\r\n");
      writer.write("%d hugged a creeper\r\n");
      writer.write(":Fall\r\n");
      writer.write("%d fell to their death\r\n");
      writer.write("%d took a leap of faith\r\n");
      writer.write(":PVP\r\n");
      writer.write("%a killed %d wielding %i\r\n");
      writer.write(":Void\r\n");
      writer.write("%d fell into the Gap\r\n");
      writer.write(":Monsters\r\n");
      writer.write("%d was killed by an angry %a\r\n");
      writer.write(":Lava\r\n");
      writer.write("%d was killed by lava\r\n");
      writer.write("%d became obsidian\r\n");
      writer.write("%d took a bath in a lake of fire\r\n");
      writer.write(":Other\r\n");
      writer.write("%d died from unknown causes\r\n");
      writer.write("%d was killed by Herobrine\r\n");
      writer.write(":Suffocation\r\n");
      writer.write("%d suffocated\r\n");
      writer.write(":Dispenser\r\n");
      writer.write("%d did not need a dispenser there.\r\n");
      writer.write("%d got shot by a dispenser.\r\n");
      writer.write(":Lightning\r\n");
      writer.write("%d got electrocuted.\r\n");
      writer.write("%d has an electrifying personality.\r\n");
      writer.write("%d made a suitable ground.\r\n");
      writer.write(":Suicide\r\n");
      writer.write("%d committed suicide.");
      writer.write("%d decided to end it all.");
	} catch (Exception e) {
		  HeroicDeath.log.info("Exception while creating " + this.location + " " + e.toString());
	  if (writer != null)
		  try {
		    writer.close();
		  } catch (IOException e2) {
			  HeroicDeath.log.info("Exception while closing writer for " + this.location + " " + e2.toString());
		  }
	  }
	  finally
	  {
	    if (writer != null) {
	      try {
	        writer.close();
	      } catch (IOException e) {
	    	  HeroicDeath.log.info("Exception while closing writer for " + this.location + " " + e.toString());
	      }
	    }
	  }
      HeroicDeath.log.info("Default message file created for HeroicDeath.");
    this.DrownMessages.add("%d drowned");
    this.CactusMessages.add("%d poked a cactus, but the cactus poked back.");
    this.FireMessages.add("%d burned to death");
    this.ExplosionMessages.add("%d exploded");
    this.CreeperExplosionMessages.add("%d was creeper bombed");
    this.FallMessages.add("%d fell to their death");
    this.PVPMessages.add("%a killed %d wielding %i");
    this.VoidMessages.add("%d fell into the Gap");
    this.MonsterMessages.add("%d was killed by an angry %a");
    this.LavaMessages.add("%d was killed by lava");
    this.OtherMessages.add("%d died from unknown causes");
    this.SuffocationMessages.add("%d suffocated.");
    this.DispenserMessages.add("%d got shot by a dispenser.");
    this.LightningMessages.add("%d got electrocuted.");
    this.SuicideMessages.add("%d committed suicide.");
  }

  public static enum ParseType
  {
    NONE, Drown, Cactus, Fire, Creeper, Explosion, 
    Fall, PVP, Void, Monster, Ghast, Slime, Zombie, PigZombie, 
    Spider, Skeleton, Giant, Wolf, Lava, Other, Suffocation, Dispenser, Lightning, Suicide;
  }
}