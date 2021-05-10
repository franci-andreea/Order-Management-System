package model;

public class Client
{
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    /**
     * Constructor to initialize the attributes of a client, including the ID
     * @param id - an integer that represents the ID given to the client
     * @param name - a string that represents the client's name
     * @param address - a string that represents the client's address
     * @param email - a string with a specific format that represents the client's email address
     * @param age - an integer that represents the client's age
     */
    public Client(int id, String name, String address, String email, int age)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    /**
     * Constructor used to initialize the attributes of a client, not taking into account a specific ID
     * @param name - a string that represents the client's name
     * @param address - a string that represents the client's address
     * @param email - a string with a specific format that represents the client's email address
     * @param age - an integer that represents the client's age
     */
    public Client(String name, String address, String email, int age)
    {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    /**
     * Empty constructor for initialization
     */
    public Client() {

    }

    /**
     * Method to print in a nice way as a String the attributes of a specific client
     * @return all the client's attributes as a String
     */
    @Override
    public String toString() {
        return "Client [id = " + id + ", name = " + name + ", address = " + address + ", email = " + email + ", age = "
                + age + "]";
    }

    /**
     * Getters and setters for the client's attributes
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
