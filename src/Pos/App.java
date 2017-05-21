package Pos;

public class App extends Thread
{
    public void run()
    {
        try
        {

        }
        catch (Exception e)
        {
            Core.App.error("Thread/Pos Exception: %s", e);
        }
    }
}
