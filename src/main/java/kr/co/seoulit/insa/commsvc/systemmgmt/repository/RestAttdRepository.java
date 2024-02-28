package kr.co.seoulit.insa.commsvc.systemmgmt.repository;

import kr.co.seoulit.insa.attdsvc.attdmgmt.entity.RestAttdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RestAttdRepository extends JpaRepository<RestAttdEntity, String> {
}
