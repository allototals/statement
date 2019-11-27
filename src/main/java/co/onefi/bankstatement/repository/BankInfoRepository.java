package co.onefi.bankstatement.repository;

import co.onefi.bankstatement.entities.BankInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BankInfoRepository extends PagingAndSortingRepository<BankInfoEntity, Integer> {

    BankInfoEntity findFirstByBankCode(String bankCode);

}
