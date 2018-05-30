package com.crealytics.assignment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crealytics.assignment.model.AggregateReport;
import com.crealytics.assignment.service.ReportsService;

@RestController
public class AssignmentController {

	@Autowired
	ReportsService service;

	@RequestMapping(value = "ingestdata/", method = RequestMethod.GET)
	public ResponseEntity<Integer> ingest() {
		try {
			service.clearData();
			//ingesting files from resources
			service.importFile("2018_01_report.csv");
			service.importFile("2018_02_report.csv");
			return new ResponseEntity<Integer>(200, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Integer>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = { "reports" }, method = RequestMethod.GET)
	public ResponseEntity<?> getReportBySiteAndMonth(@RequestParam Optional<String> site,
			@RequestParam Optional<String> month) {
		String siteToPass = site.isPresent() ? site.get() : null;
		String monthToPass = month.isPresent() ? month.get() : null;
		AggregateReport data = service.findBySiteAndMonth(siteToPass, monthToPass);
		return new ResponseEntity<AggregateReport>(data, HttpStatus.OK);
	}

}
