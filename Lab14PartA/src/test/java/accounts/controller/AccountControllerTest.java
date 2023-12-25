package accounts.controller;

import accounts.domain.Account;
import accounts.service.AccountResponse;
import accounts.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountService accountService;

    @Test
    public void testGetAccountByAccountNumber()throws Exception{
        Mockito.when(accountService.getAccount("001")).thenReturn(new AccountResponse("001",3000.00,"Mr.Tonny"));

        mockMvc.perform(MockMvcRequestBuilders.get("/account/001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(3000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountHolder").value("Mr.Tonny"));
    }

    @Test
    public void testCreateAccount() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/createaccount/001/3000.00/Mr.Tony"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(accountService,times(1)).createAccount("001",3000.00,"Mr.Tony");
    }
}
