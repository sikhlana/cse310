package Core;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread
{
    final private static ConcurrentLinkedQueue<Task> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void run()
    {
        while (true)
        {
            synchronized (queue)
            {
                if (queue.isEmpty())
                {
                    try
                    {
                        Thread.sleep(10000L);
                        continue;
                    }
                    catch (InterruptedException e)
                    {
                        Core.App.debug(e);
                        break;
                    }
                }

                queue.remove().run();
            }
        }
    }

    public static void queue(Task t)
    {
        synchronized (queue)
        {
            queue.add(t);
        }
    }

    public interface Task extends Comparable<Task>
    {
        void run();
    }
}
