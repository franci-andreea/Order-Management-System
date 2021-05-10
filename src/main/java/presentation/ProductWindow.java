package presentation;

import bll.ProductBLL;
import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductWindow
{
    private JFrame productFrame = new JFrame("Products");

    private JPanel productPanel;
    private JPanel tablePanel;
    private JPanel buttonsPanel;

    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewButton;

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;

    private JTable productTable = new JTable();

    public ProductWindow()
    {
        productFrame.setPreferredSize(new Dimension(1000, 550));

        productPanel = new JPanel();
        tablePanel = new JPanel();
        buttonsPanel = new JPanel();

        insertButton = new JButton("insert");
        updateButton = new JButton("update");
        deleteButton = new JButton("delete");
        viewButton = new JButton("view");

        idField = new JTextField();
        nameField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();

        productPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        productPanel.setLayout(new GridLayout(5, 2));
        productPanel.add(new JLabel("ID"));
        idField.setPreferredSize(new Dimension(180, 10));
        productPanel.add(idField);
        productPanel.add(new JLabel("Name"));
        productPanel.add(nameField);
        productPanel.add(new JLabel("Price"));
        productPanel.add(priceField);
        productPanel.add(new JLabel("Quantity"));
        productPanel.add(quantityField);

        buttonsPanel.add(insertButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(viewButton);

        productFrame.add(productPanel, BorderLayout.WEST);
        productFrame.add(tablePanel);
        productFrame.add(buttonsPanel, BorderLayout.SOUTH);
        productFrame.pack();
    }

    public void initializeInsertButton()
    {
        this.addInsertProductButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String introducedName = nameField.getText();
                int introducedPrice = Integer.parseInt(priceField.getText());
                int introducedQuantity = Integer.parseInt(quantityField.getText());
                Product product;

                if(idField.getText() == null || idField.getText().isEmpty())
                    product = new Product(introducedName, introducedPrice, introducedQuantity);
                else
                {
                    int introducedID = Integer.parseInt(idField.getText());
                    product = new Product(introducedID, introducedName, introducedPrice, introducedQuantity);
                }

                ProductBLL productBLL = new ProductBLL();
                productBLL.insertProduct(productFrame, product);

                JOptionPane.showMessageDialog(productFrame, "Product inserted successfully");
            }
        });
    }

    /**
     * method that implements the action listener for the delete button
     */
    public void initializeDeleteButton()
    {
        this.addDeleteProductButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ProductBLL productBLL = new ProductBLL();
                try
                {
                    if(idField.getText() == null || idField.getText().isEmpty())
                        JOptionPane.showMessageDialog(productFrame, "Please provide the ID of the product you want to delete");
                    else
                    {
                        int introducedID = Integer.parseInt(idField.getText());
                        productBLL.deleteProduct(introducedID);
                        JOptionPane.showMessageDialog(productFrame, "Product with id = " + introducedID + " deleted successfully");
                    }
                }
                catch(IllegalArgumentException illegalArgumentException)
                {
                    illegalArgumentException.printStackTrace();
                }
            }
        });
    }

    /**
     * method that implements the action listener for the update button
     */
    public void initializeUpdateButton()
    {
        this.addUpdateProductButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ProductBLL productBLL = new ProductBLL();
                try
                {
                    if(idField.getText() == null || idField.getText().isEmpty())
                        JOptionPane.showMessageDialog(productFrame, "Please provide the ID of the product you want to update");
                    else
                    {
                        int insertedID = Integer.parseInt(idField.getText());
                        int insertedNewPrice = Integer.parseInt(priceField.getText());
                        int insertedQuantity = Integer.parseInt(quantityField.getText());

                        productBLL.updateProduct(productFrame, insertedID, insertedNewPrice, insertedQuantity);
                        JOptionPane.showMessageDialog(productFrame, "Product with id = " + insertedID + " updated successfully");
                    }
                }
                catch (IllegalArgumentException illegalArgumentException)
                {
                    illegalArgumentException.printStackTrace();
                }
            }
        });
    }

    /**
     * method that implements the action listener for the view button
     */
    public void initializeViewButton()
    {
        this.addViewProductsButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ProductDAO productDAO = new ProductDAO();
                try
                {
                    tablePanel.removeAll();
                    productTable = productDAO.createTable(productDAO.findAll());
                    JScrollPane jScrollPane = new JScrollPane(productTable);
                    tablePanel.add(jScrollPane);
                    productFrame.getContentPane().validate();
                    productFrame.getContentPane().repaint();

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
    public void addInsertProductButtonPressedActionListener(ActionListener actionListener)
    {
        insertButton.addActionListener(actionListener);
    }

    public void addDeleteProductButtonPressedActionListener(ActionListener actionListener)
    {
        deleteButton.addActionListener(actionListener);
    }

    public void addUpdateProductButtonPressedActionListener(ActionListener actionListener)
    {
        updateButton.addActionListener(actionListener);
    }

    public void addViewProductsButtonPressedActionListener(ActionListener actionListener)
    {
        viewButton.addActionListener(actionListener);
    }

    /**
     * getter for the product frame
     * @return the product frame
     */
    public JFrame getProductFrame()
    {
        return productFrame;
    }

    /**
     * method that makes the product frame to be visibile
     */
    public void setVisible()
    {
        productFrame.setVisible(true);
    }
}
