package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO extends AbstractDAO<Product>
{
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertQueryString = "INSERT INTO product (id, name, price, quantity)" + " VALUES (?, ?, ?, ?)";
    private static final String updateQueryString = "UPDATE product SET price = ?, quantity = ? WHERE id = ?";
    private static final String deleteQueryString = "DELETE FROM product where id = ?";
    private static final String getAllByIdQueryString = "SELECT id FROM product";

    /**
     * method that executes the insert statement in the database's product table
     * @param product - the client to be inserted in the product table
     * @return - the value of the ID assigned to the product
     */
    public static int insert(Product product)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedID = -1;
        try
        {
            insertStatement = databaseConnection.prepareStatement(insertQueryString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, product.getId());
            insertStatement.setString(2, product.getName());
            insertStatement.setInt(3, product.getPrice());
            insertStatement.setInt(4, product.getQuantity());
            insertStatement.executeUpdate();

            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next())
            {
                insertedID = resultSet.getInt(1);
            }
        } catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally
        {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(databaseConnection);
        }

        return insertedID;
    }

    /**
     * method that executes the delete statement from the database's product table
     * @param product - the product to be deleted from the table
     */
    public static void delete(Product product)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;

        try
        {
            deleteStatement = databaseConnection.prepareStatement(deleteQueryString);
            deleteStatement.setInt(1, product.getId());
            deleteStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(databaseConnection);
        }
    }

    /**
     * method that executes the update statement on a product from the database's product table
     * the fields that can be modified are the product's price and the product's quantity
     * @param product - the product we want to update as we need its id
     * @param newPrice - an integer representing the new price
     * @param newQuantity - an integer representing the new quantity
     */
    public static void update(Product product, int newPrice, int newQuantity)
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;

        try
        {
            updateStatement = databaseConnection.prepareStatement(updateQueryString);
            updateStatement.setInt(1, newPrice);
            updateStatement.setInt(2, newQuantity);
            updateStatement.setInt(3, product.getId());
            updateStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ProductDAO:update " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(databaseConnection);
        }
    }

    /**
     * method that extract all the products' ids from the product table
     * @return - an ArrayList that contains all the products' ids found in the table
     */
    public ArrayList<Integer> getAllProductsById()
    {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement getAllByIdStatement = null;
        ResultSet resultSet;
        ArrayList<Integer> productIDs = new ArrayList<Integer>();
        try
        {
            getAllByIdStatement = databaseConnection.prepareStatement(getAllByIdQueryString);
            resultSet = getAllByIdStatement.executeQuery();
            while(resultSet.next())
            {
                productIDs.add(resultSet.getInt("id"));
            }
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, "ProductDAO:getAllProductsByID " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(getAllByIdStatement);
            ConnectionFactory.close(databaseConnection);
        }
        return productIDs;
    }
}
