package bll;

import bll.validators.OrderValidator;
import bll.validators.OutOfStockException;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class OrderBLL
{
    private OrderValidator orderValidator;
    private OrderDAO orderDAO;

    /**
     * Constructor that initializes the validator for the order fields and the order DAO that manages the SQL statements
     */
    public OrderBLL()
    {
        orderValidator = new OrderValidator();
        orderDAO = new OrderDAO();
    }

    /**
     * method to insert an order in the table
     * @param orderFrame - needed as parameter for the validate method
     * @param productFrame - needed as parameter for the validate method
     * @param orderToInsert - the order to be inserted
     * @throws OutOfStockException
     */
    public void insertOrder(JFrame orderFrame, JFrame productFrame, Order orderToInsert) throws OutOfStockException
    {
        orderValidator.validate(orderFrame, orderToInsert);
        OrderDAO.insert(orderToInsert);

        ProductDAO productDAO = new ProductDAO();
        ProductBLL productBLL = new ProductBLL();
        Product product = productDAO.findById(orderToInsert.getProductID());
        productBLL.updateProduct(productFrame, product.getId(), product.getPrice(), product.getQuantity() - orderToInsert.getQuantity());
    }

    /**
     * method that writes the bill of the last order inserted
     * @param order - the last created order
     * @throws IOException
     */
    public void writeBill(Order order) throws IOException
    {
        Product product;
        ProductDAO productDAO = new ProductDAO();

        product = productDAO.findById(order.getProductID());

        FileWriter fileWriter = new FileWriter("GeneratedBill" + order.getId() + ".txt");
        fileWriter.write("Order with id = " + order.getId() + "\n");
        fileWriter.write("Created at " + LocalDateTime.now() + "\n");
        fileWriter.write("Client ID = " + order.getClientID() + "\n");
        fileWriter.write("Product ID = " + order.getProductID() + "\n");
        fileWriter.write("Quantity = " + order.getQuantity() + "\n");

        fileWriter.write("Total order bill = " + product.getPrice() * order.getQuantity());
        fileWriter.close();
    }

}
