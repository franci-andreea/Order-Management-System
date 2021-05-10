package bll.validators;

import dao.ProductDAO;
import model.Order;
import model.Product;

import javax.swing.*;

public class OrderValidator implements Validator<Order>
{
    Product product;
    ProductDAO productDAO;

    /**
     * method that checks if the input provided by the user on the order's fields are valid
     * @param orderFrame - the order frame on which the input is inserted and where the pop-ups with the warnings
     *      *              regarding the wrong input appear
     * @param order - the order to be inserted and verified
     * @throws OutOfStockException
     */
    public void validate(JFrame orderFrame, Order order) throws OutOfStockException
    {
        if (order.getProductID() < 0)
        {
            JOptionPane.showMessageDialog(orderFrame, "The product ID is not valid!");
            throw new IllegalArgumentException("The product ID is not valid!");
        }

        if (order.getQuantity() < 0)
        {
            JOptionPane.showMessageDialog(orderFrame, "The quantity should be a positive number");
            throw new IllegalArgumentException("Quantity is not a positive number!");
        }

        productDAO = new ProductDAO();
        product = productDAO.findById(order.getProductID());

        if(order.getQuantity() > product.getQuantity())
        {
            JOptionPane.showMessageDialog(orderFrame, "The quantity is too much and there is not enough stock");
            throw new OutOfStockException("The quantity is too much and there is not enough stock");
        }
    }
}
