package Method.Client.module.combat;

import Method.Client.managers.*;
import Method.Client.module.*;
import Method.Client.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import Method.Client.utils.*;
import Method.Client.utils.system.*;
import net.minecraft.client.network.*;
import com.mojang.authlib.*;
import java.util.*;

public class AntiBot extends Module
{
    public static ArrayList<EntityBot> bots;
    public Setting level;
    public Setting tick;
    public Setting ifInAir;
    public Setting ifGround;
    public Setting ifZeroHealth;
    public Setting ifInvisible;
    public Setting ifEntityId;
    public Setting ifTabName;
    public Setting ifPing;
    public Setting remove;
    public Setting gwen;
    
    public AntiBot() {
        super("AntiBot", 0, Category.COMBAT, "Does not hit bots");
        this.level = Main.setmgr.add(new Setting("level", this, 0.0, 0.0, 6.0, false));
        this.tick = Main.setmgr.add(new Setting("tick", this, 0.0, 0.0, 999.0, true));
        this.ifInAir = Main.setmgr.add(new Setting("InAir", this, false));
        this.ifGround = Main.setmgr.add(new Setting("OnGround", this, false));
        this.ifZeroHealth = Main.setmgr.add(new Setting("ZeroHealth", this, false));
        this.ifInvisible = Main.setmgr.add(new Setting("Invisible", this, false));
        this.ifEntityId = Main.setmgr.add(new Setting("EntityId", this, false));
        this.ifTabName = Main.setmgr.add(new Setting("OutTabName", this, false));
        this.ifPing = Main.setmgr.add(new Setting("PingCheck", this, false));
        this.remove = Main.setmgr.add(new Setting("RemoveBots", this, false));
        this.gwen = Main.setmgr.add(new Setting("Gwen", this, false));
    }
    
    @Override
    public void onEnable() {
        AntiBot.bots.clear();
        super.onEnable();
    }
    
    @Override
    public boolean onPacket(final Object packet, final Connection.Side side) {
        if (this.gwen.getValBoolean()) {
            for (final Object entity : AntiBot.mc.field_71441_e.field_72996_f) {
                if (packet instanceof SPacketSpawnPlayer) {
                    final SPacketSpawnPlayer spawn = (SPacketSpawnPlayer)packet;
                    final double posX = spawn.func_186898_d() / 32.0;
                    final double posY = spawn.func_186897_e() / 32.0;
                    final double posZ = spawn.func_186899_f() / 32.0;
                    final double difX = AntiBot.mc.field_71439_g.field_70165_t - posX;
                    final double difY = AntiBot.mc.field_71439_g.field_70163_u - posY;
                    final double difZ = AntiBot.mc.field_71439_g.field_70161_v - posZ;
                    final double dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);
                    if (dist <= 17.0 && posX != AntiBot.mc.field_71439_g.field_70165_t && posY != AntiBot.mc.field_71439_g.field_70163_u && posZ != AntiBot.mc.field_71439_g.field_70161_v) {
                        return false;
                    }
                    continue;
                }
            }
        }
        return true;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.tick.getValDouble() > 0.0) {
            AntiBot.bots.clear();
        }
        for (final Object object : AntiBot.mc.field_71441_e.field_72996_f) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)object;
                if (entity instanceof EntityPlayerSP || !(entity instanceof EntityPlayer)) {
                    continue;
                }
                final EntityPlayer bot = (EntityPlayer)entity;
                if (!this.isBotBase(bot)) {
                    final int ailevel = (int)this.level.getValDouble();
                    final boolean isAi = ailevel > 0.0;
                    if (isAi && this.botPercentage(bot) > ailevel) {
                        this.addBot(bot);
                    }
                    else {
                        if (isAi || !this.botCondition(bot)) {
                            continue;
                        }
                        this.addBot(bot);
                    }
                }
                else {
                    this.addBot(bot);
                    if (!this.remove.getValBoolean()) {
                        continue;
                    }
                    AntiBot.mc.field_71441_e.func_72900_e((Entity)bot);
                }
            }
        }
        super.onClientTick(event);
    }
    
    void addBot(final EntityPlayer player) {
        if (!isBot(player)) {
            AntiBot.bots.add(new EntityBot(player));
        }
    }
    
    public static boolean isBot(final EntityPlayer player) {
        for (final EntityBot bot : AntiBot.bots) {
            if (bot.getName().equals(player.func_70005_c_())) {
                return player.func_82150_aj() == bot.isInvisible() || player.func_82150_aj();
            }
            if (bot.getId() == player.func_145782_y() || bot.getUuid().equals(player.func_146103_bH().getId())) {
                return true;
            }
        }
        return false;
    }
    
    boolean botCondition(final EntityPlayer bot) {
        if (this.tick.getValDouble() > 0.0 && bot.field_70173_aa < this.tick.getValDouble()) {
            return true;
        }
        if (this.ifInAir.getValBoolean() && bot.func_82150_aj() && bot.field_70181_x == 0.0 && bot.field_70163_u > AntiBot.mc.field_71439_g.field_70163_u + 1.0 && Utils.isBlockMaterial(new BlockPos((Entity)bot).func_177977_b(), Blocks.field_150350_a)) {
            return true;
        }
        if (this.ifGround.getValBoolean() && bot.field_70181_x == 0.0 && !bot.field_70124_G && bot.field_70122_E && bot.field_70163_u % 1.0 != 0.0 && bot.field_70163_u % 0.5 != 0.0) {
            return true;
        }
        if (this.ifZeroHealth.getValBoolean() && bot.func_110143_aJ() <= 0.0f) {
            return true;
        }
        if (this.ifInvisible.getValBoolean() && bot.func_82150_aj()) {
            return true;
        }
        if (this.ifEntityId.getValBoolean() && bot.func_145782_y() >= 1000000000) {
            return true;
        }
        if (this.ifTabName.getValBoolean()) {
            boolean isTabName = false;
            for (final NetworkPlayerInfo npi : Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_175106_d()) {
                npi.func_178845_a();
                if (npi.func_178845_a().getName().contains(bot.func_70005_c_())) {
                    isTabName = true;
                }
            }
            return !isTabName;
        }
        return false;
    }
    
    int botPercentage(final EntityPlayer bot) {
        int percentage = 0;
        if (this.tick.getValDouble() > 0.0 && bot.field_70173_aa < this.tick.getValDouble()) {
            ++percentage;
        }
        if (this.ifInAir.getValBoolean() && bot.func_82150_aj() && bot.field_70163_u > AntiBot.mc.field_71439_g.field_70163_u + 1.0 && Utils.isBlockMaterial(new BlockPos((Entity)bot).func_177977_b(), Blocks.field_150350_a)) {
            ++percentage;
        }
        if (this.ifGround.getValBoolean() && bot.field_70181_x == 0.0 && !bot.field_70124_G && bot.field_70122_E && bot.field_70163_u % 1.0 != 0.0 && bot.field_70163_u % 0.5 != 0.0) {
            ++percentage;
        }
        if (this.ifZeroHealth.getValBoolean() && bot.func_110143_aJ() <= 0.0f) {
            ++percentage;
        }
        if (this.ifInvisible.getValBoolean() && bot.func_82150_aj()) {
            ++percentage;
        }
        if (this.ifEntityId.getValBoolean() && bot.func_145782_y() >= 1000000000) {
            ++percentage;
        }
        if (this.ifTabName.getValBoolean()) {
            boolean isTabName = false;
            for (final NetworkPlayerInfo npi : Objects.requireNonNull(Wrapper.INSTANCE.mc().func_147114_u()).func_175106_d()) {
                npi.func_178845_a();
                if (npi.func_178845_a().getName().contains(bot.func_70005_c_())) {
                    isTabName = true;
                }
            }
            if (!isTabName) {
                ++percentage;
            }
        }
        return percentage;
    }
    
    boolean isBotBase(final EntityPlayer bot) {
        if (isBot(bot)) {
            return true;
        }
        bot.func_146103_bH();
        final GameProfile botProfile = bot.func_146103_bH();
        bot.func_110124_au();
        if (botProfile.getName() == null) {
            return true;
        }
        final String botName = botProfile.getName();
        return botName.contains("Body #") || botName.contains("NPC") || botName.equalsIgnoreCase(Utils.getEntityNameColor((EntityLivingBase)bot));
    }
    
    static {
        AntiBot.bots = new ArrayList<EntityBot>();
    }
    
    public static class EntityBot
    {
        private final String name;
        private final int id;
        private final UUID uuid;
        private final boolean invisible;
        
        public EntityBot(final EntityPlayer player) {
            this.name = String.valueOf(player.func_146103_bH().getName());
            this.id = player.func_145782_y();
            this.uuid = player.func_146103_bH().getId();
            this.invisible = player.func_82150_aj();
        }
        
        public int getId() {
            return this.id;
        }
        
        public String getName() {
            return this.name;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
        
        public boolean isInvisible() {
            return this.invisible;
        }
    }
}
