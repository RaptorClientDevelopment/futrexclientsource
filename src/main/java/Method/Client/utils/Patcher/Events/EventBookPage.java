package Method.Client.utils.Patcher.Events;

import net.minecraftforge.fml.common.eventhandler.*;

public class EventBookPage extends Event
{
    private String page;
    
    public EventBookPage(final String page) {
        this.page = page;
    }
    
    public String getPage() {
        return this.page;
    }
    
    public void setPage(final String page) {
        this.page = page;
    }
}
