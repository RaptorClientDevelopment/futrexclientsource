package Method.Client.utils.Screens.Custom.Search;

import net.minecraft.block.*;
import java.util.stream.*;
import java.util.*;
import java.util.function.*;
import com.google.gson.*;

public class SearchGuiSettings
{
    public static ArrayList<String> blockNames;
    public static String[] defaultNames;
    
    public static String getName(final Block block) {
        return "" + Block.field_149771_c.func_177774_c((Object)block);
    }
    
    public SearchGuiSettings(final Block... blocks) {
        Arrays.stream(blocks).parallel().map(SearchGuiSettings::getName).distinct().sorted().forEachOrdered(SearchGuiSettings.blockNames::add);
        SearchGuiSettings.defaultNames = SearchGuiSettings.blockNames.toArray(new String[0]);
    }
    
    public static List<String> getBlockNames() {
        return Collections.unmodifiableList((List<? extends String>)SearchGuiSettings.blockNames);
    }
    
    public static List<String> getAllBlockNames(final List<String> blockNames) {
        final ArrayList<String> Allblocks = new ArrayList<String>();
        for (final Block block : Block.field_149771_c) {
            if (!blockNames.contains(block.func_149732_F())) {
                Allblocks.add(getName(block));
            }
        }
        return Allblocks;
    }
    
    public static void add(final Block block) {
        final String name = getName(block);
        if (Collections.binarySearch(SearchGuiSettings.blockNames, name) >= 0) {
            return;
        }
        SearchGuiSettings.blockNames.add(name);
        Collections.sort(SearchGuiSettings.blockNames);
    }
    
    public static void remove(final int index) {
        if (index < 0 || index >= SearchGuiSettings.blockNames.size()) {
            return;
        }
        SearchGuiSettings.blockNames.remove(index);
    }
    
    public static void resetToDefaults() {
        SearchGuiSettings.blockNames.clear();
        SearchGuiSettings.blockNames.addAll(Arrays.asList(SearchGuiSettings.defaultNames));
    }
    
    public static List<String> SearchBlocksAll(final List<String> blockNames, final String text) {
        final ArrayList<String> Allblocks = new ArrayList<String>();
        for (final Block block : Block.field_149771_c) {
            if (!blockNames.contains(block.func_149732_F()) && block.func_149732_F().toLowerCase().contains(text.toLowerCase())) {
                Allblocks.add(getName(block));
            }
        }
        return Allblocks;
    }
    
    public static void fromJson(final JsonElement json) {
        if (!json.isJsonArray()) {
            return;
        }
        SearchGuiSettings.blockNames.clear();
        StreamSupport.stream((Spliterator<Object>)json.getAsJsonArray().spliterator(), true).filter(JsonElement::isJsonPrimitive).filter(e -> e.getAsJsonPrimitive().isString()).map(e -> Block.func_149684_b(e.getAsString())).filter(Objects::nonNull).map((Function<? super Object, ?>)SearchGuiSettings::getName).distinct().sorted().forEachOrdered(s -> SearchGuiSettings.blockNames.add(s));
    }
    
    public static JsonElement toJson() {
        final JsonArray json = new JsonArray();
        SearchGuiSettings.blockNames.forEach(s -> json.add((JsonElement)new JsonPrimitive(s)));
        return (JsonElement)json;
    }
    
    static {
        SearchGuiSettings.blockNames = new ArrayList<String>();
    }
}
