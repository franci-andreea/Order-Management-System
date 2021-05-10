package presentation;

import bll.OrderBLL;
import bll.validators.OutOfStockException;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class OrderWindow
{
    private JFrame orderFrame = new JFrame("Orders");

    private JPanel orderPanel;
    private JPanel tablePanel;
    private JPanel buttonsPanel;

    private JButton insertButton;
    private JButton viewButton;

    private JTextField idField;
    private JComboBox clientIdField;
    private JComboBox productIdField;
    private JTextField quantityField;

    private JTable orderTable = new JTable();

    private ClientDAO clientDAO = new ClientDAO();
    private ArrayList<Integer> clientsIDs;

    private ProductDAO productDAO = new ProductDAO();
    private ArrayList<Integer> productsIDs;

    /**
     * Constructor that initializes the main window's appearance
     */
    public OrderWindow()
    {
        orderFrame.setPreferredSize(new Dimension(1000, 550));

        orderPanel = new JPanel();
        tablePanel = new JPanel();
        buttonsPanel = new JPanel();

        insertButton = new JButton("create");
        viewButton = new JButton("view");

        idField = new JTextField();

        productsIDs = productDAO.getAllProductsById();
        productIdField = new JComboBox(productsIDs.toArray());

        clientsIDs = clientDAO.getAllClientsById();
        clientIdField = new JComboBox(clientsIDs.toArray());

        quantityField = new JTextField();

        orderPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        orderPanel.setLayout(new GridLayout(5,6 ));
        orderPanel.add(new JLabel("ID"));
        idField.setPreferredSize(new Dimension(150, 10));
        orderPanel.add(idField);
        orderPanel.add(new JLabel("Client ID"));
        orderPanel.add(clientIdField);
        orderPanel.add(new JLabel("Product ID"));
        orderPanel.add(productIdField);
        orderPanel.add(new JLabel("Quantity"));
        orderPanel.add(quantityField);

        buttonsPanel.add(insertButton);
        buttonsPanel.add(viewButton);

        orderFrame.add(orderPanel, BorderLayout.WEST);
        orderFrame.add(tablePanel);
        orderFrame.add(buttonsPanel, BorderLayout.SOUTH);
        orderFrame.pack();
        orderFrame.setVisible(true);
    }

    /**
     * method that implements the action listener for the insert button
     * @param productFrame - needed as parameter
     */
    public void initializeCreateButton(JFrame productFrame)
    {
        this.addCreateOrderButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int introducedProductID = Integer.parseInt(productIdField.getSelectedItem().toString());
                System.out.println("productID selected = " + introducedProductID);
                int introducedClientID = Integer.parseInt(clientIdField.getSelectedItem().toString());
                System.out.println("clientID selected = " + introducedClientID);
                int introducedQuantity = Integer.parseInt(quantityField.getText());
                Order order;

                if(idField.getText() == null || idField.getText().isEmpty())
                    order = new Order(introducedProductID, introducedClientID, introducedQuantity);
                else
                {
                    int introducedID = Integer.parseInt(idField.getText());
                    order = new Order(introducedID, introducedProductID, introducedClientID, introducedQuantity);
                }

                OrderBLL orderBLL = new OrderBLL();
                try
                {
                    orderBLL.insertOrder(orderFrame, productFrame, order);
                    JOptionPane.showMessageDialog(orderFrame, "Order created successfully!");
                    orderBLL.writeBill(order);
                } catch (OutOfStockException outOfStockException)
                {
                    JOptionPane.showMessageDialog(orderFrame, "Not enough stock!");
                    outOfStockException.printStackTrace();
                }
                catch (IOException exception)
                {
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * method that implements the action listener for the view button
     */
    public void initializeViewButton()
    {
        this.addViewOrdersButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                OrderDAO orderDAO = new OrderDAO();
                try
                {
                    tablePanel.removeAll();
                    orderTable = orderDAO.createTable(orderDAO.findAll());
                    JScrollPane jScrollPane = new JScrollPane(orderTable);
                    tablePanel.add(jScrollPane);
                    orderFrame.getContentPane().validate();
                    orderFrame.getContentPane().repaint();

                } catch (IllegalAccessException illegalAccessException)
                {
                    illegalAccessException.printStackTrace();
                }
            }
        });
    }

    /**
     * methods that add action listeners to the buttons visible on the client window
     */
    public void addCreateOrderButtonPressedActionListener(ActionListener actionListener)
    {
        insertButton.addActionListener(actionListener);
    }

    public void addViewOrdersButtonPressedActionListener(ActionListener actionListener)
    {
        viewButton.addActionListener(actionListener);
    }
}
