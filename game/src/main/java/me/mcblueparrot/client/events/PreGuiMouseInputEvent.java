package me.mcblueparrot.client.events;

import me.mcblueparrot.client.annotation.semantic.ForgeCompat;

public class PreGuiMouseInputEvent {

    public boolean cancelled;

    @Deprecated
    @ForgeCompat
    public void setCanceled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
