package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO extends AbstractDAO<Order>
{
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertQueryString = "INSERT INTO ordermanagement.order (id, productID, clientID, quantity)" + " VALUES (?, ?, ?, ?)";
    private static final String updateQueryString = "UPDATE ordermanagement.order SET quantity = ? WHERE id = ?";

    /**
     * method that executes the insert statement in the database's order table
     * @param order - the order to be inserted in the order table
     * @return
     */
    public static int insert(Order order)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedID = -1;
        try
        {
            insertStatement = databaseConnection.prepareStatement(insertQueryString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getId());
            insertStatement.setInt(2, order.getProductID());
            insertStatement.setInt(3, order.getClientID());
            insertStatement.setInt(4, order.getQuantity());
            insertStatement.executeUpdate();

            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next())
            {
                insertedID = resultSet.getInt(1);
            }
        } catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally
        {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(databaseConnection);
        }

        return insertedID;
    }

    /**
     * method that executes the update statement on an element from the database's order table
     * @param order - the order we want to update as we need its id
     * @param newQuantity - an integer representing the new quantity
     */
    public static void update(Order order, int newQuantity)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try
        {
            updateStatement = databaseConnection.prepareStatement(updateQueryString);
            updateStatement.setInt(1, newQuantity);
            updateStatement.setInt(2, order.getId());
            updateStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "OrderDAO:update " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(databaseConnection);
        }
    }

}
