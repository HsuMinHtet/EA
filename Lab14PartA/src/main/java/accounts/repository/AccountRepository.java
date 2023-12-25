package accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accounts.domain.Account;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, String>{
   Account findByAccountHolder(String accountHolder);

   Account findByAccountNumber(String accountNumber);

   List<Account> findByAccountHolderEndingWithIgnoreCase(String word);

}
