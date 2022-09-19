package com.zerobase.dividend_service.service;

import com.zerobase.dividend_service.model.ScrapedResult;

public interface FinanceService {

	ScrapedResult getDividendByCompanyName(String companyName);
}
