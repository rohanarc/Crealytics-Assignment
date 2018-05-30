package com.crealytics.assignment.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crealytics.assignment.model.PublisherAdvertising;

/**
 * CSV parser utility tests
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVUtilsTest {

    @Autowired
    private CSVUtils utils;

    @Test()
    public void validDataTest() throws IOException, URISyntaxException{
    	List<PublisherAdvertising> reports=utils.parseFile("2018_01_report.csv");
    	assertEquals(reports.get(0).getClicks(), new Integer(30965));
    }
}
