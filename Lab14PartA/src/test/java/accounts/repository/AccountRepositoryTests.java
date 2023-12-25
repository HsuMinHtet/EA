package accounts.repository;

import accounts.domain.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTests {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void whenFindByAccountHolder_thenReturnAccount(){
        //given
        Account account= new Account("001",3000.00,"Mr.John");
        testEntityManager.persist(account);
        testEntityManager.flush();
        //when
        Account found= accountRepository.findByAccountHolder(account.getAccountHolder());
        Assert.assertEquals(account.getAccountNumber(),found.getAccountNumber());
    }

    @Test
    public void whenFindByAccountNumber_thenReturnAccount(){
        //given
        Account account= new Account("001",3000.00,"Mr.John");
        testEntityManager.persist(account);
        testEntityManager.flush();
        //when
        Account found= accountRepository.findByAccountNumber(account.getAccountNumber());
        Assert.assertEquals(account.getAccountNumber(),found.getAccountNumber());
    }

    @Test
    public void whenFindByAccountHolderEndingWithIgnoreCase_thenReturnAccountList(){
        //given
        List<Account> accounts= new ArrayList<>();
        Account account1= new Account("001",3000.00,"Mr.John");
        Account account2= new Account("002",6000.00,"Mr.Tann");
        accounts.add(account1);
        accounts.add(account2);
        testEntityManager.persist(account1);
        testEntityManager.persist(account2);
        testEntityManager.flush();
        //when
        List<Account> found= accountRepository.findByAccountHolderEndingWithIgnoreCase("n");
       //Then
        Assert.assertEquals(found.size(),accounts.size());
    }


}

