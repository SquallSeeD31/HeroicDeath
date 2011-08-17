package com.herocraftonline.squallseed31.heroicdeath;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class HeroicDeathEvent extends Event implements Cancellable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -28983074208940338L;
	private DeathCertificate dc;
	private boolean cancel;
	
	public HeroicDeathEvent(DeathCertificate dc)
	{
		super("HeroicDeathEvent");
		this.dc = dc;
		this.cancel = false;
	}

	public DeathCertificate getDeathCertificate() {
		return dc;
	}

	public void setDeathCertificate(DeathCertificate dc) {
		this.dc = dc;
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
