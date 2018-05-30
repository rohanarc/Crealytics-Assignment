package com.crealytics.assignment.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.crealytics.assignment.model.AggregateReport;
import com.crealytics.assignment.model.PublisherAdvertising;
import com.crealytics.assignment.utils.ApplicationException;

/**
 * basic report service tests
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReportServiceTests {

	private @InjectMocks ReportsService reportService;

	@Mock
	MongoTemplate template;

	List<PublisherAdvertising> reports = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		PublisherAdvertising dto = new PublisherAdvertising();
		dto.setMonth("January");
		dto.setSite("android");
		dto.setClicks(10);
		dto.setConversions(100);
		dto.setRequests(100);
		dto.setRevenue(10.0);
		dto.setImpressions(1000L);
		reports = new ArrayList<PublisherAdvertising>();
		reports.add(dto);
		Query query = new Query();
		query.addCriteria(Criteria.where("site").is("android"));
		query.addCriteria(Criteria.where("month").is("January"));
		Mockito.when(template.find(query, PublisherAdvertising.class, "Advertising_Report")).thenReturn(reports);
	}

	@Test
	public void findMonthTest() {
		String month = ReportsService.findMonth("Jan");
		assertEquals(month, "January");
		month = ReportsService.findMonth("1");
		assertEquals(month, "January");
	}

	@Test
	public void findBySiteAndMonth_valid() {
		AggregateReport report = reportService.findBySiteAndMonth("android", "1");
		assertEquals(report.getCr().intValue(), 10);
	}

	@Test(expected = ApplicationException.class)
	public void findBySiteAndMonth_ApplicationException() {
		reportService.findBySiteAndMonth("aa", "1");
	}
}
