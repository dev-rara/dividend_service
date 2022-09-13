package com.zerobase.dividend_service.service;

import com.zerobase.dividend_service.model.Company;
import com.zerobase.dividend_service.model.ScrapedResult;
import com.zerobase.dividend_service.persist.entity.CompanyEntity;
import com.zerobase.dividend_service.persist.entity.DividendEntity;
import com.zerobase.dividend_service.persist.repository.CompanyRepository;
import com.zerobase.dividend_service.persist.repository.DividendRepository;
import com.zerobase.dividend_service.scraper.Scraper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class CompanyService {

	private final Trie trie;
	private final Scraper yahooFinanceScraper;
	private final CompanyRepository companyRepository;
	private final DividendRepository dividendRepository;

	public Company save(String ticker) {

 		boolean exists= companyRepository.existsByTicker(ticker);
		if (exists) {
			throw new RuntimeException("already exists ticker -> " + ticker);
		}

		 return storeCompanyAndDividend(ticker);
	}

	public Page<CompanyEntity> getAllCompany(Pageable pageable) {
		return companyRepository.findAll(pageable);
	}

	public void addAutocompleteKeyword(String keyword) {
		trie.put(keyword, null);
	}

	// 자동완성 Trie 방식
	public List<String> autocomplete(String keyword) {
		return (List<String>) trie.prefixMap(keyword).keySet()
									.stream()
									.limit(10)
									.collect(Collectors.toList());
	}

	// 자동완성 Like 방식
	public List<String> getCompanyNamesByKeyword(String keyword) {
		Pageable limit = PageRequest.of(0, 10);
		Page<CompanyEntity> companyEntities = companyRepository.findByNameStartingWithIgnoreCase(keyword, limit);
		return companyEntities.stream()
						.map(e -> e.getName())
						.collect(Collectors.toList());
	}

	public void deleteAutocompleteKeyword(String keyword) {
		trie.remove(keyword);
	}

	private Company storeCompanyAndDividend(String ticker) {
		// ticker 를 기준으로 회사를 스크래핑
		 Company company = yahooFinanceScraper.scrapCompanyByTicker(ticker);
		if (ObjectUtils.isEmpty(company)) {
			throw new RuntimeException("failed to scrap ticker -> " + ticker);
		}

		// 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
		ScrapedResult scrapedResult = yahooFinanceScraper.scrap(company);

		// 스크래핑 결과
		CompanyEntity companyEntity = companyRepository.save(new CompanyEntity(company));
		List<DividendEntity> dividendEntities  = scrapedResult.getDividends().stream()
														.map(e -> new DividendEntity(companyEntity.getId(), e))
														.collect(Collectors.toList());

		dividendRepository.saveAll(dividendEntities);
		return company;
	}

}
