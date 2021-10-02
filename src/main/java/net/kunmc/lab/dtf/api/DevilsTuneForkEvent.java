package net.kunmc.lab.dtf.api;

import net.minecraftforge.eventbus.api.Event;

public class DevilsTuneForkEvent extends Event {
    private final DevilsTuneForkAPI api;
    private boolean enable;

    public DevilsTuneForkEvent(DevilsTuneForkAPI api) {
        this.api = api;
    }

    public DevilsTuneForkAPI getApi() {
        return api;
    }

    public void setEnable() {
        this.enable = true;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
