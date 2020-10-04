package ru.sfedu.train.models.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.train.api.DataProviderCSV;
import ru.sfedu.train.models.Driver;

public class DriverConverter extends AbstractBeanField<Driver, String> {

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Driver driver = (Driver) value;
        return String.valueOf(driver.getId());
    }

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        DataProviderCSV csv = new DataProviderCSV();
        return csv.getDriverById(Long.parseLong(s));
    }
}
