package com.rara.dividend_service.service;

import com.rara.dividend_service.model.ScrapedResult;

public interface FinanceService {

	ScrapedResult getDividendByCompanyName(String companyName);
}
