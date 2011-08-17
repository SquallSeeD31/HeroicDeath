package com.herocraftonline.squallseed31.heroicdeath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.bukkit.material.MaterialData;



//Again, this persistence format is hard-coded, and was taken
//from the previous developer of DeathNotify
//will be replaced with custom work later.
public class HeroicDeathItems
{
  protected Map<MaterialData, String> HDItems = new HashMap<MaterialData, String>();

  private Object itemLock = new Object();
  private String location = "heroicdeath.items";
  
  public void load(File dataFolder)
  {
	  File itemFile = new File(dataFolder, this.location);
    if (!itemFile.exists()) {
      FileWriter writer = null;
      try {
        writer = new FileWriter(itemFile);
        writer.write("#This file contains custom names for the item a PvP killer is holding\r\n");
        writer.write("#The names here will be replaced in the %i marker for PvP deaths\r\n");
        writer.write("#File Usage:\r\n");
        writer.write("#NAME:ID:(optional)DATA\r\n");
        writer.write("#The DATA parameter is optional, and refers to materials with multiple states\r\n");
        writer.write("#Such as colored dyes and wool.\r\n");
        writer.write("#Item IDs and data values may be found on the Minecraft Wiki\r\n");
        writer.write("#If a mapping is not provided for an item in this file, a default name will be used automatically.\r\n");
        writer.write("fists:0:-0x1\r\n");
        writer.write("stone:1\r\n");
        writer.write("grass:2\r\n");
        writer.write("dirt:3\r\n");
        writer.write("cobblestone:4\r\n");
        writer.write("wood:5\r\n");
        writer.write("sapling:6:0x0\r\n");
        writer.write("oak sapling:6:0x0\r\n");
        writer.write("spruce sapling:6:0x1\r\n");
        writer.write("pine sapling:6:0x1\r\n");
        writer.write("redwood sapling:6:0x1\r\n");
        writer.write("birch sapling:6:0x2\r\n");
        writer.write("bedrock:7\r\n");
        writer.write("water:8\r\n");
        writer.write("stationary water:9\r\n");
        writer.write("lava:10\r\n");
        writer.write("stationary lava:11\r\n");
        writer.write("sand:12\r\n");
        writer.write("gravel:13\r\n");
        writer.write("gold ore:14\r\n");
        writer.write("iron ore:15\r\n");
        writer.write("coal ore:16\r\n");
        writer.write("log:17:0x0\r\n");
        writer.write("oak:17:0x0\r\n");
        writer.write("spruce:17:0x1\r\n");
        writer.write("pine:17:0x1\r\n");
        writer.write("redwood:17:0x1\r\n");
        writer.write("birch:17:0x2\r\n");
        writer.write("leaves:18\r\n");
        writer.write("sponge:19\r\n");
        writer.write("glass:20\r\n");
        writer.write("lapis ore:21\r\n");
        writer.write("lapis block:22\r\n");
        writer.write("dispenser:23\r\n");
        writer.write("sandstone:24\r\n");
        writer.write("note block:25\r\n");
        writer.write("bed block:26\r\n");
        writer.write("powered rail:27\r\n");
        writer.write("detector rail:28\r\n");
        writer.write("sticky piston:29\r\n");
        writer.write("web:30\r\n");
        writer.write("tall grass:31\r\n");
        writer.write("dead shrub:32\r\n");
        writer.write("piston:33\r\n");
        writer.write("extended piston:34\r\n");
        writer.write("wool:35:0x0\r\n");
        writer.write("white wool:35:0x0\r\n");
        writer.write("orange wool:35:0x1\r\n");
        writer.write("magenta wool:35:0x2\r\n");
        writer.write("light blue wool:35:0x3\r\n");
        writer.write("yellow wool:35:0x4\r\n");
        writer.write("light green wool:35:0x5\r\n");
        writer.write("pink wool:35:0x6\r\n");
        writer.write("grey wool:35:0x7\r\n");
        writer.write("gray wool:35:0x7\r\n");
        writer.write("light gray wool:35:0x8\r\n");
        writer.write("cyan wool:35:0x9\r\n");
        writer.write("purple wool:35:0xa\r\n");
        writer.write("blue wool:35:0xb\r\n");
        writer.write("brown wool:35:0xc\r\n");
        writer.write("dark green wool:35:0xd\r\n");
        writer.write("red wool:35:0xe\r\n");
        writer.write("black wool:35:0xf\r\n");
        writer.write("moving block:36\r\n");
        writer.write("flower:37\r\n");
        writer.write("yellow flower:37\r\n");
        writer.write("rose:38\r\n");
        writer.write("red flower:38\r\n");
        writer.write("red rose:38\r\n");
        writer.write("brown mushroom:39\r\n");
        writer.write("red mushroom:40\r\n");
        writer.write("gold block:41\r\n");
        writer.write("iron block:42\r\n");
        writer.write("stone double slab:43:0x0\r\n");
        writer.write("sandstone double slab:43:0x1\r\n");
        writer.write("wooden double slab:43:0x2\r\n");
        writer.write("cobblestone double slab:43:0x3\r\n");
        writer.write("stone slab:44:0x0\r\n");
        writer.write("sandstone slab:44:0x1\r\n");
        writer.write("wooden slab:44:0x2\r\n");
        writer.write("cobblestone slab:44:0x3\r\n");
        writer.write("brick block:45\r\n");
        writer.write("TNT:46\r\n");
        writer.write("bookshelf:47\r\n");
        writer.write("mossy cobblestone:48\r\n");
        writer.write("obsidian:49\r\n");
        writer.write("torch:50\r\n");
        writer.write("fire:51\r\n");
        writer.write("mob spawner:52\r\n");
        writer.write("wood stairs:53\r\n");
        writer.write("chest:54\r\n");
        writer.write("redstone wire:55\r\n");
        writer.write("diamond ore:56\r\n");
        writer.write("diamond block:57\r\n");
        writer.write("workbench:58\r\n");
        writer.write("crops:59\r\n");
        writer.write("soil:60\r\n");
        writer.write("furnace:61\r\n");
        writer.write("burning furnace:62\r\n");
        writer.write("sign post:63\r\n");
        writer.write("wooden door:64\r\n");
        writer.write("ladder:65\r\n");
        writer.write("tracks:66\r\n");
        writer.write("cobblestone stairs:67\r\n");
        writer.write("wall sign:68\r\n");
        writer.write("lever:69\r\n");
        writer.write("stone plate:70\r\n");
        writer.write("iron door block:71\r\n");
        writer.write("wood plate:72\r\n");
        writer.write("redstone ore:73\r\n");
        writer.write("glowing redstone ore:74\r\n");
        writer.write("redstone torch:75\r\n");
        writer.write("redstone torch:76\r\n");
        writer.write("stone button:77\r\n");
        writer.write("snow:78\r\n");
        writer.write("ice:79\r\n");
        writer.write("snow block:80\r\n");
        writer.write("cactus:81\r\n");
        writer.write("clay block:82\r\n");
        writer.write("sugar cane block:83\r\n");
        writer.write("jukebox:84\r\n");
        writer.write("fence:85\r\n");
        writer.write("pumpkin:86\r\n");
        writer.write("netherrack:87\r\n");
        writer.write("soul sand:88\r\n");
        writer.write("glowstone:89\r\n");
        writer.write("portal:90\r\n");
        writer.write("jack o lantern:91\r\n");
        writer.write("off redstone repeater:93\r\n");
        writer.write("on redstone repeater:94\r\n");
        writer.write("locked chest:95\r\n");
        writer.write("trapdoor:96\r\n");
        writer.write("iron shovel:256\r\n");
        writer.write("iron pickaxe:257\r\n");
        writer.write("iron axe:258\r\n");
        writer.write("flint & steel:259\r\n");
        writer.write("apple:260\r\n");
        writer.write("bow & arrow:261\r\n");
        writer.write("arrow:262\r\n");
        writer.write("coal:263:0x0\r\n");
        writer.write("charcoal:263:0x1\r\n");
        writer.write("diamond:264\r\n");
        writer.write("iron bar:265\r\n");
        writer.write("gold bar:266\r\n");
        writer.write("iron sword:267\r\n");
        writer.write("wooden sword:268\r\n");
        writer.write("wooden shovel:269\r\n");
        writer.write("wooden pickaxe:270\r\n");
        writer.write("wooden pick:270\r\n");
        writer.write("wooden axe:271\r\n");
        writer.write("stone sword:272\r\n");
        writer.write("stone shovel:273\r\n");
        writer.write("stone pickaxe:274\r\n");
        writer.write("stone axe:275\r\n");
        writer.write("diamond sword:276\r\n");
        writer.write("diamond shovel:277\r\n");
        writer.write("diamond pickaxe:278\r\n");
        writer.write("diamond axe:279\r\n");
        writer.write("stick:280\r\n");
        writer.write("bowl:281\r\n");
        writer.write("soup bowl:282\r\n");
        writer.write("gold sword:283\r\n");
        writer.write("gold shovel:284\r\n");
        writer.write("gold pickaxe:285\r\n");
        writer.write("gold axe:286\r\n");
        writer.write("string:287\r\n");
        writer.write("feather:288\r\n");
        writer.write("sulfur:289\r\n");
        writer.write("wood hoe:290\r\n");
        writer.write("stone hoe:291\r\n");
        writer.write("iron hoe:292\r\n");
        writer.write("diamond hoe:293\r\n");
        writer.write("gold hoe:294\r\n");
        writer.write("seeds:295\r\n");
        writer.write("wheat:296\r\n");
        writer.write("bread:297\r\n");
        writer.write("leather helmet:298\r\n");
        writer.write("leather chestplate:299\r\n");
        writer.write("leather pants:300\r\n");
        writer.write("leather boots:301\r\n");
        writer.write("chainmail helmet:302\r\n");
        writer.write("chainmail chestplate:303\r\n");
        writer.write("chainmail pants:304\r\n");
        writer.write("chainmail boots:305\r\n");
        writer.write("iron helmet:306\r\n");
        writer.write("iron chestplate:307\r\n");
        writer.write("iron pants:308\r\n");
        writer.write("iron boots:309\r\n");
        writer.write("diamond helmet:310\r\n");
        writer.write("diamond chestplate:311\r\n");
        writer.write("diamond pants:312\r\n");
        writer.write("diamond boots:313\r\n");
        writer.write("gold helmet:314\r\n");
        writer.write("gold chestplate:315\r\n");
        writer.write("gold pants:316\r\n");
        writer.write("gold boots:317\r\n");
        writer.write("flint:318\r\n");
        writer.write("raw porkchop:319\r\n");
        writer.write("cooked pork:320\r\n");
        writer.write("painting:321\r\n");
        writer.write("golden apple:322\r\n");
        writer.write("sign:323\r\n");
        writer.write("wood door:324\r\n");
        writer.write("bucket:325\r\n");
        writer.write("water bucket:326\r\n");
        writer.write("lava bucket:327\r\n");
        writer.write("minecart:328\r\n");
        writer.write("saddle:329\r\n");
        writer.write("iron door:330\r\n");
        writer.write("redstone dust:331\r\n");
        writer.write("snowball:332\r\n");
        writer.write("boat:333\r\n");
        writer.write("leather:334\r\n");
        writer.write("milk bucket:335\r\n");
        writer.write("brick:336\r\n");
        writer.write("clay:337\r\n");
        writer.write("reed:338\r\n");
        writer.write("paper:339\r\n");
        writer.write("book:340\r\n");
        writer.write("slimeorb:341\r\n");
        writer.write("storage minecart:342\r\n");
        writer.write("powered minecart:343\r\n");
        writer.write("egg:344\r\n");
        writer.write("compass:345\r\n");
        writer.write("fishing rod:346\r\n");
        writer.write("watch:347\r\n");
        writer.write("lightstone dust:348\r\n");
        writer.write("light dust:348\r\n");
        writer.write("raw fish:349\r\n");
        writer.write("fish:349\r\n");
        writer.write("cooked fish:350\r\n");
        writer.write("ink sack:351:0x0\r\n");
        writer.write("rose red:351:0x1\r\n");
        writer.write("cactus green:351:0x2\r\n");
        writer.write("coco beans:351:0x3\r\n");
        writer.write("lapis lazuli:351:0x4\r\n");
        writer.write("purple dye:351:0x5\r\n");
        writer.write("cyan dye:351:0x6\r\n");
        writer.write("light gray dye:351:0x7\r\n");
        writer.write("gray dye:351:0x8\r\n");
        writer.write("pink dye:351:0x9\r\n");
        writer.write("lime dye:351:0xa\r\n");
        writer.write("dandelion yellow:351:0xb\r\n");
        writer.write("light blue dye:351:0xc\r\n");
        writer.write("magenta dye:351:0xd\r\n");
        writer.write("orange dye:351:0xe\r\n");
        writer.write("bone meal:351:0xf\r\n");
        writer.write("bone:352\r\n");
        writer.write("sugar:353\r\n");
        writer.write("cake:354\r\n");
        writer.write("bed:355\r\n");
        writer.write("redstone repeater:356\r\n");
        writer.write("cookie:357\r\n");
        writer.write("map:358\r\n");
        writer.write("shears:359\r\n");
        writer.write("gold record:2256\r\n");
        writer.write("green record:2257\r\n");
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
      HeroicDeath.log.info("Default item file created for HeroicDeath.");
    }
    
    synchronized (this.itemLock) {
      this.HDItems = new HashMap<MaterialData, String>();
      try {
        Scanner scanner = new Scanner(itemFile);
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();
          if (line.startsWith("#")) {
            continue;
          }
          if (line.equals("")) {
            continue;
          }
          String[] split = line.split(":");
          String name = split[0];
          int type = Integer.valueOf(split[1]);
          byte data = 0;
          if (split.length > 2) {
        	  try {
        		  data = Byte.decode(split[2]);
        	  } catch (NumberFormatException e) {
        		  HeroicDeath.log.info("Improper data value format (did you prefix it with 0x?): " + e.toString());
        		  data = 0;
        	  }
          }
          this.HDItems.put(new MaterialData(type, data), name);
        }
        scanner.close();
      } catch (Exception e) {
    	  HeroicDeath.log.info("Exception while reading " + this.location + " (Are you sure you formatted it correctly?) " + e.toString());
      }
    }
  }

  public String getItem(MaterialData item) {
	  int iType = item.getItemTypeId();
	  byte iData = item.getData();
	  String genericMatch = null;
    synchronized (this.itemLock) {
      for (Entry<MaterialData, String> entry : this.HDItems.entrySet()) {
    	  if (entry.getKey().getItemTypeId() == iType && entry.getKey().getData() == iData)
    		  return entry.getValue();
    	  if (entry.getKey().getItemTypeId() == iType && entry.getKey().getData() == 0)
    		  genericMatch = entry.getValue();
      }
    }
    return (genericMatch == null) ? friendlyName(item.getItemType().toString()) : genericMatch;
  }
  
  public String friendlyName(String rawName) {
	    String[] split = rawName.split("_");
	    String output = null;
	    int i = 0;
	    for (String word : split) {
	    	String first = ((Character)word.charAt(0)).toString();
	    	String rest = word.substring(1);
	    	if (i == 0) {
	    		output = first.toUpperCase() + rest.toLowerCase();
	    	} else {
		    	output += first.toUpperCase() + rest.toLowerCase();	    		
	    	}
	    	if (i < split.length)
	    		output += " ";
	    	i++;
	    }
	    return output;
  }
}