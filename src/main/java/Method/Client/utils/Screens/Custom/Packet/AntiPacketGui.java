package Method.Client.utils.Screens.Custom.Packet;

import Method.Client.*;
import java.io.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.login.client.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public final class AntiPacketGui extends GuiScreen
{
    private static ListGui listGui;
    private ListGui AllMobs;
    private GuiTextField MobFieldName;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiButton doneButton;
    private String PacketToAdd;
    private String PacketToRemove;
    public static ArrayList<String> Packets;
    public static ArrayList<String> AllPackets;
    public static ArrayList<AntiPacketPacket> ListOfPackets;
    
    public boolean func_73868_f() {
        return false;
    }
    
    public static List<String> removePackets(final List<String> input) {
        AntiPacketGui.AllPackets.clear();
        for (final AntiPacketPacket listOfPacket : AntiPacketGui.ListOfPackets) {
            if (!input.contains(listOfPacket.packet.getClass().getSimpleName())) {
                AntiPacketGui.AllPackets.add(listOfPacket.packet.getClass().getSimpleName());
            }
        }
        return AntiPacketGui.AllPackets;
    }
    
    public static List<String> PacketSearch(final String text) {
        final ArrayList<String> Temp = new ArrayList<String>();
        for (final AntiPacketPacket listOfPacket : AntiPacketGui.ListOfPackets) {
            final String s = listOfPacket.packet.getClass().getSimpleName();
            if (listOfPacket.packet.getClass().getSimpleName().toLowerCase().contains(text.toLowerCase())) {
                Temp.add(s);
            }
        }
        return Temp;
    }
    
    public void func_73866_w_() {
        this.ListSetup();
        AntiPacketGui.listGui = new ListGui(this.field_146297_k, this, AntiPacketGui.Packets, this.field_146294_l - this.field_146294_l / 3, 0);
        this.AllMobs = new ListGui(this.field_146297_k, this, removePackets(AntiPacketGui.Packets), 0, 0);
        this.MobFieldName = new GuiTextField(1, this.field_146297_k.field_71466_p, 64, this.field_146295_m - 55, 150, 18);
        this.field_146292_n.add(this.addButton = new GuiButton(0, 214, this.field_146295_m - 56, 30, 20, "Add"));
        this.field_146292_n.add(this.removeButton = new GuiButton(1, this.field_146294_l - 300, this.field_146295_m - 56, 100, 20, "Remove Selected"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l - 108, 8, 100, 20, "Remove All"));
        this.field_146292_n.add(new GuiButton(20, this.field_146294_l - 308, 8, 100, 20, "Add Server"));
        this.field_146292_n.add(new GuiButton(40, this.field_146294_l - 208, 8, 100, 20, "Add Client"));
        this.field_146292_n.add(this.doneButton = new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m - 28, "Done"));
    }
    
    public static ArrayList<AntiPacketPacket> GetPackets() {
        final ArrayList<AntiPacketPacket> list = new ArrayList<AntiPacketPacket>();
        for (final AntiPacketPacket listOfPacket : AntiPacketGui.ListOfPackets) {
            for (final String s : AntiPacketGui.listGui.list) {
                if (s.equalsIgnoreCase(listOfPacket.packet.getClass().getSimpleName())) {
                    list.add(listOfPacket);
                    break;
                }
            }
        }
        return list;
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0: {
                if (this.AllMobs.selected >= 0 && this.AllMobs.selected <= this.AllMobs.list.size() && !this.PacketToAdd.isEmpty() && !AntiPacketGui.Packets.contains(this.PacketToAdd)) {
                    AntiPacketGui.Packets.add(this.PacketToAdd);
                    AntiPacketGui.AllPackets.remove(this.PacketToAdd);
                    this.PacketToAdd = "";
                    break;
                }
                break;
            }
            case 1: {
                AntiPacketGui.Packets.remove(AntiPacketGui.listGui.selected);
                AntiPacketGui.AllPackets.add(this.PacketToRemove);
                break;
            }
            case 2: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiYesNo((GuiYesNoCallback)this, "Reset to Defaults", "Are you sure?", 0));
                break;
            }
            case 20: {
                if (this.field_146297_k.field_71441_e != null) {
                    for (final AntiPacketPacket listOfPacket : AntiPacketGui.ListOfPackets) {
                        if (listOfPacket.packet.getClass().getSimpleName().startsWith("S") && !AntiPacketGui.listGui.list.contains(listOfPacket.packet.getClass().getSimpleName())) {
                            AntiPacketGui.listGui.list.add(listOfPacket.packet.getClass().getSimpleName());
                        }
                    }
                    break;
                }
                break;
            }
            case 3: {
                this.field_146297_k.func_147108_a((GuiScreen)Main.ClickGui);
                break;
            }
            case 40: {
                if (this.field_146297_k.field_71441_e != null) {
                    for (final AntiPacketPacket listOfPacket : AntiPacketGui.ListOfPackets) {
                        if (listOfPacket.packet.getClass().getSimpleName().startsWith("C") && !AntiPacketGui.listGui.list.contains(listOfPacket.packet.getClass().getSimpleName())) {
                            AntiPacketGui.listGui.list.add(listOfPacket.packet.getClass().getSimpleName());
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public void func_73878_a(final boolean result, final int id) {
        if (id == 0 && result) {
            AntiPacketGui.Packets.clear();
        }
        super.func_73878_a(result, id);
        this.field_146297_k.func_147108_a((GuiScreen)this);
    }
    
    public void func_146274_d() throws IOException {
        super.func_146274_d();
        final int mouseX = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c;
        final int mouseY = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1;
        AntiPacketGui.listGui.handleMouseInput(mouseX, mouseY);
        this.AllMobs.handleMouseInput(mouseX, mouseY);
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.MobFieldName.func_146192_a(mouseX, mouseY, mouseButton);
        if (mouseX < 550 || mouseX > this.field_146294_l - 50 || mouseY < 32 || mouseY > this.field_146295_m - 64) {
            AntiPacketGui.listGui.selected = -1;
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        this.MobFieldName.func_146201_a(typedChar, keyCode);
        if (keyCode == 28) {
            this.func_146284_a(this.addButton);
        }
        else if (keyCode == 211) {
            this.func_146284_a(this.removeButton);
        }
        else if (keyCode == 1) {
            this.func_146284_a(this.doneButton);
        }
        if (!this.MobFieldName.func_146179_b().isEmpty()) {
            this.AllMobs.list = PacketSearch(this.MobFieldName.func_146179_b());
        }
        else {
            this.AllMobs.list = removePackets(AntiPacketGui.Packets);
        }
    }
    
    public static ArrayList<String> Getmobs() {
        return new ArrayList<String>(AntiPacketGui.listGui.list);
    }
    
    public void func_73876_c() {
        this.MobFieldName.func_146178_a();
        if (AntiPacketGui.listGui.selected >= 0 && AntiPacketGui.listGui.selected <= AntiPacketGui.listGui.list.size()) {
            this.PacketToRemove = AntiPacketGui.listGui.list.get(AntiPacketGui.listGui.selected);
        }
        if (this.AllMobs.selected >= 0 && this.AllMobs.selected < this.AllMobs.list.size()) {
            this.PacketToAdd = this.AllMobs.list.get(this.AllMobs.selected);
        }
        this.addButton.field_146124_l = (this.PacketToAdd != null);
        this.removeButton.field_146124_l = (AntiPacketGui.listGui.selected >= 0 && AntiPacketGui.listGui.selected < AntiPacketGui.listGui.list.size());
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        this.func_73732_a(this.field_146297_k.field_71466_p, "Packet (" + AntiPacketGui.listGui.getSize() + ")", this.field_146294_l / 2, 12, 16777215);
        AntiPacketGui.listGui.drawScreen(mouseX, mouseY, partialTicks);
        this.AllMobs.drawScreen(mouseX, mouseY, partialTicks);
        this.MobFieldName.func_146194_f();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        if (this.MobFieldName.func_146179_b().isEmpty() && !this.MobFieldName.func_146206_l()) {
            this.func_73731_b(this.field_146297_k.field_71466_p, "Packet Name", 68, this.field_146295_m - 50, 8421504);
        }
        func_73734_a(48, this.field_146295_m - 56, 64, this.field_146295_m - 36, -6250336);
        func_73734_a(49, this.field_146295_m - 55, 64, this.field_146295_m - 37, -16777216);
        func_73734_a(214, this.field_146295_m - 56, 244, this.field_146295_m - 55, -6250336);
        func_73734_a(214, this.field_146295_m - 37, 244, this.field_146295_m - 36, -6250336);
        func_73734_a(244, this.field_146295_m - 56, 246, this.field_146295_m - 36, -6250336);
        func_73734_a(214, this.field_146295_m - 55, 243, this.field_146295_m - 52, -16777216);
        func_73734_a(214, this.field_146295_m - 40, 243, this.field_146295_m - 37, -16777216);
        func_73734_a(215, this.field_146295_m - 55, 216, this.field_146295_m - 37, -16777216);
        func_73734_a(242, this.field_146295_m - 55, 245, this.field_146295_m - 37, -16777216);
    }
    
    private void ListSetup() {
        AntiPacketGui.ListOfPackets.clear();
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketEncryptionResponse()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new C00Handshake()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketLoginStart()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketDisconnect()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEnableCompression()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEncryptionRequest()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketLoginSuccess()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketAnimation()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketChatMessage()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketClickWindow()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketClientSettings()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketClientStatus()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketCloseWindow()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketConfirmTeleport()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketConfirmTransaction()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketCreativeInventoryAction()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketCustomPayload()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketEnchantItem()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketEntityAction()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketHeldItemChange()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketInput()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketKeepAlive()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPlaceRecipe()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPlayer()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPlayerAbilities()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPlayerDigging()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPlayerTryUseItem()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPing()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketPlayerTryUseItemOnBlock()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketRecipeInfo()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketResourcePackStatus()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketServerQuery()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketSeenAdvancements()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketSpectate()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketSteerBoat()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketTabComplete()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketUpdateSign()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketUseEntity()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new CPacketVehicleMove()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketAdvancementInfo()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketAnimation()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketBlockAction()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketBlockBreakAnim()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketBlockChange()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketCamera()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketChangeGameState()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketChat()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketChunkData()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketCloseWindow()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketCollectItem()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketCombatEvent()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketConfirmTransaction()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketCooldown()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketCustomPayload()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketDestroyEntities()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketDisconnect()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketDisplayObjective()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEffect()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntity()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityAttach()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityEffect()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityEquipment()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityHeadLook()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityMetadata()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityProperties()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityStatus()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityTeleport()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketEntityVelocity()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketExplosion()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketHeldItemChange()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketJoinGame()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketKeepAlive()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketMaps()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketMoveVehicle()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketMultiBlockChange()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketOpenWindow()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketParticles()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketPlaceGhostRecipe()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketPlayerAbilities()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketPlayerListHeaderFooter()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketPlayerListItem()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketPlayerPosLook()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketRecipeBook()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketRemoveEntityEffect()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketResourcePackSend()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketRespawn()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketScoreboardObjective()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSelectAdvancementsTab()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketServerDifficulty()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSetExperience()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSetPassengers()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSetSlot()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSignEditorOpen()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSoundEffect()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnExperienceOrb()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnGlobalEntity()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnMob()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnObject()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnPainting()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnPlayer()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketSpawnPosition()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketStatistics()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketTabComplete()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketTeams()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketTimeUpdate()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketTitle()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketUnloadChunk()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketUpdateBossInfo()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketUpdateHealth()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketUpdateScore()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketUpdateTileEntity()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketUseBed()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketWindowItems()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketWindowProperty()));
        AntiPacketGui.ListOfPackets.add(new AntiPacketPacket((Packet)new SPacketWorldBorder()));
    }
    
    static {
        AntiPacketGui.Packets = new ArrayList<String>();
        AntiPacketGui.AllPackets = new ArrayList<String>();
        AntiPacketGui.ListOfPackets = new ArrayList<AntiPacketPacket>();
    }
    
    private static class ListGui extends GuiScrollingList
    {
        private final Minecraft mc;
        private List<String> list;
        private int selected;
        private int offsetx;
        
        public ListGui(final Minecraft mc, final AntiPacketGui screen, final List<String> list, final int offsetx, final int offsety) {
            super(mc, screen.field_146294_l / 4, screen.field_146295_m, 32 + offsety, screen.field_146295_m - 64, 50 + offsetx, 16, screen.field_146294_l, screen.field_146295_m);
            this.selected = -1;
            this.offsetx = offsetx;
            this.mc = mc;
            this.list = list;
        }
        
        protected int getSize() {
            return this.list.size();
        }
        
        protected void elementClicked(final int index, final boolean doubleClick) {
            if (index >= 0 && index < this.list.size()) {
                this.selected = index;
            }
        }
        
        protected boolean isSelected(final int index) {
            return index == this.selected;
        }
        
        protected void drawBackground() {
            Gui.func_73734_a(50 + this.offsetx, this.top, 66 + this.offsetx, this.bottom, -1);
        }
        
        protected void drawSlot(final int slotIdx, final int entryRight, final int slotTop, final int slotBuffer, final Tessellator tess) {
            final FontRenderer fr = this.mc.field_71466_p;
            GlStateManager.func_179094_E();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179121_F();
            fr.func_78276_b(" (" + this.list.get(slotIdx) + ")", 68 + this.offsetx, slotTop + 2, 15790320);
        }
    }
}
