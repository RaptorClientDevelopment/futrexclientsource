package Method.Client.module.Profiles;

import Method.Client.module.*;
import Method.Client.*;
import Method.Client.managers.*;
import java.util.*;

public class Profiletem extends Module
{
    public String name;
    
    public Profiletem(final String name) {
        super(name, 0, Category.PROFILES, name);
        this.name = name;
    }
    
    @Override
    public void setsave() {
        this.StoredModules.clear();
        this.StoredModules.addAll(ModuleManager.toggledModules);
        this.StoredModules.removeIf(module -> module.getCategory().equals(Category.PROFILES) || module.getCategory().equals(Category.ONSCREEN));
        this.StoredSettings.clear();
        for (final Module storedModule : this.StoredModules) {
            for (final Setting setting : Main.setmgr.getSettingsByMod(storedModule)) {
                this.StoredSettings.add(new Setting(setting));
            }
        }
        FileManager.savePROFILES();
    }
    
    @Override
    public void setdelete() {
        this.StoredModules.clear();
        this.StoredSettings.clear();
    }
    
    @Override
    public void onEnable() {
        final ArrayList<Module> remove = new ArrayList<Module>();
        final ArrayList<Module> list;
        ModuleManager.toggledModules.forEach(module -> {
            if (!module.getCategory().equals(Category.PROFILES) && !module.getCategory().equals(Category.ONSCREEN)) {
                list.add(module);
            }
            return;
        });
        remove.forEach(module -> {
            if (!module.getCategory().equals(Category.PROFILES) && !module.getCategory().equals(Category.ONSCREEN)) {
                module.setToggled(false);
            }
            return;
        });
        ModuleManager.toggledModules.removeAll(remove);
        for (final Module N : this.StoredModules) {
            if (!N.getCategory().equals(Category.PROFILES) && !N.getCategory().equals(Category.ONSCREEN)) {
                N.toggle();
                final ArrayList<Setting> change = new ArrayList<Setting>();
                for (final Setting setting : this.StoredSettings) {
                    if (setting.getParentMod().equals(N)) {
                        change.add(setting);
                    }
                }
                Main.setmgr.setSettingsByMod(N, change);
            }
        }
        this.toggle();
    }
}
