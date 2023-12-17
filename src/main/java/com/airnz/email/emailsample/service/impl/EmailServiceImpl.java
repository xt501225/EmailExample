package com.airnz.email.emailsample.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airnz.email.emailsample.assembler.DataObjectAssembler;
import com.airnz.email.emailsample.assembler.DtoAssembler;
import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.exception.NotFoundResourceException;
import com.airnz.email.emailsample.model.command.EmailRequestCommand;
import com.airnz.email.emailsample.model.dataObject.EmailDO;
import com.airnz.email.emailsample.model.dto.EmailResponse;
import com.airnz.email.emailsample.model.dto.MultiResponseDto;
import com.airnz.email.emailsample.model.dto.ResponseDto;
import com.airnz.email.emailsample.model.dto.SingleResponseDto;
import com.airnz.email.emailsample.repository.IEmailRepository;
import com.airnz.email.emailsample.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {

    private static Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private DtoAssembler dtoAssembler;

    @Autowired
    private DataObjectAssembler dataObjectAssembler;

    @Autowired
    private IEmailRepository emailRepository;


    @Override
    public ResponseDto getInBoxEmails(EmailRequestCommand requestCommand) {

        String emailAddress = requestCommand.getFomrAddress();

        List<EmailDO> emailDOs = emailRepository.getEmailsByAddressAndType(emailAddress, EmailType.RECEIVED);
        List<EmailResponse> response = emailDOs.stream().map(emailDO -> dtoAssembler.dataObjectToDto(emailDO))
                .collect(Collectors.toList());
        return MultiResponseDto.of(response);
    }

    @Override
    public ResponseDto getEmailById(EmailRequestCommand requestCommand) {
        String emailId = requestCommand.getEmailId();
        EmailDO emailDO = emailRepository.getEmailById(emailId);
        EmailResponse response = dtoAssembler.dataObjectToDto(emailDO);
        response.setEmailType(emailDO.getEmailType());
        return SingleResponseDto.of(response);
    }

    @Override
    public ResponseDto sendEmail(EmailRequestCommand reqCommand) {
        LOG.info("Sending Email via Email Server");
        EmailDO sentBoxDO = dataObjectAssembler.toSentBoxDataObject(reqCommand);
        List<EmailDO> inboxDOs = dataObjectAssembler.toInBoxDataObject(sentBoxDO);

        String draftEmailId = reqCommand.getEmailId();
        if (draftEmailId != null && !draftEmailId.isEmpty()) {
            deleteDraftEmail(draftEmailId);
        }

        saveEmail(sentBoxDO, inboxDOs);

        EmailResponse res = new EmailResponse();
        res.setId(sentBoxDO.getId());
        return SingleResponseDto.of(res);
    }

    private void deleteDraftEmail(String draftEmailId) {
        EmailDO draftEmailDO = this.emailRepository.getEmailById(draftEmailId);
        if (draftEmailDO == null) {
            LOG.error("Can not find any Email with id: {}" + draftEmailId);
            throw new NotFoundResourceException("can not find any Email with id:" + draftEmailId);
        } else {
            emailRepository.delete(draftEmailDO);
        }
    }

    private void saveEmail(EmailDO sentBoxDO, List<EmailDO> inboxDOs) {
        String mailId = emailRepository.save(sentBoxDO);
        inboxDOs.forEach(inboxDO -> inboxDO.setOriginalId(mailId));
        emailRepository.save(inboxDOs);
    }

    @Override
    public ResponseDto saveDraftEmail(EmailRequestCommand reqCommand) {
        EmailDO draftBoxDO = dataObjectAssembler.toDraftBoxDataObject(reqCommand);
        String mailId = emailRepository.save(draftBoxDO);
        LOG.info("Saved Draft Email with id: {}", mailId);
        EmailResponse res = new EmailResponse();
        res.setId(mailId);
        return SingleResponseDto.of(res);
    }

    @Override
    public ResponseDto updateDraftEmail(EmailRequestCommand reqCommand) {
        String mailId = reqCommand.getEmailId();
        EmailDO draftBoxDO = emailRepository.getEmailById(mailId);
        if (draftBoxDO == null) {
            LOG.error("Can not find any Email with id: {}" + mailId);
            throw new NotFoundResourceException("can not find any Email with id:" + mailId);
        }

        EmailDO newDraftBoxDO = dataObjectAssembler.toDraftBoxDataObject(reqCommand);
        newDraftBoxDO.setId(draftBoxDO.getId());
        EmailDO emailDO = emailRepository.update(newDraftBoxDO);
        LOG.info("Updated Draft Email with id: {}", draftBoxDO.getId());
        EmailResponse response = dtoAssembler.dataObjectToDto(emailDO);
        return SingleResponseDto.of(response);
    }
}