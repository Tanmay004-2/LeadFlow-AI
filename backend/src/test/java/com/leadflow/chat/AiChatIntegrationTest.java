package com.leadflow.chat;

     import com.leadflow.account.entity.Account;
     import com.leadflow.account.repository.AccountRepository;
     import com.leadflow.business.entity.Business;
     import com.leadflow.business.repository.BusinessRepository;
     import com.leadflow.campaign.entity.Campaign;
     import com.leadflow.campaign.repository.CampaignRepository;
     import com.leadflow.chat.entity.Conversation;
     import com.leadflow.chat.entity.Lead;
     import com.leadflow.chat.repository.ConversationRepository;
     import com.leadflow.chat.repository.LeadRepository;
     import com.leadflow.chat.service.AiServiceClient;
     import com.leadflow.security.JwtService;
     import com.leadflow.user.entity.Role;
     import com.leadflow.user.entity.User;
     import org.junit.jupiter.api.BeforeEach;
     import org.junit.jupiter.api.Test;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
     import org.springframework.boot.test.context.SpringBootTest;
     import org.springframework.boot.test.mock.mockito.MockBean;
     import org.springframework.http.MediaType;
     import org.springframework.test.web.servlet.MockMvc;

     import java.util.HashMap;
     import java.util.UUID;

     import static org.mockito.ArgumentMatchers.any;
     import static org.mockito.ArgumentMatchers.anyInt;
     import static org.mockito.ArgumentMatchers.anyString;
     import static org.mockito.Mockito.when;
     import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
     import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;





     import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

     @SpringBootTest
     @AutoConfigureMockMvc
     public class AiChatIntegrationTest {

           @Autowired private MockMvc mockMvc;
           @Autowired private AccountRepository accountRepository;
           @Autowired private BusinessRepository businessRepository;
           @Autowired private CampaignRepository campaignRepository;
           @Autowired private LeadRepository leadRepository;
           @Autowired private ConversationRepository conversationRepository;
           @Autowired private JwtService jwtService;

           @MockBean private AiServiceClient aiServiceClient;

           private UUID conversationId;
           private String customerToken;

           @BeforeEach
           void setup() {
               conversationRepository.deleteAll();
               leadRepository.deleteAll();
               campaignRepository.deleteAll();
               businessRepository.deleteAll();
               accountRepository.deleteAll();

                Account account = new Account(); account.setName("Test Account"); accountRepository.save(account);
                Business business = new Business(); business.setName("Test Biz"); business.setAccount(account); businessRepository.save(business);
                Campaign campaign = new Campaign(); campaign.setBusinessId(business.getId()); campaign.setName("Test Camp"); campaignRepository.save(campaign);

                UUID sessionId = UUID.randomUUID();
                Lead lead = new Lead(); lead.setBusinessId(business.getId()); lead.setCampaignId(campaign.getId()); lead.setSessionId(sessionId); leadRepository.save

                Conversation conversation = new Conversation(); conversation.setBusinessId(business.getId()); conversation.setLeadId(lead.getId()); conversationRepos
                conversationId = conversation.getId();

                User customerUser = new User(); customerUser.setId(sessionId); customerUser.setBusinessId(business.getId()); customerUser.setRole(Role.CUSTOMER); cust
                customerToken = jwtService.generateToken(customerUser);
           }

           @Test
           void processCustomerMessage_ShouldReturnAiResponse() throws Exception {
               AiServiceClient.AiResponse mockResponse = new AiServiceClient.AiResponse();
               mockResponse.setIntent("GENERAL_INQUIRY");
               mockResponse.setExtractedEntities(new HashMap<>());
               mockResponse.setNewScore(10);
               mockResponse.setResponseMessage("Hello, how can I help?");
               mockResponse.setRequiresHandoff(false);
               mockResponse.setAction("NONE");

                when(aiServiceClient.processMessage(anyString(), anyInt(), any(), any())).thenReturn(mockResponse);







                String payload = "{\"content\": \"Hi there\"}";

                mockMvc.perform(post("/api/v1/customer/chat/" + conversationId)
                        .header("Authorization", "Bearer " + customerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.content").value("Hello, how can I help?"));
           }
     }
