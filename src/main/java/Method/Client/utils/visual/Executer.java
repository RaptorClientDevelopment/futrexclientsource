package Method.Client.utils.visual;

import java.util.concurrent.*;

public class Executer
{
    private static ExecutorService executor;
    
    public static void init() {
        Executer.executor = Executors.newSingleThreadExecutor();
    }
    
    public static void execute(final Runnable task) {
        Executer.executor.execute(task);
    }
}
