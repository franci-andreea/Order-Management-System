package bll.validators;

import model.Product;

import javax.swing.*;
import java.util.regex.Pattern;

public class ProductValidator implements Validator<Product>
{
    private static final String NAME_PATTERN = "^[a-zA-Z]*$";

    /**
     * method that checks if the input provided by the user on the order's fields are valid
     * @param productFrame - the product frame on which the input is inserted and where the pop-ups with the warnings
     *      *      *         regarding the wrong input appear
     * @param product - the product to be inserted and verified
     */
    public void validate(JFrame productFrame, Product product)
    {
        if(product.getQuantity() < 0)
        {
            JOptionPane.showMessageDialog(productFrame, "The quantity has to be a positive number!");
            throw new IllegalArgumentException("The quantity has to be a positive number!");
        }

        if(product.getPrice() < 0)
        {
            JOptionPane.showMessageDialog(productFrame, "The price of the product should be a positive number!");
            throw new IllegalArgumentException("The price of the product should be a positive number!");
        }

        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        if(!namePattern.matcher(product.getName()).matches())
        {
            JOptionPane.showMessageDialog(productFrame, "Name should only contains a-Z characters!");
            throw new IllegalArgumentException("Name should only contains a-Z characters!");
        }
    }
}
