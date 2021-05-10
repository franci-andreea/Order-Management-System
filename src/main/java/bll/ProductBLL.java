package bll;

import bll.validators.ProductValidator;
import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import java.util.NoSuchElementException;

public class ProductBLL
{
    private ProductDAO productDAO;
    private ProductValidator productValidator;

    /**
     * Constructor that initializes the validator for the product fields and the product DAO that manages the SQL statements
     */
    public ProductBLL()
    {
        productValidator = new ProductValidator();
        productDAO = new ProductDAO();
    }

    /**
     * method to insert a product in the table
     * @param productFrame - needed as parameter for the validate method
     * @param productToInsert - the product to be inserted in the table
     */
    public void insertProduct(JFrame productFrame, Product productToInsert)
    {
        productValidator.validate(productFrame, productToInsert);
        ProductDAO.insert(productToInsert);
    }

    /**
     * method to delete a product from the table
     * @param id - an integer representing the id of the product we want to delete
     */
    public void deleteProduct(int id)
    {
        Product productToDelete = productDAO.findById(id);
        if(productToDelete == null)
        {
            throw new NoSuchElementException("The product with id = " + id + " was not found");
        }
        ProductDAO.delete(productToDelete);
    }

    /**
     * method to update a product from the table
     * @param productFrame - needed as parameter for the validate method
     * @param id - an integer representing the id of the product that has to be updated
     * @param newPrice - an integer representing the new price
     * @param newQuantity - an integer representing the new quantity
     */
    public void updateProduct(JFrame productFrame, int id, int newPrice, int newQuantity)
    {
        Product productToUpdate = productDAO.findById(id);
        if(productToUpdate == null)
        {
            throw new NoSuchElementException("The product with id = " + id + " was not found");
        }
        Product cloneProduct = new Product(productToUpdate.getName(), productToUpdate.getPrice(), productToUpdate.getQuantity());
        productValidator.validate(productFrame, cloneProduct);
        ProductDAO.update(productToUpdate, newPrice, newQuantity);
    }
}
