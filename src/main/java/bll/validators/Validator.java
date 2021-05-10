package bll.validators;

import javax.swing.*;

public interface Validator<T>
{
    public void validate(JFrame frame, T t) throws OutOfStockException;
}
