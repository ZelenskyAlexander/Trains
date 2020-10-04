package ru.sfedu.train.constants;

public class Constants {

    public static final String CONFIG_KEY = "config";
    public static final String CONFIG_PATH = "src/main/resources/config.properties";
    public static final String CONFIG_JAR = "/config.properties";

//    properties keys

    public static final String CSV_DRIVER = "CSV_DRIVER";
    public static final String CSV_TRAIN = "CSV_TRAIN";
    public static final String CSV_PASSENGER = "CSV_PASSENGER";
    public static final String CSV_FREIGHT = "CSV_FREIGHT";

    public static final String XML_DRIVER = "XML_DRIVER";
    public static final String XML_TRAIN = "XML_TRAIN";
    public static final String XML_PASSENGER = "XML_PASSENGER";
    public static final String XML_FREIGHT = "XML_FREIGHT";

    public static final String DB_DRIVER = "DB_DRIVER";
    public static final String DB_CONNECT = "DB_CONNECT";
    public static final String DB_USER = "DB_USER";
    public static final String DB_PASS = "DB_PASS";

//    dml and ddl

    public static final String DB_DRIVER_ID = "Id";
    public static final String DB_DRIVER_FIRST_NAME = "FirstName";
    public static final String DB_DRIVER_LAST_NAME = "LastName";
    public static final String DB_DRIVER_INSERT = "INSERT INTO Driver VALUES (%d, '%s', '%s');";
    public static final String DB_DRIVER_SELECT = "SELECT * FROM Driver WHERE Id=%d LIMIT 1;";
    public static final String DB_DRIVER_DELETE = "DELETE FROM Driver WHERE Id=%d;";
    public static final String DB_DRIVER_DELETE_CASCADE = "DELETE FROM Train WHERE Driver=%d;";

    public static final String DB_PASSENGER_ID = "Id";
    public static final String DB_PASSENGER_PRODUCER = "Producer";
    public static final String DB_PASSENGER_NUMBER = "NumPassenger";
    public static final String DB_PASSENGER_TYPE = "Type";
    public static final String DB_PASSENGER_INSERT = "INSERT INTO Passenger VALUES (%d, '%s', %d, '%s');";
    public static final String DB_PASSENGER_SELECT = "SELECT * FROM Passenger WHERE Id=%d LIMIT 1;";
    public static final String DB_PASSENGER_DELETE = "DELETE FROM Passenger WHERE Id=%d;";
    public static final String DB_PASSENGER_DELETE_CASCADE = "DELETE FROM Train WHERE Wagon=%d AND Type='PASSENGER';";

    public static final String DB_FREIGHT_ID = "Id";
    public static final String DB_FREIGHT_PRODUCER = "Producer";
    public static final String DB_FREIGHT_CAPACITY = "MaxCapacity";
    public static final String DB_FREIGHT_TYPE = "Type";
    public static final String DB_FREIGHT_INSERT = "INSERT INTO Freight VALUES (%d, '%s', %d, '%s');";
    public static final String DB_FREIGHT_SELECT = "SELECT * FROM Freight WHERE Id=%d LIMIT 1;";
    public static final String DB_FREIGHT_DELETE = "DELETE FROM Freight WHERE Id=%d;";
    public static final String DB_FREIGHT_DELETE_CASCADE = "DELETE FROM Train WHERE Wagon=%d AND Type='FREIGHT';";

    public static final String DB_TRAIN_ID = "Id";
    public static final String DB_TRAIN_WAGON = "Wagon";
    public static final String DB_TRAIN_DRIVER = "Driver";
    public static final String DB_TRAIN_TYPE = "Type";
    public static final String DB_TRAIN_INSERT = "INSERT INTO Train VALUES (%d, %d, %d, '%s');";
    public static final String DB_TRAIN_SELECT = "SELECT * FROM Train WHERE Id=%d;";
    public static final String DB_TRAIN_DELETE = "DELETE FROM Train WHERE Id=%d;";
    public static final String DB_TRAIN_VALID_SELECT = "select count(*) as cnt from Train where id = %d or Driver = %d or (Wagon = %d and Type = '%s');";
    public static final String DB_TRAIN_VALID_CNT = "cnt";
    public static final String DB_HOOK_VALID_SELECT = "select * from Train where wagon<>%d and Type=(select Type from Train where id=%d limit 1) limit 1;";
    public static final String DB_UNHOOK_DELETE = "DELETE FROM Train WHERE id = %d AND wagon = %d;";

//    cli

    public static final String CLI_CSV = "CSV";
    public static final String CLI_XML = "XML";
    public static final String CLI_DB = "DB";

    public static final String CLI_DRIVER = "DRIVER";
    public static final String CLI_PASSENGER = "PASSENGER";
    public static final String CLI_FREIGHT = "FREIGHT";
    public static final String CLI_TRAIN = "TRAIN";

    public static final String CLI_APPEND = "APPEND";
    public static final String CLI_GET_BY_ID = "GETBYID";
    public static final String CLI_DEL_BY_ID = "DELBYID";

    public static final String CLI_TRAIN_DISSOLVE = "DISSOLVE";//удалить
    public static final String CLI_TRAIN_HOOK = "HOOK";//присоединить
    public static final String CLI_TRAIN_UNHOOK = "UNHOOK";//отсоединить
    public static final String CLI_TRAIN_INSTALL = "INSTALL";//сформировать

    public static final String CLI_PARSE_P = ", ";
    public static final String CLI_PARSE_S = " ";
    public static final String CLI_PARSE_M = "-";
}
