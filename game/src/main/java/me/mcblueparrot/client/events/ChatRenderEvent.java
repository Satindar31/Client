package me.mcblueparrot.client.events;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiNewChat;

@RequiredArgsConstructor
public class ChatRenderEvent {

    public final GuiNewChat chat;
    public final int updateCounter;
    public boolean cancelled;

}
