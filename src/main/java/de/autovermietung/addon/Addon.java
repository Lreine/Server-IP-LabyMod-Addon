package de.autovermietung.addon;/*

    
    Projekt: LabyMod Addon
    Package: de.autovermietung.addon
    Klasse: Addon
    Addon erstellt am 15.10.2020 um 14:47
    Diese Klasse wurde von Autovermietung (JavaSucht.net) erstellt.
    
    
   */

import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;
import net.minecraftforge.event.terraingen.OreGenEvent;

import java.util.List;

public class Addon extends LabyModAddon {

    public static String Prefix = "§8[§aServerIP§8] ";

    private boolean enabled = true;

    @Override
    public void onEnable() {
        System.out.println("[INFO] Addon 'Addon' wurde gestartet.");
        this.getApi().registerForgeListener(this);

        this.getApi().getEventManager().registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(final ServerData serverData) {
                if (LabyMod.getInstance().getLabyPlay() != null){
                    if(enabled){
                        LabyMod.getInstance().displayMessageInChat(Prefix + "§7Du bist auf §a" + serverData.getIp() + "§7 gejoint.");
                    }
                }
            }
        });

        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String s) {
                    if(s.startsWith("-dev")){
                        LabyMod.getInstance().displayMessageInChat(Prefix + "§7Server-IP Addon by Autovermietung (JavaSucht.net)");
                        LabyMod.getInstance().displayMessageInChat(Prefix + "§cBei Bugs etc. bitte auf dem JavaSucht.net TeamSpeak³ Server melden.");
                        return true;
                    }
                return false;
            }
        });
    }

    @Override
    public void loadConfig() {
        this.enabled = !this.getConfig().has("enabled") || this.getConfig().get("enabled").getAsBoolean();
    }

    @Override
    protected void fillSettings(final List<SettingsElement> list) {
        final BooleanElement booleanElement = new BooleanElement("Server-IP beim Joinen", new ControlElement.IconData(Material.EMERALD), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean enabled) {
                Addon.this.enabled = enabled;

                Addon.this.getConfig().addProperty("enabled", enabled);
                Addon.this.saveConfig();
            }
        }, this.enabled);
        list.add(booleanElement);
    }
}
