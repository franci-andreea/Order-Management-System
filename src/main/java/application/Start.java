package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import presentation.Controller;

public class Start
{
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args)
    {
        try
        {
            Controller controller = new Controller();
            controller.start();
        }
        catch (Exception ex)
        {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
    }

}
