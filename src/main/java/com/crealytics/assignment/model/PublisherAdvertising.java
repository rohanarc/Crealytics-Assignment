package com.crealytics.assignment.model;

import com.opencsv.bean.CsvBindByName;

public class PublisherAdvertising {
	@CsvBindByName(column = "site")
	private String site;

	@CsvBindByName(column = "requests")
	private Integer requests;

	@CsvBindByName(column = "impressions")
	private Long impressions;

	@CsvBindByName(column = "clicks")
	private Integer clicks;

	@CsvBindByName(column = "conversions")
	private Integer conversions;

	@CsvBindByName(column = " revenue (USD)")
	private Double revenue;
	
	private String month;

	public PublisherAdvertising() {

	}
		
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getRequests() {
		return requests;
	}

	public void setRequests(Integer requests) {
		this.requests = requests;
	}

	public Long getImpressions() {
		return impressions;
	}

	public void setImpressions(Long impressions) {
		this.impressions = impressions;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getConversions() {
		return conversions;
	}

	public void setConversions(Integer conversions) {
		this.conversions = conversions;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
