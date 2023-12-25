package accounts.service;

import accounts.domain.Account;
import accounts.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTests {
    @TestConfiguration
    static class AccountServiceImplTestContextConfiguration{
        @Bean
        public AccountService accountService(){
            return new AccountService();
        }
    }
    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Before
    public void setUp(){
        String accountNumber="001";

        Account account= new Account("001",3000.00,"Mr.John");
        Account account1= new Account("001",3000.00,"Mr.John");
        Optional<Account> johnAccount= Optional.of(account);
        //getAccountNumber
        Mockito.when(accountRepository.findById(accountNumber)).thenReturn(johnAccount);
        //createAccount
        Mockito.when(accountRepository.save(account1)).thenReturn(account);
    }
    @Test
    public void getAccountNumber_ReturnAccountNumber(){
        //given
        String accountNumber="001";
        //when
        AccountResponse accountResponse= accountService.getAccount(accountNumber);
        //Then
        Assert.assertEquals(accountResponse.getAccountNumber(),accountNumber);
    }
    @Test
    public void createAccount_WithoutError() throws Exception{
        //given
        Account account= new Account("001",3000.00,"Mr.John");
        //when
        accountService.createAccount(account.getAccountNumber(), account.getBalance(),account.getAccountHolder());
        //Then
        verify(accountRepository,times(1)).save(account);
    }

}
