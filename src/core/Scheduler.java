package Core;

import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduler extends Thread
{
    final private static Queue<Task> queue = new PriorityQueue<>();

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
