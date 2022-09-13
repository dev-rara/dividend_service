package com.zerobase.dividend_service.persist.repository;

import com.zerobase.dividend_service.persist.entity.DividendEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
	List<DividendEntity> findAllByCompanyId(Long id);
}
