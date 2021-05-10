package presentation;

import bll.ClientBLL;

import dao.ClientDAO;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientWindow
{
    private JFrame clientFrame = new JFrame("Clients");

    private JPanel clientPanel;
    private JPanel tablePanel;
    private JPanel buttonsPanel;

    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewButton;

    private JTextField idField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField mailField;
    private JTextField ageField;

    private JTable clientTable = new JTable();

    /**
     * Constructor that initializes the main window's appearance
     */
    public ClientWindow()
    {
        clientFrame.setPreferredSize(new Dimension(1000, 550));

        clientPanel = new JPanel();
        tablePanel = new JPanel();
        buttonsPanel = new JPanel();

        insertButton = new JButton("insert");
        updateButton = new JButton("update");
        deleteButton = new JButton("delete");
        viewButton = new JButton("view");

        idField = new JTextField();
        nameField = new JTextField();
        addressField = new JTextField();
        mailField = new JTextField();
        ageField = new JTextField();

        clientPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        clientPanel.setLayout(new GridLayout(5,2));
        clientPanel.add(new JLabel("ID"));
        idField.setPreferredSize(new Dimension(180, 10));
        clientPanel.add(idField);
        clientPanel.add(new JLabel("Name"));
        clientPanel.add(nameField);
        clientPanel.add(new JLabel("Address"));
        clientPanel.add(addressField);
        clientPanel.add(new JLabel("Mail"));
        clientPanel.add(mailField);
        clientPanel.add(new JLabel("Age"));
        clientPanel.add(ageField);

        buttonsPanel.add(insertButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(viewButton);

        clientFrame.add(clientPanel, BorderLayout.WEST);
        clientFrame.add(tablePanel, BorderLayout.EAST);
        clientFrame.add(buttonsPanel, BorderLayout.SOUTH);
        clientFrame.pack();
        clientFrame.setVisible(true);
    }

    /**
     * method that implements the action listener for the insert button
     */
    public void initializeInsertButton()
    {
         this.addInsertClientButtonPressedActionListener(new ActionListener()
         {
             @Override
             public void actionPerformed(ActionEvent e)
             {
                 String introducedName = nameField.getText();
                 String introducedAddress = addressField.getText();
                 String introducedEmail = mailField.getText();
                 int introducedAge = Integer.parseInt(ageField.getText());
                 Client client;

                 try
                 {
                     if(idField.getText() == null || idField.getText().isEmpty())
                         client = new Client(introducedName, introducedAddress, introducedEmail, introducedAge);
                     else
                     {
                         int introducedID = Integer.parseInt(idField.getText());
                         client = new Client(introducedID, introducedName, introducedAddress, introducedEmail, introducedAge);
                     }

                     ClientBLL clientBLL = new ClientBLL();
                     clientBLL.insertClient(clientFrame, client);
                     JOptionPane.showMessageDialog(clientFrame, "Client inserted successfully!");

                 } catch (IllegalAccessException illegalAccessException)
                 {
                     illegalAccessException.printStackTrace();
                 }
             }
         });
    }

    /**
     * method that implements the action listener for the delete button
     */
    public void initializeDeleteButton()
    {
        this.addDeleteClientButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ClientBLL clientBLL = new ClientBLL();
                try
                {
                    if(idField.getText() == null || idField.getText().isEmpty())
                        JOptionPane.showMessageDialog(clientFrame, "Please introduce the ID of the client you want to delete");
                    else
                    {
                        int introducedID = Integer.parseInt(idField.getText());
                        clientBLL.deleteClient(introducedID);
                        JOptionPane.showMessageDialog(clientFrame, "Client with id = " + introducedID + " deleted successfully");
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
        this.addUpdateClientButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ClientBLL clientBLL = new ClientBLL();
                try
                {
                    if(idField.getText() == null || idField.getText().isEmpty())
                        JOptionPane.showMessageDialog(clientFrame, "Please provide the ID of the client you want to update");
                    else
                    {
                        int insertedID = Integer.parseInt(idField.getText());
                        String insertedAddress = addressField.getText();
                        int insertedAge = Integer.parseInt(ageField.getText());

                        clientBLL.updateClient(clientFrame, insertedID, insertedAddress, insertedAge);
                        JOptionPane.showMessageDialog(clientFrame, "Client with id = " + insertedID + " updated successfully");
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
        this.addViewClientsButtonPressedActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ClientDAO clientDAO = new ClientDAO();
                try
                {
                    tablePanel.removeAll();
                    clientTable = clientDAO.createTable(clientDAO.findAll());
                    JScrollPane jScrollPane = new JScrollPane(clientTable);
                    tablePanel.add(jScrollPane);
                    clientFrame.getContentPane().validate();
                    clientFrame.getContentPane().repaint();

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
    public void addInsertClientButtonPressedActionListener(ActionListener actionListener)
    {
        insertButton.addActionListener(actionListener);
    }

    public void addDeleteClientButtonPressedActionListener(ActionListener actionListener)
    {
        deleteButton.addActionListener(actionListener);
    }

    public void addUpdateClientButtonPressedActionListener(ActionListener actionListener)
    {
        updateButton.addActionListener(actionListener);
    }

    public void addViewClientsButtonPressedActionListener(ActionListener actionListener)
    {
        viewButton.addActionListener(actionListener);
    }
}
