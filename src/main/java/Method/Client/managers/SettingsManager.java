package Method.Client.managers;

import Method.Client.module.*;
import java.util.stream.*;
import java.util.*;

public class SettingsManager
{
    private final ArrayList<Setting> settings;
    
    public SettingsManager() {
        this.settings = new ArrayList<Setting>();
    }
    
    public Setting add(final Setting in) {
        this.settings.add(in);
        return in;
    }
    
    public ArrayList<Setting> getSettings() {
        return this.settings;
    }
    
    public ArrayList<Setting> getSettingsByMod(final Module mod) {
        return this.settings.stream().filter(s -> s.getParentMod().equals(mod)).collect((Collector<? super Object, ?, ArrayList<Setting>>)Collectors.toList());
    }
    
    public void setSettingsByMod(final Module mod, final ArrayList<Setting> change) {
        for (final Setting s : this.settings) {
            if (s.getParentMod().equals(mod)) {
                for (final Setting Inputsetting : change) {
                    if (s.getName().equalsIgnoreCase(Inputsetting.getName())) {
                        s.setall(Inputsetting);
                    }
                }
            }
        }
    }
    
    public Setting getSettingByName(final String name) {
        for (final Setting set : this.getSettings()) {
            if (set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        System.err.println("[FutureX] Error Setting NOT found: '" + name + "'!");
        return null;
    }
}
