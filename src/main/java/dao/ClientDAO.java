package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO extends AbstractDAO<Client>
{
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertQueryString = "INSERT INTO client (id, name, address, email, age)" + " VALUES (?, ?, ?, ?, ?)";
    private static final String updateQueryString = "UPDATE client SET address = ?, age = ? WHERE id = ?";
    private static final String deleteQueryString = "DELETE FROM client where id = ?";
    private static final String getAllByIdQueryString = "SELECT id FROM client";

    /**
     * method that executes the insert statement in the database's client table
     * @param client - the client to be inserted in the client table
     * @return - the value of the ID assigned to the client
     */
    public static int insert(Client client)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedID = -1;
        try
        {
            insertStatement = databaseConnection.prepareStatement(insertQueryString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, client.getId());
            insertStatement.setString(2, client.getName());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getEmail());
            insertStatement.setInt(5, client.getAge());
            insertStatement.executeUpdate();

            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next())
            {
                insertedID = resultSet.getInt(1);
            }

        } catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally
        {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(databaseConnection);
        }

        return insertedID;
    }

    /**
     * method that executes the delete statement from the database's client table
     * @param client - the client to be deleted from the table
     */
    public static void delete(Client client)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try
        {
            deleteStatement = databaseConnection.prepareStatement(deleteQueryString);
            deleteStatement.setInt(1, client.getId());
            deleteStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(databaseConnection);
        }
    }

    /**
     * method that executes the update statement on a client from the database's client table
     * the fields that can be modified are the client's address and the client's age
     * @param client - the client we want to update as we need to get its id
     * @param newAddress - string that represents the new address
     * @param newAge - integer that represents the new age
     */
    public static void update(Client client, String newAddress, int newAge)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try
        {
            updateStatement = databaseConnection.prepareStatement(updateQueryString);
            updateStatement.setInt(3, client.getId());
            updateStatement.setInt(2, newAge);
            updateStatement.setString(1, newAddress);
            updateStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(databaseConnection);
        }

    }

    /**
     * method that extract all the clients' ids from the client table
     * @return - an ArrayList that contains all the clients' ids found in the table
     */
    public ArrayList<Integer> getAllClientsById()
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement getAllByIdStatement = null;
        ResultSet resultSet;
        ArrayList<Integer> clientIDs = new ArrayList<Integer>();
        try
        {
            getAllByIdStatement = databaseConnection.prepareStatement(getAllByIdQueryString);
            resultSet = getAllByIdStatement.executeQuery();
            while(resultSet.next())
            {
                clientIDs.add(resultSet.getInt("id"));
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(getAllByIdStatement);
            ConnectionFactory.close(databaseConnection);
        }
        return clientIDs;
    }

}
