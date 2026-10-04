package com.leadflow.sales;

     import com.leadflow.account.entity.Account;
     import com.leadflow.account.repository.AccountRepository;
     import com.leadflow.business.entity.Business;
     import com.leadflow.business.repository.BusinessRepository;
     import com.leadflow.chat.entity.Conversation;
     import com.leadflow.chat.repository.ConversationRepository;
     import com.leadflow.sales.entity.AssignmentSettings;
     import com.leadflow.sales.repository.AssignmentSettingsRepository;
     import com.leadflow.sales.service.AgentAssignmentService;
     import com.leadflow.user.entity.Role;
     import com.leadflow.user.entity.User;
     import com.leadflow.user.repository.UserRepository;
     import org.junit.jupiter.api.BeforeEach;
     import org.junit.jupiter.api.Test;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.boot.test.context.SpringBootTest;

     import java.util.UUID;

     import static org.assertj.core.api.Assertions.assertThat;

     @SpringBootTest
     public class SalesRoutingIntegrationTest {

           @Autowired private UserRepository userRepository;
           @Autowired private ConversationRepository conversationRepository;
           @Autowired private AssignmentSettingsRepository settingsRepository;
           @Autowired private AccountRepository accountRepository;
           @Autowired private BusinessRepository businessRepository;
           @Autowired private AgentAssignmentService assignmentService;

           private UUID businessId;

           @BeforeEach
           void setup() {
               conversationRepository.deleteAll();
               settingsRepository.deleteAll();
               userRepository.deleteAll();
               businessRepository.deleteAll();





                accountRepository.deleteAll();

                Account account = new Account(); account.setName("Agency"); accountRepository.save(account);
                Business business = new Business(); business.setName("Biz"); business.setAccount(account); businessRepository.save(business);
                businessId = business.getId();

                User agent1 = new User(); agent1.setBusinessId(businessId); agent1.setEmail("agent1@test.com"); agent1.setPassword("pass"); agent1.setRole(Role.SALES_
                User agent2 = new User(); agent2.setBusinessId(businessId); agent2.setEmail("agent2@test.com"); agent2.setPassword("pass"); agent2.setRole(Role.SALES_
           }

           @Test
           void roundRobinAssignment_ShouldDistributeEvenly() {
               Conversation convo1 = new Conversation(); convo1.setBusinessId(businessId); convo1.setLeadId(UUID.randomUUID()); conversationRepository.save(convo1);
               Conversation convo2 = new Conversation(); convo2.setBusinessId(businessId); convo2.setLeadId(UUID.randomUUID()); conversationRepository.save(convo2);

                AssignmentSettings settings = new AssignmentSettings();
                settings.setBusinessId(businessId);
                settings.setStrategy("ROUND_ROBIN");
                settingsRepository.save(settings);

                assignmentService.assignConversation(convo1, businessId);
                assignmentService.assignConversation(convo2, businessId);

                Conversation updatedConvo1 = conversationRepository.findById(convo1.getId()).orElseThrow();
                Conversation updatedConvo2 = conversationRepository.findById(convo2.getId()).orElseThrow();

                assertThat(updatedConvo1.getAgentId()).isNotNull();
                assertThat(updatedConvo2.getAgentId()).isNotNull();
                assertThat(updatedConvo1.getAgentId()).isNotEqualTo(updatedConvo2.getAgentId());
                assertThat(updatedConvo2.getStatus()).isEqualTo("HANDED_OFF");
           }
     }

     RECOVERY RESULT: Files Reconstructed: 8



     User prompt: The missing-file recovery is complete. Now continue SOURCE RECOVERY. Do NOT: The existing conversation is the source of truth. We now need the ACTUAL FILE CONTENTS of the already-
     generated project so I can reconstruct the repository locally. Recover the following batch ONLY: BATCH 1 — ROOT + INFRASTRUCTURE + DATABASE + CORE Backend configuration AI configuration Frontend
     configuration Flyway migrations Backend core Security Authentication/config RULES: Use exactly this format: FILE: <complete contents> Repeat for every file. At the end provide: BATCH 1 RESULT Then STOP. Do
     not output Batch 2 yet.

     Response: FILE: docker-compose.yml

     version: '3.8'

     services:
       postgres:
         image: postgres:15
         environment:
           POSTGRES_USER: leadflow_user
           POSTGRES_PASSWORD: password
           POSTGRES_DB: leadflow
         ports:





             - "5432:5432"
           volumes:
             - postgres_data:/var/lib/postgresql/data

        redis:
          image: redis:7
          ports:
            - "6379:6379"
          volumes:
            - redis_data:/data

     volumes:
       postgres_data:
       redis_data:
