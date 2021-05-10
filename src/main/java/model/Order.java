package model;

public class Order
{
    private int id;
    private int productID;
    private int clientID;
    private int quantity;

    /**
     * Constructor to initialize the attributes of an order, including the ID
     * @param id - an integer that represents the ID given to the order
     * @param productID - an integer representing the ID of an existing product from the product table in the database
     * @param clientID - an integer representing the ID of an existing client from the client table in the database
     * @param quantity - an integer that represents how many units from the specified product the order has
     */
    public Order(int id, int productID, int clientID, int quantity)
    {
        this.id = id;
        this.productID = productID;
        this.clientID = clientID;
        this.quantity = quantity;
    }

    /**
     * Constructor used to initialize the attributes of an order, not taking into account a specific ID
     * @param productID - an integer representing the ID of an existing product from the product table in the database
     * @param clientID - an integer representing the ID of an existing client from the client table in the database
     * @param quantity - an integer that represents how many units from the specified product the order has
     */
    public Order(int productID, int clientID, int quantity)
    {
        this.productID = productID;
        this.clientID = clientID;
        this.quantity = quantity;
    }

    /**
     * Empty constructor for initialization
     */
    public Order() {

    }

    /**
     * Getters and setters for the order's attributes
     * Each one of the getters returns the specific attribute described in the constructors above
     */
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getProductID()
    {
        return productID;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public int getClientID()
    {
        return clientID;
    }

    public void setClientID(int clientID)
    {
        this.clientID = clientID;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
