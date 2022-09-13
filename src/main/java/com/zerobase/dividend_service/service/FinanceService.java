package com.zerobase.dividend_service.service;

import com.zerobase.dividend_service.model.Company;
import com.zerobase.dividend_service.model.Dividend;
import com.zerobase.dividend_service.model.ScrapedResult;
import com.zerobase.dividend_service.persist.entity.CompanyEntity;
import com.zerobase.dividend_service.persist.entity.DividendEntity;
import com.zerobase.dividend_service.persist.repository.CompanyRepository;
import com.zerobase.dividend_service.persist.repository.DividendRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FinanceService {

	private final CompanyRepository companyRepository;
	private final DividendRepository dividendRepository;

	public ScrapedResult getDividendByCompanyName(String companyName) {

		// 1. 회사명을 기준으로 회사 정보 조회
		CompanyEntity company = companyRepository.findByName(companyName)
								.orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

		// 2. 조회된 회사 ID 로 배당금 정보 조회
		List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(company.getId());

		// 3. 결과 조합 후 반환
//		List<Dividend> dividends = new ArrayList<>();
//		for(var entity : dividendEntities) {
//			dividends.add(Dividend.builder()
//									.date(entity.getDate())
//									.dividend(entity.getDividend())
//									.build());
//		}

		List<Dividend> dividends = dividendEntities.stream()
													.map(e -> Dividend.builder()
														.date(e.getDate())
														.dividend(e.getDividend())
														.build())
														.collect(Collectors.toList());

		return new ScrapedResult(Company.builder()
										.ticker(company.getTicker())
										.name(company.getName())
										.build(), dividends);
	}
}
