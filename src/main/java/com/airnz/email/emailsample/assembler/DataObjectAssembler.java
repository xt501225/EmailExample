package com.airnz.email.emailsample.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.airnz.email.emailsample.enums.EmailContentType;
import com.airnz.email.emailsample.enums.EmailRecipientType;
import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.model.command.EmailRequestCommand;
import com.airnz.email.emailsample.model.dataObject.EmailContentDO;
import com.airnz.email.emailsample.model.dataObject.EmailDO;
import com.airnz.email.emailsample.model.dataObject.EmailRecipientDO;
import com.airnz.email.emailsample.model.req.EmailRequest;

@Component
public class DataObjectAssembler {

    public EmailDO toSentBoxDataObject(EmailRequestCommand reqCommand) {
        EmailDO sentBoxDO = this.buildEmailDO(reqCommand.getFomrAddress(), reqCommand.getEmailRequest());
        sentBoxDO.setRecipientType(EmailRecipientType.FROM);
        sentBoxDO.setEmailType(EmailType.SENT);
        return sentBoxDO;
    }

    public EmailDO toDraftBoxDataObject(EmailRequestCommand reqCommand) {
        EmailDO draftDO = this.buildEmailDO(reqCommand.getFomrAddress(), reqCommand.getEmailRequest());
        draftDO.setRecipientType(EmailRecipientType.FROM);
        draftDO.setEmailType(EmailType.DRAFT);
        return draftDO;
    }

    private EmailDO buildEmailDO(String emailAddress, EmailRequest req) {
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress(emailAddress);
        emailDO.setFromAddress(emailAddress);
        emailDO.setSubject(req.getEmailBody().getSubject());
        emailDO.setEmailContents(req.getEmailBody().getContents().stream()
                .map(content -> new EmailContentDO(emailDO.getId(), EmailContentType.valueOf(content.getType()), content.getDetail()))
                .collect(Collectors.toList()));

        List<EmailRecipientDO> lst = new ArrayList();
        lst.addAll(req.getEmailRecipient().getRecipientTo().stream().map(t -> new EmailRecipientDO(emailDO.getId(),
                t.getEmailAddress(), EmailRecipientType.TO)).collect(Collectors.toList()));
        lst.addAll(req.getEmailRecipient().getRecipientCc().stream().map(t -> new EmailRecipientDO(emailDO.getId(),
                t.getEmailAddress(), EmailRecipientType.CC)).collect(Collectors.toList()));
        lst.addAll(req.getEmailRecipient().getRecipientBcc().stream().map(t -> new EmailRecipientDO(emailDO.getId(),
                t.getEmailAddress(), EmailRecipientType.BCC)).collect(Collectors.toList()));
        emailDO.setRecipientDOs(lst);
        emailDO.setCreatedAt(System.currentTimeMillis());
        return emailDO;
    }

    public List<EmailDO> toInBoxDataObject(EmailDO sentBoxDO) {
        List<EmailDO> inboxList = new ArrayList<>();
        sentBoxDO.getRecipientDOs().forEach(recipientDO -> {
            EmailDO inboxDO = new EmailDO();
            inboxDO.setEmailAddress(recipientDO.getEmailAddress());
            inboxDO.setFromAddress(sentBoxDO.getFromAddress());
            inboxDO.setRecipientType(recipientDO.getRecipientType());

            inboxDO.setSubject(sentBoxDO.getSubject());
            inboxDO.setEmailContents(sentBoxDO.getEmailContents());

            //filter out bcc
            inboxDO.getRecipientDOs().addAll(sentBoxDO.getRecipientDOs().stream()
                    .filter(recDO -> !recDO.getRecipientType().equals(EmailRecipientType.BCC)).collect(Collectors.toList()));

            //added sender
            inboxDO.getRecipientDOs().add(new EmailRecipientDO(sentBoxDO.getId(), sentBoxDO.getEmailAddress(),
                    EmailRecipientType.FROM));

            inboxDO.setEmailType(EmailType.RECEIVED);
            inboxDO.setCreatedAt(System.currentTimeMillis());

            inboxList.add(inboxDO);
        });
        return inboxList;
    }
}
