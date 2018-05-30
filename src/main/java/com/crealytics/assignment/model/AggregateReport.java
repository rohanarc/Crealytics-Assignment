package com.crealytics.assignment.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class AggregateReport {

	private String month;

	private String site;

	private Integer requests;

	private Long impressions;

	private Integer clicks;

	private Integer conversions;

	private BigDecimal revenue;

	@JsonProperty("CTR")
	private BigDecimal ctr;

	@JsonProperty("CR")
	private BigDecimal cr;

	private BigDecimal fill_rate;

	private BigDecimal eCPM;

	public AggregateReport(Collection<PublisherAdvertising> reports, String site, String month) {
		this.setRequests(reports.stream().collect(Collectors.summingInt(o -> o.getRequests())));
		this.setImpressions(reports.stream().collect(Collectors.summingLong(o -> o.getImpressions())));
		this.setClicks(reports.stream().collect(Collectors.summingInt(o -> o.getClicks())));
		this.setConversions(reports.stream().collect(Collectors.summingInt(o -> o.getConversions())));
		this.setRevenue(new BigDecimal(reports.stream().collect(Collectors.summingDouble(o -> o.getRevenue())))
				.setScale(2, RoundingMode.HALF_UP));
		this.setSite(site);
		this.setMonth(month);
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

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getCtr() {
		if (getClicks() != null && getImpressions() != null) {
			this.ctr = new BigDecimal((double) getClicks() / getImpressions() * 100).setScale(2, RoundingMode.HALF_UP);
		} else {
			this.ctr = new BigDecimal(0);
		}
		return this.ctr;
	}

	public BigDecimal getCr() {
		if (getConversions() != null && getImpressions() != null) {
			this.cr = new BigDecimal((double) getConversions() / getImpressions() * 100).setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.cr = new BigDecimal(0);
		}
		return this.cr;
	}

	public BigDecimal getFill_rate() {
		if (getImpressions() != null && getRequests() != null) {
			this.fill_rate = new BigDecimal((double) getImpressions() / getRequests() * 100).setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.fill_rate = new BigDecimal(0);
		}
		return this.fill_rate;
	}

	public BigDecimal geteCPM() {
		if (getImpressions() != null && getRevenue() != null) {
			this.eCPM = new BigDecimal(getRevenue().doubleValue() * 10000 / getImpressions()).setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.eCPM = new BigDecimal(0);
		}
		return this.eCPM;
	}

}
