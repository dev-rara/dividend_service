package com.rara.dividend_service.scraper;

import com.rara.dividend_service.model.Company;
import com.rara.dividend_service.model.ScrapedResult;

public interface Scraper {
	Company scrapCompanyByTicker(String ticker);
	ScrapedResult scrap(Company company);
}
