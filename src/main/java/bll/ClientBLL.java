package bll;

import bll.validators.ClientValidator;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.util.NoSuchElementException;

public class ClientBLL
{
    private ClientDAO clientDAO;
    private ClientValidator clientValidator;

    /**
     * Constructor that initializes the validator for the client fields and the client DAO that manages the SQL statements
     */
    public ClientBLL()
    {
        clientValidator = new ClientValidator();
        clientDAO = new ClientDAO();
    }

    /**
     * method to insert the client in the table
     * @param clientFrame - we need to send this frame as parameter for the validate method
     * @param clientToInsert - the client to be inserted
     * @throws IllegalAccessException
     */
    public void insertClient(JFrame clientFrame, Client clientToInsert) throws IllegalAccessException
    {
        clientValidator.validate(clientFrame, clientToInsert);
        ClientDAO.insert(clientToInsert);
    }

    /**
     * method to delete the client from the table
     * @param id - an integer representing the id of the client we want to delete
     */
    public void deleteClient(int id)
    {
        Client clientToDelete = clientDAO.findById(id);
        if(clientToDelete == null)
        {
            throw new NoSuchElementException("The client with id = " + id + " was not found");
        }
        ClientDAO.delete(clientToDelete);
    }

    /**
     * method to update a client's details in the table
     * @param clientFrame - needed as parameter for the validate method
     * @param id - an integer representing the id of the client we want to update
     * @param newAddress - a string representing the new address
     * @param newAge - a string representing the new age
     */
    public void updateClient(JFrame clientFrame, int id, String newAddress, int newAge)
    {
        Client clientToUpdate = clientDAO.findById(id);
        if(clientToUpdate == null)
        {
            throw new NoSuchElementException("The client with id = " + id + " was not found");
        }
        Client cloneClient = new Client(clientToUpdate.getName(), newAddress, clientToUpdate.getEmail(), newAge);
        clientValidator.validate(clientFrame, cloneClient);
        ClientDAO.update(clientToUpdate, newAddress, newAge);
    }

}
