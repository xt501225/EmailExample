package com.airnz.email.emailsample.repository;

import java.util.List;

import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.model.dataObject.EmailDO;

public interface IEmailRepository {

    EmailDO getEmailById(String id);

    List<EmailDO> getEmailsByAddressAndType(String emailAddress, EmailType emailType);

    String save(EmailDO emailDO);

    List<String> save(List<EmailDO> emailDOs);

    EmailDO update(EmailDO emailDO);

    boolean delete(EmailDO emailDO);
}
