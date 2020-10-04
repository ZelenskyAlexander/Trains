package ru.sfedu.train.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.train.models.Train;

import static org.junit.Assert.*;
import static ru.sfedu.train.constants.Status.SUCCESS;
import static ru.sfedu.train.constants.WagonType.FREIGHT;
import static ru.sfedu.train.constants.WagonType.PASSENGER;

public class DataProviderJDBCTest {

    IDataProvider db = new DataProviderJDBC();
    Fill f = new Fill();

    @Before
    public void saveRecord() {
        Assert.assertEquals(db.appendDriver(f.driver1()).getStatus(), SUCCESS);
        Assert.assertEquals(db.appendDriver(f.driver2()).getStatus(), SUCCESS);

        Assert.assertEquals(db.appendFreight(f.freight1()).getStatus(), SUCCESS);
        Assert.assertEquals(db.appendFreight(f.freight2()).getStatus(), SUCCESS);

        Assert.assertEquals(db.appendPassenger(f.passenger1()).getStatus(), SUCCESS);
        Assert.assertEquals(db.appendPassenger(f.passenger2()).getStatus(), SUCCESS);
    }

    @Test
    public void getRecord() {
        Assert.assertNotNull(db.getDriverById(1));
        System.out.println(db.getDriverById(1));
        Assert.assertNotNull(db.getDriverById(2));
        System.out.println(db.getDriverById(2));

        Assert.assertNotNull(db.getFreightById(1));
        System.out.println(db.getFreightById(1));
        Assert.assertNotNull(db.getFreightById(2));
        System.out.println(db.getFreightById(2));

        Assert.assertNotNull(db.getPassengerById(1));
        System.out.println(db.getPassengerById(1));
        Assert.assertNotNull(db.getPassengerById(2));
        System.out.println(db.getPassengerById(2));
    }

    @Test
    public void initTrain() {
        Train train1 = new Train();
        train1.setId(1);
        train1.setDriver(db.getDriverById(1));
        train1.setWagon(db.getWagonById(FREIGHT,1));
        train1.setType(FREIGHT.toString());
//
        Assert.assertEquals(db.installTrain(train1).getStatus(), SUCCESS);
        Assert.assertEquals(db.hookWagon(1, 2).getStatus(), SUCCESS);
        System.out.println(db.getTrainById(1));

        Train train2 = new Train();
        train2.setId(2);
        train2.setDriver(db.getDriverById(2));
        train2.setWagon(db.getWagonById(PASSENGER,1));
        train2.setType(PASSENGER.toString());
//
        Assert.assertEquals(db.installTrain(train2).getStatus(), SUCCESS);
        Assert.assertEquals(db.hookWagon(2, 2).getStatus(), SUCCESS);
        System.out.println(db.getTrainById(2));

        Assert.assertEquals(db.unhookWagon(1, 2).getStatus(), SUCCESS);
        Assert.assertEquals(db.unhookWagon(1, 1).getStatus(), SUCCESS);
        Assert.assertEquals(db.dissolveTrain(2).getStatus(), SUCCESS);
    }

    @After
    public void delRecord() {
        Assert.assertEquals(db.delDriverById(1).getStatus(), SUCCESS);
        Assert.assertEquals(db.delDriverById(2).getStatus(), SUCCESS);
        Assert.assertEquals(db.delFreightById(1).getStatus(), SUCCESS);
        Assert.assertEquals(db.delFreightById(2).getStatus(), SUCCESS);
        Assert.assertEquals(db.delPassengerById(1).getStatus(), SUCCESS);
        Assert.assertEquals(db.delPassengerById(2).getStatus(), SUCCESS);
    }


}