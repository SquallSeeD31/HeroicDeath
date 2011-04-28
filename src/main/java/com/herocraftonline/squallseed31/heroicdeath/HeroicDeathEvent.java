package com.herocraftonline.squallseed31.heroicdeath;

import org.bukkit.event.Event;

public class HeroicDeathEvent extends Event {
	
	private final DeathCertificate dc;
	
	public HeroicDeathEvent(DeathCertificate dc)
	{
		super("HeroicDeathEvent");
		this.dc = dc;
	}

	public DeathCertificate getDeathCertificate() {
		return dc;
	}
}
