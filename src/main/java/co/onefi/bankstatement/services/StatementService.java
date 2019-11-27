package co.onefi.bankstatement.services;

import co.onefi.bankstatement.entities.StatementEntity;

public interface StatementService {
    // Method to retrieve last bank statement uploaded by client Id

    StatementEntity findTop1ByClientIdOrderByUpdatedAtDesc(String clientId);

    // Method to save bank statement
    StatementEntity saveBankStatement(StatementEntity statements);

    // Method to retrieve bank statement by clientId or account No
    StatementEntity getLatestBankStatement(String clientId, String accountNumber);

    //Method to retrive MD5 Hash checksum
    StatementEntity findOneByFileHash(String fileHash);
}
