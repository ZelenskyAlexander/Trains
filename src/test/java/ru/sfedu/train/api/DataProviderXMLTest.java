package ru.sfedu.train.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.train.models.Train;

import static ru.sfedu.train.constants.Status.SUCCESS;
import static ru.sfedu.train.constants.WagonType.FREIGHT;
import static ru.sfedu.train.constants.WagonType.PASSENGER;

public class DataProviderXMLTest {

    IDataProvider xml = new DataProviderXML();
    Fill f = new Fill();

    @Before
    public void saveRecord() {
        Assert.assertEquals(xml.appendDriver(f.driver1()).getStatus(), SUCCESS);
        Assert.assertEquals(xml.appendDriver(f.driver2()).getStatus(), SUCCESS);

        Assert.assertEquals(xml.appendFreight(f.freight1()).getStatus(), SUCCESS);
        Assert.assertEquals(xml.appendFreight(f.freight2()).getStatus(), SUCCESS);

        Assert.assertEquals(xml.appendPassenger(f.passenger1()).getStatus(), SUCCESS);
        Assert.assertEquals(xml.appendPassenger(f.passenger2()).getStatus(), SUCCESS);
    }

    @Test
    public void getRecord() {
        Assert.assertNotNull(xml.getDriverById(1));
        System.out.println(xml.getDriverById(1));
        Assert.assertNotNull(xml.getDriverById(2));
        System.out.println(xml.getDriverById(2));

        Assert.assertNotNull(xml.getFreightById(1));
        System.out.println(xml.getFreightById(1));
        Assert.assertNotNull(xml.getFreightById(2));
        System.out.println(xml.getFreightById(2));

        Assert.assertNotNull(xml.getPassengerById(1));
        System.out.println(xml.getPassengerById(1));
        Assert.assertNotNull(xml.getPassengerById(2));
        System.out.println(xml.getPassengerById(2));
    }

    @Test
    public void initTrain() {
        Train train1 = new Train();
        train1.setId(1);
        train1.setDriver(xml.getDriverById(1));
        train1.setWagon(xml.getWagonById(FREIGHT,1));
        train1.setType(FREIGHT.toString());
//
        Assert.assertEquals(xml.installTrain(train1).getStatus(), SUCCESS);
        Assert.assertEquals(xml.hookWagon(1, 2).getStatus(), SUCCESS);
        System.out.println(xml.getTrainById(1));

        Train train2 = new Train();
        train2.setId(2);
        train2.setDriver(xml.getDriverById(2));
        train2.setWagon(xml.getWagonById(PASSENGER,1));
        train2.setType(PASSENGER.toString());
//
        Assert.assertEquals(xml.installTrain(train2).getStatus(), SUCCESS);
        Assert.assertEquals(xml.hookWagon(2, 2).getStatus(), SUCCESS);
        System.out.println(xml.getTrainById(2));

        Assert.assertEquals(xml.unhookWagon(1, 2).getStatus(), SUCCESS);
        Assert.assertEquals(xml.unhookWagon(1, 1).getStatus(), SUCCESS);
        Assert.assertEquals(xml.dissolveTrain(2).getStatus(), SUCCESS);
    }

    @After
    public void delRecord() {
        Assert.assertEquals(xml.delDriverById(1).getStatus(), SUCCESS);
        Assert.assertEquals(xml.delDriverById(2).getStatus(), SUCCESS);
        Assert.assertEquals(xml.delFreightById(1).getStatus(), SUCCESS);
        Assert.assertEquals(xml.delFreightById(2).getStatus(), SUCCESS);
        Assert.assertEquals(xml.delPassengerById(1).getStatus(), SUCCESS);
        Assert.assertEquals(xml.delPassengerById(2).getStatus(), SUCCESS);
    }

}