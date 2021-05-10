package presentation;

import connection.ConnectionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Controller
{
    private AppMainWindow appMainWindow;
    private ClientWindow clientWindow;
    private ProductWindow productWindow;
    private OrderWindow orderWindow;

    /**
     * method that starts the application
     */
    public void start()
    {
        Connection connection = ConnectionFactory.getConnection();
        if(connection != null)
        {
            System.out.println("Connected!");
        }

        appMainWindow = new AppMainWindow();

        initializeButtons();
    }

    /**
     * method that initializes all the button's action listeners
     */
    public void initializeButtons()
    {
        appMainWindow.addClientsButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clientWindow = new ClientWindow();
                clientWindow.initializeInsertButton();
                clientWindow.initializeDeleteButton();
                clientWindow.initializeUpdateButton();
                clientWindow.initializeViewButton();
            }
        });

        appMainWindow.addProductsButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                productWindow = new ProductWindow();
                productWindow.setVisible();
                productWindow.initializeInsertButton();
                productWindow.initializeDeleteButton();
                productWindow.initializeUpdateButton();
                productWindow.initializeViewButton();
            }
        });

        appMainWindow.addOrdersButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                productWindow = new ProductWindow();
                orderWindow = new OrderWindow();
                orderWindow.initializeCreateButton(productWindow.getProductFrame());
                orderWindow.initializeViewButton();
            }
        });
    }
}
