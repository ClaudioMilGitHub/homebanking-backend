package com.example.demo.mapper;

import com.example.demo.dto.account.AccountDTO;
import com.example.demo.dto.account.AccountRequestDTO;
import com.example.demo.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    public Account fromAccountDTO (AccountDTO dto);

    public AccountDTO toAccountDTO (Account account);

    public Account fromAccountRequestDTO (AccountRequestDTO dto);
}
