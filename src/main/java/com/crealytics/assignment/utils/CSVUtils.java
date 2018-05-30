package com.crealytics.assignment.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.crealytics.assignment.model.PublisherAdvertising;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class CSVUtils {

	@Autowired
	private ResourceLoader resourceLoader;
	/**
	 * Parse csv file and create domain objects
	 * @param fileName
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public List<PublisherAdvertising> parseFile(String fileName) throws IOException, URISyntaxException {
		try (Reader reader = new BufferedReader(new InputStreamReader(resourceLoader.getResource("classpath:"+fileName).getInputStream()));) {
			CsvToBean<PublisherAdvertising> csvToBean = new CsvToBeanBuilder<PublisherAdvertising>(reader)
					.withType(PublisherAdvertising.class).withIgnoreLeadingWhiteSpace(true).build();
			List<PublisherAdvertising> reports = csvToBean.parse();
			return reports;
		}
	}
}
