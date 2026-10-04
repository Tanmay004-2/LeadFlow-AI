package com.leadflow.account.service;

     import com.leadflow.account.dto.AccountDto;
     import com.leadflow.account.entity.Account;
     import com.leadflow.account.repository.AccountRepository;
     import lombok.RequiredArgsConstructor;
     import org.springframework.stereotype.Service;






     import org.springframework.transaction.annotation.Transactional;

     import java.util.List;
     import java.util.stream.Collectors;

     @Service
     @RequiredArgsConstructor
     public class AccountService {

           private final AccountRepository accountRepository;

           @Transactional(readOnly = true)
           public List<AccountDto> getAllAccounts() {
               return accountRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
           }

           private AccountDto mapToDto(Account account) {
               AccountDto dto = new AccountDto();
               dto.setId(account.getId());
               dto.setName(account.getName());
               return dto;
           }
     }
