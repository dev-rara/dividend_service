package com.zerobase.dividend_service.scraper;

import com.zerobase.dividend_service.model.Company;
import com.zerobase.dividend_service.model.ScrapedResult;

public interface Scraper {
	Company scrapCompanyByTicker(String ticker);
	ScrapedResult scrap(Company company);
}
