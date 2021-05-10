package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AppMainWindow
{
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton clientButton;
    private JButton productButton;
    private JButton orderButton;

    /**
     * Constructor that initializes the main window's appearance
     */
    public AppMainWindow()
    {
        mainFrame = new JFrame("Order Management Application");

        mainPanel = new JPanel();

        clientButton = new JButton("Clients");
        productButton = new JButton("Products");
        orderButton = new JButton("Orders");

        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        mainPanel.setLayout(new GridLayout());

        mainPanel.add(clientButton);
        mainPanel.add(new JPanel());
        mainPanel.add(productButton);
        mainPanel.add(new JPanel());
        mainPanel.add(orderButton);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /**
     * methods that add action listeners to all the buttons from the main application window
     */
    public void addClientsButtonPressedActionListener(ActionListener actionListener)
    {
        clientButton.addActionListener(actionListener);
    }

    public void addProductsButtonPressedActionListener(ActionListener actionListener)
    {
        productButton.addActionListener(actionListener);
    }

    public void addOrdersButtonPressedActionListener(ActionListener actionListener)
    {
        orderButton.addActionListener(actionListener);
    }

}
