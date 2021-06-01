package Method.Client.utils;

import Method.Client.module.render.*;
import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public class PlayerIdentity implements Serializable
{
    private String displayName;
    private final String stringUuid;
    
    public PlayerIdentity(final String stringUuid) {
        final String formattedUuid = stringUuid.replace("-", "");
        this.stringUuid = stringUuid;
        this.displayName = "Loading...";
        new Thread(() -> {
            this.displayName = getName(formattedUuid);
            MobOwner.identityCacheMap.put(this.getStringUuid(), this);
        }).start();
    }
    
    private static String getName(final String UUID) {
        try {
            final URL e = new URL("https://api.mojang.com/user/profiles/" + UUID.replace("-", "") + "/names");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(e.openConnection().getInputStream()));
            final StringBuilder jsonb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonb.append(line).append("\n");
            }
            final String formattedjson = jsonb.toString();
            reader.close();
            final JsonArray array = new JsonParser().parse(formattedjson).getAsJsonArray();
            final JsonObject obj = array.get(array.size() - 1).getAsJsonObject();
            final String nameform = obj.get("name").getAsString();
            try {
                obj.get("changedToAt");
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(obj.get("changedToAt").getAsLong());
                return nameform;
            }
            catch (Exception ee) {
                return nameform;
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
            return UUID;
        }
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getStringUuid() {
        return this.stringUuid;
    }
}
