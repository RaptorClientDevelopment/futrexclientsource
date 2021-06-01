package Method.Client.utils.system;

import net.minecraft.world.chunk.*;
import net.minecraftforge.event.world.*;
import net.minecraft.util.datafix.*;
import net.minecraft.world.chunk.storage.*;
import java.io.*;
import java.util.*;
import Method.Client.utils.visual.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class WorldDownloader
{
    public static AnvilChunkLoader newdownload;
    public static boolean Saving;
    public static File SaveDir;
    public static String Savename;
    public static ArrayList<Chunk> chunks;
    
    public static void ChunkeventLOAD(final ChunkEvent.Load event) {
        if (WorldDownloader.Saving) {
            WorldDownloader.chunks.add(event.getChunk());
        }
    }
    
    public static ISaveHandler getSaveLoader(final String saveName, final boolean storePlayerdata, final File file) {
        return (ISaveHandler)new AnvilSaveHandler(file, saveName, storePlayerdata, new DataFixer(Wrapper.mc.func_184126_aj().field_188262_d));
    }
    
    public static void Clienttick() {
        if (WorldDownloader.Saving) {
            final ArrayList<Chunk> remove = new ArrayList<Chunk>();
            for (final Chunk option : WorldDownloader.chunks) {
                if (Wrapper.mc.field_71441_e.func_190526_b(option.field_76635_g, option.field_76647_h)) {
                    try {
                        WorldDownloader.newdownload.func_75816_a((World)Wrapper.INSTANCE.world(), Wrapper.mc.field_71441_e.func_72964_e(option.field_76635_g, option.field_76647_h));
                    }
                    catch (MinecraftException | IOException ex2) {
                        final Exception ex;
                        final Exception e = ex;
                        e.printStackTrace();
                    }
                }
                if (WorldDownloader.newdownload.func_191063_a(option.field_76635_g, option.field_76647_h)) {
                    remove.add(option);
                }
            }
            WorldDownloader.chunks.removeAll(remove);
        }
    }
    
    public static void start() {
        ChatUtils.message("Starting world download");
        WorldDownloader.Savename = "Download Time" + (int)System.currentTimeMillis() / 1000;
        WorldDownloader.SaveDir = new File(new File(Wrapper.INSTANCE.mc().field_71412_D, "saves").getAbsolutePath(), WorldDownloader.Savename);
        if (!WorldDownloader.SaveDir.exists()) {
            WorldDownloader.SaveDir.mkdir();
            WorldDownloader.newdownload = new AnvilChunkLoader(new File(WorldDownloader.SaveDir.getAbsolutePath()), Wrapper.mc.func_184126_aj());
            WorldDownloader.Saving = true;
            WorldDownloader.chunks.addAll((Collection<? extends Chunk>)Wrapper.mc.field_71441_e.func_72863_F().field_73236_b.values());
            ChatUtils.message(".minecraft/saves/" + WorldDownloader.Savename);
            return;
        }
        ChatUtils.error("This file already Exists?");
        ChatUtils.message(WorldDownloader.SaveDir.getPath());
    }
    
    protected static NBTTagList newDoubleNBTList(final double... numbers) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (final double d0 : numbers) {
            nbttaglist.func_74742_a((NBTBase)new NBTTagDouble(d0));
        }
        return nbttaglist;
    }
    
    public static void stop() {
        ChatUtils.message("Stopped world download");
        WorldDownloader.newdownload.func_75818_b();
        WorldDownloader.Saving = false;
        final ISaveHandler isavehandler = getSaveLoader(WorldDownloader.Savename, true, new File(Wrapper.INSTANCE.mc().field_71412_D, "saves"));
        final WorldInfo info = new WorldInfo(Wrapper.mc.field_71441_e.func_72912_H());
        final NBTTagCompound player = new NBTTagCompound();
        Wrapper.mc.field_71439_g.func_189511_e(player);
        info.func_176143_a(Wrapper.mc.field_71439_g.func_180425_c());
        info.field_76108_i = player;
        try {
            player.func_74782_a("Pos", (NBTBase)newDoubleNBTList(Wrapper.mc.field_71439_g.field_70165_t, Wrapper.mc.field_71439_g.field_70163_u, Wrapper.mc.field_71439_g.field_70161_v));
        }
        catch (Exception ex) {}
        info.func_176121_c(true);
        info.func_76060_a(GameType.CREATIVE);
        info.field_82576_c = "3;minecraft:air;127;decoration";
        info.func_76085_a(WorldType.field_77138_c);
        isavehandler.func_75755_a(info, player);
        if (WorldDownloader.newdownload.getPendingSaveCount() > 1) {
            ChatUtils.warning("There are still: " + WorldDownloader.newdownload.getPendingSaveCount() + " Chunks Pending to be saved");
        }
        ChatUtils.message("Finished Download!");
    }
    
    static {
        WorldDownloader.Saving = false;
        WorldDownloader.chunks = new ArrayList<Chunk>();
    }
}
