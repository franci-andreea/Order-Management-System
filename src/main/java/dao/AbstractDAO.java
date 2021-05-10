package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * method that builds the SELECT query from a table based on a condition
     * @param field - a string representing the field's name in the table
     * @return a string representing the specific query
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }


    /**
     * method that build the SELECT query of all the elements from a table with all their fields
     * @return a string representing the specific query
     */
    private String createSelectAllQuery()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ordermanagement.");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * method that executes the query of selecting the elements from a table with all their fields
     * @return a list of elements of type T found in the table after executing the query
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement findAllStatement = null;
        ResultSet resultSet = null;
        String findAllQuery = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            findAllStatement = connection.prepareStatement(findAllQuery);

            resultSet = findAllStatement.executeQuery(createSelectAllQuery());

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(findAllStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * method that executes the query of finding an element based on its id value
     * @param id - an integer representing the id field from the table
     * @return
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement findByIdStatement = null;
        ResultSet resultSet = null;
        String findByIdQuery = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            findByIdStatement = connection.prepareStatement(findByIdQuery);
            findByIdStatement.setInt(1, id);
            resultSet = findByIdStatement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(findByIdStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * method that extracts the data with all the fields about an element from the table
     * @param object - the object whose data we want to retrieve from the table
     */
    public static void retrieveProperties(Object object)
    {
        for (Field field : object.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * method that creates a JTable with all the data from the database's tables in order to add them to the GUI
     * @param objects - a list of type T objects that have to be placed in a JTable
     * @return the JTable with the data from the database's table
     * @throws IllegalAccessException
     */
    public JTable createTable(List<T> objects) throws IllegalAccessException
    {
        DefaultTableModel tableModel = new DefaultTableModel();
        int numberOfColumns = objects.get(0).getClass().getDeclaredFields().length;

        for(Field field : objects.get(0).getClass().getDeclaredFields())
            tableModel.addColumn(field.getName());

        for(Object object : objects)
        {
            Object[] currentObjectRowDetails = new Object[numberOfColumns];
            int currentColumnInTheTable = 0;

            for(Field field : objects.get(0).getClass().getDeclaredFields())
            {
                field.setAccessible(true);
                currentObjectRowDetails[currentColumnInTheTable] = field.get(object);
                currentColumnInTheTable++;
            }

            tableModel.addRow(currentObjectRowDetails);
        }
        return new JTable(tableModel);
    }

}

