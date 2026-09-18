package com.chaitanya.dto;

import lombok.Data;

@Data
public class DashboardResponse {
	private Integer totalEnquiries;
	private Integer openEnquiries;
	private Integer enrolledEnquiries;
	private Integer lostEnquiries;
}
