package com.airnz.email.emailsample.service;

import com.airnz.email.emailsample.model.command.EmailRequestCommand;
import com.airnz.email.emailsample.model.dto.ResponseDto;

public interface IEmailService {

    ResponseDto getInBoxEmails(EmailRequestCommand requestCommand);

    ResponseDto getEmailById(EmailRequestCommand requestCommand);

    ResponseDto sendEmail(EmailRequestCommand requestCommand);

    ResponseDto saveDraftEmail(EmailRequestCommand requestCommand);

    ResponseDto updateDraftEmail(EmailRequestCommand requestCommand);

}
