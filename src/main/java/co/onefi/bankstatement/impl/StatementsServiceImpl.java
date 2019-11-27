package co.onefi.bankstatement.impl;

//import co.onefi.bankstatement.Entities.StatementsEntity;
import co.onefi.bankstatement.entities.StatementEntity;
import co.onefi.bankstatement.repository.StatementRepository;
import co.onefi.bankstatement.services.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatementsServiceImpl implements StatementService {

    @Autowired
    private StatementRepository statementsRepository;

    @Override
    public StatementEntity findTop1ByClientIdOrderByUpdatedAtDesc(String clientId) {
        return statementsRepository.findFirstByClientIdOrderByEndStatementDateTimeDesc(clientId);
    }

    @Override
    public StatementEntity saveBankStatement(StatementEntity statements) {
        return statementsRepository.save(statements);
    }

    @Override
    public StatementEntity getLatestBankStatement(String clientId, String accountNumber) {
        return statementsRepository.findOneByClientIdOrAccountNumberOrderByUpdatedAtDesc(clientId, accountNumber);
    }

    @Override
    public StatementEntity findOneByFileHash(String fileHash) {
        return statementsRepository.findOneByFileHash(fileHash);
    }
}
