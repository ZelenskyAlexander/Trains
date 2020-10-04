package ru.sfedu.train.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.train.models.Train;

import java.util.Collection;
import java.util.Collections;

import static ru.sfedu.train.constants.Status.SUCCESS;
import static ru.sfedu.train.constants.WagonType.FREIGHT;
import static ru.sfedu.train.constants.WagonType.PASSENGER;

public class DataProviderCSVTest {

    IDataProvider csv = new DataProviderCSV();
    Fill f = new Fill();

    @Before
    public void saveRecord() {
        Assert.assertEquals(csv.appendDriver(f.driver1()).getStatus(), SUCCESS);
        Assert.assertEquals(csv.appendDriver(f.driver2()).getStatus(), SUCCESS);

        Assert.assertEquals(csv.appendFreight(f.freight1()).getStatus(), SUCCESS);
        Assert.assertEquals(csv.appendFreight(f.freight2()).getStatus(), SUCCESS);

        Assert.assertEquals(csv.appendPassenger(f.passenger1()).getStatus(), SUCCESS);
        Assert.assertEquals(csv.appendPassenger(f.passenger2()).getStatus(), SUCCESS);
    }

    @Test
    public void getRecord() {
        Assert.assertNotNull(csv.getDriverById(1));
        System.out.println(csv.getDriverById(1));
        Assert.assertNotNull(csv.getDriverById(2));
        System.out.println(csv.getDriverById(2));

        Assert.assertNotNull(csv.getFreightById(1));
        System.out.println(csv.getFreightById(1));
        Assert.assertNotNull(csv.getFreightById(2));
        System.out.println(csv.getFreightById(2));

        Assert.assertNotNull(csv.getPassengerById(1));
        System.out.println(csv.getPassengerById(1));
        Assert.assertNotNull(csv.getPassengerById(2));
        System.out.println(csv.getPassengerById(2));
    }

    @Test
    public void initTrain() {
        Train train1 = new Train();
        train1.setId(1);
        train1.setDriver(csv.getDriverById(1));
        train1.setWagon(csv.getWagonById(FREIGHT,1));
        train1.setType(FREIGHT.toString());
//
        Assert.assertEquals(csv.installTrain(train1).getStatus(), SUCCESS);
        Assert.assertEquals(csv.hookWagon(1, 2).getStatus(), SUCCESS);
        System.out.println(csv.getTrainById(1));

        Train train2 = new Train();
        train2.setId(2);
        train2.setDriver(csv.getDriverById(2));
        train2.setWagon(csv.getWagonById(PASSENGER,1));
        train2.setType(PASSENGER.toString());
//
        Assert.assertEquals(csv.installTrain(train2).getStatus(), SUCCESS);
        Assert.assertEquals(csv.hookWagon(2, 2).getStatus(), SUCCESS);
        System.out.println(csv.getTrainById(2));

        Assert.assertEquals(csv.unhookWagon(1, 2).getStatus(), SUCCESS);
        Assert.assertEquals(csv.unhookWagon(1, 1).getStatus(), SUCCESS);
        Assert.assertEquals(csv.dissolveTrain(2).getStatus(), SUCCESS);
    }

    @After
    public void delRecord() {
        Assert.assertEquals(csv.delDriverById(1).getStatus(), SUCCESS);
        Assert.assertEquals(csv.delDriverById(2).getStatus(), SUCCESS);
        Assert.assertEquals(csv.delFreightById(1).getStatus(), SUCCESS);
        Assert.assertEquals(csv.delFreightById(2).getStatus(), SUCCESS);
        Assert.assertEquals(csv.delPassengerById(1).getStatus(), SUCCESS);
        Assert.assertEquals(csv.delPassengerById(2).getStatus(), SUCCESS);
    }

}