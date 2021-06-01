package Method.Client.utils;

import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;
import Method.Client.utils.system.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import com.mojang.authlib.exceptions.*;

public class LoginUtils
{
    public static String loginAlt(final String email, final String password) {
        final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        String displayText = "Logged [License]: " + Wrapper.INSTANCE.mc().func_110432_I().func_111285_a();
        try {
            authentication.logIn();
            Minecraft.func_71410_x().field_71449_j = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
        }
        catch (AuthenticationUnavailableException e2) {
            displayText = "Cannot contact authentication server!";
        }
        catch (AuthenticationException e) {
            if (e.getMessage().contains("Invalid username or password.") || e.getMessage().toLowerCase().contains("account migrated")) {
                displayText = "Wrong password!";
            }
            else {
                displayText = "Cannot contact authentication server!";
            }
        }
        catch (NullPointerException e3) {
            displayText = "Wrong password!";
        }
        return displayText;
    }
    
    public static String getName(final String email, final String password) {
        final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(email);
        authentication.setPassword(password);
        try {
            authentication.logIn();
            return authentication.getSelectedProfile().getName();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void changeCrackedName(final String name) {
        Minecraft.func_71410_x().field_71449_j = new Session(name, "", "", "mojang");
    }
}
