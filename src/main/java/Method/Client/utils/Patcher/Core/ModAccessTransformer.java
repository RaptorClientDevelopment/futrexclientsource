package Method.Client.utils.Patcher.Core;

import net.minecraftforge.fml.common.asm.transformers.*;
import java.io.*;

public final class ModAccessTransformer extends AccessTransformer
{
    public ModAccessTransformer() throws IOException {
        super("META-INF/accesstransformer.cfg");
    }
}
