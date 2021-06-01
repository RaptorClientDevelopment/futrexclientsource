package Method.Client.module.command;

import Method.Client.utils.visual.*;
import com.google.gson.*;
import com.google.common.reflect.*;
import com.mojang.authlib.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class UsernameHistory extends Command
{
    public UsernameHistory() {
        super("NameHistory ");
    }
    
    @Override
    public void runCommand(final String s, final String[] args) {
        final String name = args[0];
        try {
            final String uuid = this.grabUUID(name);
            final String names = readURL(new URL("https://api.mojang.com/user/profiles/" + uuid + "/names"));
            if (names.isEmpty()) {
                ChatUtils.error(name + " has had no username changes.");
            }
            else {
                final Collection<GameProfile> profiles = (Collection<GameProfile>)new Gson().fromJson(names, new TypeToken<Collection<GameProfile>>() {}.getType());
                String output = "";
                for (final GameProfile profile : profiles) {
                    output = output + "\"" + profile.getName() + "\", ";
                }
                ChatUtils.warning(name + " has had the usernames: " + output.substring(0, output.length() - 2) + ".");
            }
        }
        catch (Exception e) {
            ChatUtils.error("Failed to look up user.");
            ChatUtils.error("Usage: " + this.getSyntax());
        }
    }
    
    private String grabUUID(final String name) {
        try {
            final String userInfo = readURL(new URL("https://api.mojang.com/users/profiles/minecraft/" + name));
            final Map<String, Object> output = (Map<String, Object>)new Gson().fromJson(userInfo, new TypeToken<Map<String, Object>>() {}.getType());
            return output.get("id").toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String readURL(final URL url) {
        final StringBuilder temp = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String s;
            while ((s = reader.readLine()) != null) {
                temp.append(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
        return temp.toString();
    }
    
    @Override
    public String getDescription() {
        return "Finds username's past";
    }
    
    @Override
    public String getSyntax() {
        return "NameHistory <username>";
    }
}
