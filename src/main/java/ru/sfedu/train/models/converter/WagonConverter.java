package ru.sfedu.train.models.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.train.api.DataProviderCSV;
import ru.sfedu.train.constants.WagonType;
import ru.sfedu.train.models.Wagon;

public class WagonConverter extends AbstractBeanField<Wagon, String> {

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Wagon wagon = (Wagon) value;
        return String.format("%s#%s", wagon.getClass().getSimpleName().toUpperCase(), wagon.getId());
    }

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        DataProviderCSV csv = new DataProviderCSV();
        String[] param = s.split("#", 2);
        return csv.getWagonById(WagonType.valueOf(param[0].toUpperCase()), Long.parseLong(param[1]));
    }

}
