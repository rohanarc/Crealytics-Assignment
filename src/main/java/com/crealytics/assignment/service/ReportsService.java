package com.crealytics.assignment.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.crealytics.assignment.model.AggregateReport;
import com.crealytics.assignment.model.PublisherAdvertising;
import com.crealytics.assignment.utils.ApplicationException;
import com.crealytics.assignment.utils.CSVUtils;

@Service
public class ReportsService {

	@Autowired
	private MongoTemplate template;

	@Autowired
	CSVUtils utils;

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * utility method to clear data from Mongo DB
	 */
	public void clearData() {
		template.remove(new Query(), "Advertising_Report");
	}

	/**
	 * import file contents to DB
	 * 
	 * @param fileName
	 * @return
	 */
	public void importFile(String fileName) {
		try {
			log.info("File name:"+fileName);
			String month = getMonthFromFileName(fileName);
			log.info("month:"+month);
			List<PublisherAdvertising> reports = utils.parseFile(fileName);
			if (reports != null && !reports.isEmpty()) {
				reports.forEach(report -> report.setMonth(month));
				template.insert(reports, "Advertising_Report");
				log.info("imported to DB");
			}
		} catch (IOException e) {
			log.error("Error reading file:" + e.getMessage());
		} catch (URISyntaxException ue) {
			log.error("File name could not be parsed as a URI reference." + ue.getMessage());
		}
	}

	/**
	 * search query based on criteria will return aggregated report.
	 * 
	 * @param site
	 * @param month
	 * @return
	 * @throws ApplicationException
	 */
	public AggregateReport findBySiteAndMonth(String site, String month) throws ApplicationException {
		String reportingMonth = findMonth(month);
		Query query = new Query();
		if (site != null && !site.isEmpty()) {
			query.addCriteria(Criteria.where("site").is(site));
		}
		if (month != null && !month.isEmpty()) {
			query.addCriteria(Criteria.where("month").is(reportingMonth));
		}
		List<PublisherAdvertising> reports = template.find(query, PublisherAdvertising.class, "Advertising_Report");
		if (reports != null && !reports.isEmpty()) {
			return new AggregateReport(reports, site, reportingMonth);
		} else {
			throw new ApplicationException("No data found for reporting period or site.");
		}
	}

	/**
	 * Based on the input will return full month description 1-> January, Jan or
	 * jan -> January
	 * 
	 * @param month
	 * @return
	 */
	public static String findMonth(String month) {
		Month mon = null;
		if (month != null && !month.isEmpty()) {
			try {
				mon = Stream.of(Month.values()).map(m -> Month.of(Integer.parseInt(month))).findFirst().orElse(null);
			} catch (NumberFormatException ex) {
				// do nothing
			}
			if (mon == null) {
				mon = Stream.of(Month.values())
						.filter(value -> value.name().toLowerCase().contains(month.toLowerCase())).findFirst()
						.orElse(null);
				if (mon == null) {
					return null;
				}
			}
			return mon.getDisplayName(TextStyle.FULL, Locale.getDefault());
		}
		return null;
	}

	private String getMonthFromFileName(String fileName) {
		String month = null;
		if (fileName != null && !fileName.isEmpty()) {
			String monthNo = fileName.substring(5, 7);
			int mon = Integer.parseInt(monthNo);
			month = Month.of(mon).getDisplayName(TextStyle.FULL, Locale.getDefault());
		}
		return month;
	}
}
