package Method.Client.managers;

import Method.Client.module.Profiles.*;
import Method.Client.module.*;
import Method.Client.*;
import Method.Client.utils.Screens.Custom.Search.*;
import Method.Client.clickgui.*;
import Method.Client.clickgui.component.*;
import java.util.*;
import Method.Client.utils.Screens.Custom.Xray.*;
import java.io.*;
import com.google.gson.*;
import Method.Client.utils.system.*;

public class FileManager
{
    private static final Gson gsonPretty;
    private static final JsonParser jsonParser;
    public static final File SaveDir;
    private static final File Mods;
    private static final File XRAYDATA;
    private static final File FRIENDS;
    private static final File ONSCREEN;
    private static final File SEARCH;
    private static final File PROFILES;
    
    public FileManager() {
        if (!FileManager.SaveDir.exists()) {
            FileManager.SaveDir.mkdir();
        }
        if (!FileManager.Mods.exists()) {
            SaveMods();
        }
        if (!FileManager.ONSCREEN.exists()) {
            saveframes();
        }
        else {
            Loadpos();
        }
        if (!FileManager.XRAYDATA.exists()) {
            saveXRayData();
        }
        else {
            loadXRayData();
        }
        if (!FileManager.PROFILES.exists()) {
            savePROFILES();
        }
        if (!FileManager.SEARCH.exists()) {
            saveSearchData();
        }
        else {
            loadSearchData();
        }
        if (!FileManager.FRIENDS.exists()) {
            saveFriends();
        }
        else {
            loadFriends();
        }
    }
    
    public static void loadPROFILES() {
        try {
            final BufferedReader loadJson = new BufferedReader(new FileReader(FileManager.PROFILES));
            final JsonObject moduleJason = (JsonObject)FileManager.jsonParser.parse((Reader)loadJson);
            loadJson.close();
            for (final Map.Entry<String, JsonElement> entry : moduleJason.entrySet()) {
                final Module m = new Profiletem(entry.getKey());
                ModuleManager.addModule(m);
                final JsonObject jsonMod = (JsonObject)entry.getValue();
                m.setKeys(jsonMod.get("key").getAsString());
                m.visible = jsonMod.get("Visible").getAsBoolean();
                final ArrayList<Module> Modstore = new ArrayList<Module>();
                for (final Module module : ModuleManager.modules) {
                    if (!module.getCategory().equals(Category.ONSCREEN) && !module.getCategory().equals(Category.PROFILES) && jsonMod.has(module.getName())) {
                        Modstore.add(module);
                    }
                }
                m.setStoredModules(Modstore);
                final ArrayList<Setting> Setstore = new ArrayList<Setting>();
                for (final Module module2 : Modstore) {
                    for (final Setting s : Main.setmgr.getSettingsByMod(module2)) {
                        if (s.getMode().equals("Slider")) {
                            s.setValDouble(jsonMod.get(s.getName()).getAsDouble());
                        }
                        if (s.getMode().equals("Check")) {
                            s.setValBoolean(jsonMod.get(s.getName()).getAsBoolean());
                        }
                        if (s.getMode().equals("Combo")) {
                            s.setValString(jsonMod.get(s.getName()).getAsString());
                        }
                        if (s.getMode().equals("Color")) {
                            s.setValDouble(jsonMod.get(s.getName() + "c").getAsDouble());
                            s.setsaturation((float)jsonMod.get(s.getName() + "s").getAsDouble());
                            s.setbrightness((float)jsonMod.get(s.getName() + "b").getAsDouble());
                            s.setAlpha((float)jsonMod.get(s.getName() + "a").getAsDouble());
                        }
                        Setstore.add(s);
                    }
                }
                m.setStoredSettings(Setstore);
            }
        }
        catch (NullPointerException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public static void savePROFILES() {
        try {
            final JsonObject json = new JsonObject();
            for (final Module m : ModuleManager.getModulesInCategory(Category.PROFILES)) {
                final JsonObject JsonMod = new JsonObject();
                JsonMod.addProperty("key", m.getKeys().toString());
                JsonMod.addProperty("Visible", m.visible);
                json.add(m.getName(), (JsonElement)JsonMod);
                for (final Setting s : m.getStoredSettings()) {
                    if (s.getMode().equals("Slider")) {
                        JsonMod.addProperty(s.getName(), (Number)s.getValDouble());
                    }
                    if (s.getMode().equals("Check")) {
                        JsonMod.addProperty(s.getName(), s.getValBoolean());
                    }
                    if (s.getMode().equals("Combo")) {
                        JsonMod.addProperty(s.getName(), s.getValString());
                    }
                    if (s.getMode().equals("Color")) {
                        JsonMod.addProperty(s.getName() + "c", (Number)s.getValDouble());
                        JsonMod.addProperty(s.getName() + "s", (Number)s.getSat());
                        JsonMod.addProperty(s.getName() + "b", (Number)s.getBri());
                        JsonMod.addProperty(s.getName() + "a", (Number)s.getAlpha());
                    }
                }
                for (final Module storedModule : m.getStoredModules()) {
                    JsonMod.addProperty(storedModule.getName(), "Toggled");
                }
            }
            final PrintWriter saveJson = new PrintWriter(new FileWriter(FileManager.PROFILES));
            saveJson.println(FileManager.gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (NullPointerException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public static void SaveMods() {
        try {
            final JsonObject json = new JsonObject();
            ModuleManager.getModules().forEach(module -> Save(json, module));
            final JsonObject JsonMod = new JsonObject();
            JsonMod.addProperty("PREFIX", CommandManager.cmdPrefix);
            json.add("PREFIX", (JsonElement)JsonMod);
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("VERSION", "0.0.1");
            json.add("VERSION", (JsonElement)jsonObject);
            final PrintWriter saveJson = new PrintWriter(new FileWriter(FileManager.Mods));
            saveJson.println(FileManager.gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (NullPointerException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    private static void Save(final JsonObject json, final Module m) {
        final JsonObject JsonMod = new JsonObject();
        JsonMod.addProperty("toggled", m.isToggled());
        JsonMod.addProperty("key", m.getKeys().toString());
        JsonMod.addProperty("Visible", m.visible);
        json.add(m.getName(), (JsonElement)JsonMod);
        for (final Setting s : Main.setmgr.getSettingsByMod(m)) {
            if (s.getMode().equals("Slider")) {
                JsonMod.addProperty(s.getName(), (Number)s.getValDouble());
            }
            if (s.getMode().equals("Check")) {
                JsonMod.addProperty(s.getName(), s.getValBoolean());
            }
            if (s.getMode().equals("Combo")) {
                JsonMod.addProperty(s.getName(), s.getValString());
            }
            if (s.getMode().equals("Color")) {
                JsonMod.addProperty(s.getName() + "c", (Number)s.getValDouble());
                JsonMod.addProperty(s.getName() + "s", (Number)s.getSat());
                JsonMod.addProperty(s.getName() + "b", (Number)s.getBri());
                JsonMod.addProperty(s.getName() + "a", (Number)s.getAlpha());
            }
        }
    }
    
    public static void LoadMods() {
        try {
            final BufferedReader loadJson = new BufferedReader(new FileReader(FileManager.Mods));
            final JsonObject moduleJason = (JsonObject)FileManager.jsonParser.parse((Reader)loadJson);
            loadJson.close();
            for (final Map.Entry<String, JsonElement> entry : moduleJason.entrySet()) {
                final Module mods = ModuleManager.getModuleByName(entry.getKey());
                if (entry.getKey().equals("PREFIX")) {
                    final JsonObject jsonMod = (JsonObject)entry.getValue();
                    CommandManager.cmdPrefix = jsonMod.get("PREFIX").getAsCharacter();
                }
                Load(entry, mods);
            }
        }
        catch (NullPointerException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    private static void Load(final Map.Entry<String, JsonElement> entry, final Module m) {
        if (m != null) {
            final JsonObject jsonMod = (JsonObject)entry.getValue();
            m.setKeys(jsonMod.get("key").getAsString());
            m.visible = jsonMod.get("Visible").getAsBoolean();
            for (final Setting s : Main.setmgr.getSettingsByMod(m)) {
                if (s.getMode().equals("Slider")) {
                    s.setValDouble(jsonMod.get(s.getName()).getAsDouble());
                }
                if (s.getMode().equals("Check")) {
                    s.setValBoolean(jsonMod.get(s.getName()).getAsBoolean());
                }
                if (s.getMode().equals("Combo")) {
                    s.setValString(jsonMod.get(s.getName()).getAsString());
                }
                if (s.getMode().equals("Color")) {
                    s.setValDouble(jsonMod.get(s.getName() + "c").getAsDouble());
                    s.setsaturation((float)jsonMod.get(s.getName() + "s").getAsDouble());
                    s.setbrightness((float)jsonMod.get(s.getName() + "b").getAsDouble());
                    s.setAlpha((float)jsonMod.get(s.getName() + "a").getAsDouble());
                }
            }
            if (jsonMod.get("toggled").getAsBoolean()) {
                ModuleManager.FileManagerLoader.add(m);
                m.setToggled(true);
            }
        }
    }
    
    public static void loadSearchData() {
        try {
            final BufferedReader loadJson = new BufferedReader(new FileReader(FileManager.SEARCH));
            final JsonArray json = (JsonArray)FileManager.jsonParser.parse((Reader)loadJson);
            loadJson.close();
            SearchGuiSettings.fromJson((JsonElement)json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveSearchData() {
        try {
            final JsonElement json = SearchGuiSettings.toJson();
            final PrintWriter saveJson = new PrintWriter(new FileWriter(FileManager.SEARCH));
            saveJson.println(FileManager.gsonPretty.toJson(json));
            saveJson.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void Loadpos() {
        try {
            final BufferedReader loadJson = new BufferedReader(new FileReader(FileManager.ONSCREEN));
            final JsonObject moduleJason = (JsonObject)FileManager.jsonParser.parse((Reader)loadJson);
            loadJson.close();
            for (final Map.Entry<String, JsonElement> entry : moduleJason.entrySet()) {
                final JsonObject jsonMod = (JsonObject)entry.getValue();
                for (final Frame frame : ClickGui.frames) {
                    if (entry.getKey().equals(frame.getName())) {
                        frame.setX(jsonMod.get("x").getAsInt());
                        frame.setY(jsonMod.get("y").getAsInt());
                        frame.setOpen(jsonMod.get("open").getAsBoolean());
                    }
                }
            }
        }
        catch (NullPointerException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public static void loadFriends() {
        final List<String> friends = read(FileManager.FRIENDS);
        for (final String name : friends) {
            FriendManager.addFriend(name);
        }
    }
    
    public static void saveXRayData() {
        try {
            final JsonElement json = XrayGuiSettings.toJson();
            final PrintWriter saveJson = new PrintWriter(new FileWriter(FileManager.XRAYDATA));
            saveJson.println(FileManager.gsonPretty.toJson(json));
            saveJson.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadXRayData() {
        try {
            final BufferedReader loadJson = new BufferedReader(new FileReader(FileManager.XRAYDATA));
            final JsonArray json = (JsonArray)FileManager.jsonParser.parse((Reader)loadJson);
            loadJson.close();
            XrayGuiSettings.fromJson((JsonElement)json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveframes() {
        try {
            final JsonObject json = new JsonObject();
            for (final Frame frame : ClickGui.frames) {
                final JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("x", (Number)frame.getX());
                jsonHack.addProperty("y", (Number)frame.getY());
                jsonHack.addProperty("open", frame.isOpen());
                json.add(frame.getName(), (JsonElement)jsonHack);
            }
            final PrintWriter saveJson = new PrintWriter(new FileWriter(FileManager.ONSCREEN));
            saveJson.println(FileManager.gsonPretty.toJson((JsonElement)json));
            saveJson.close();
        }
        catch (NullPointerException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    public static void saveFriends() {
        write(FileManager.FRIENDS, FriendManager.friendsList, true, true);
    }
    
    public static void write(final File outputFile, final List<String> writeContent, final boolean newline, final boolean overrideContent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
            for (final String outputLine : writeContent) {
                writer.write(outputLine);
                writer.flush();
                if (newline) {
                    writer.newLine();
                }
            }
        }
        catch (Exception ex) {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception ex2) {}
        }
    }
    
    public static List<String> read(final File inputFile) {
        final ArrayList<String> readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                readContent.add(line);
            }
        }
        catch (Exception ex) {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (Exception ex2) {}
        }
        return readContent;
    }
    
    static {
        gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        jsonParser = new JsonParser();
        SaveDir = new File(String.format("%s%s%s-%s-%s%s", Wrapper.INSTANCE.mc().field_71412_D, File.separator, "FutureX", "1.12.2", "0.0.1", File.separator));
        Mods = new File(FileManager.SaveDir, "mods.json");
        XRAYDATA = new File(FileManager.SaveDir, "xraydata.json");
        FRIENDS = new File(FileManager.SaveDir, "friends.json");
        ONSCREEN = new File(FileManager.SaveDir, "onscreen.json");
        SEARCH = new File(FileManager.SaveDir, "search.json");
        PROFILES = new File(FileManager.SaveDir, "profiles.json");
    }
}
