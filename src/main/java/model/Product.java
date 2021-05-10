package model;

public class Product
{
    private int id;
    private String name;
    private int price;
    private int quantity;

    /**
     * Constructor to initialize the attributes of a product, including the ID
     * @param id - an integer that represents the ID given to the product
     * @param name - a string that represents the product's name
     * @param price - an integer that represents the product's price per unit
     * @param quantity - an integer representing how many units of this specific product are available in the warehouse
     */
    public Product(int id, String name, int price, int quantity)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Constructor used to initialize the attributes of a product, not taking into account a specific ID
     * @param name - a string that represents the product's name
     * @param price - an integer that represents the product's price per unit
     * @param quantity - an integer representing how many units of this specific product are available in the warehouse
     */
    public Product(String name, int price, int quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Empty constructor for initialization
     */
    public Product() {

    }

    /**
     * Getters and setters for the product's attributes
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
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
