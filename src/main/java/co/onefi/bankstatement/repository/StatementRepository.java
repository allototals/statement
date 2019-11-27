package co.onefi.bankstatement.repository;

import co.onefi.bankstatement.entities.StatementEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends PagingAndSortingRepository<StatementEntity, Integer> {

    // Method to retrive last bank statements uploaded
    StatementEntity findFirstByClientIdOrderByEndStatementDateTimeDesc(String clientId);

    StatementEntity findOneByFileHash(String fileHash);

    StatementEntity findOneByClientIdOrAccountNumberOrderByUpdatedAtDesc(String clientId, String accountNumber);
}
