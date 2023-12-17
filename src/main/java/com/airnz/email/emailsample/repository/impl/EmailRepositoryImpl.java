package com.airnz.email.emailsample.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.model.dataObject.EmailDO;
import com.airnz.email.emailsample.repository.IEmailRepository;
import com.airnz.email.emailsample.utils.IDGenerator;

@Repository
public class EmailRepositoryImpl implements IEmailRepository {

    private static Logger LOG = LoggerFactory.getLogger(EmailRepositoryImpl.class);

    private Map<String, Map<EmailType, List<EmailDO>>> emailAddressMap = new HashMap<>();

    private Map<String, EmailDO> emailIDMap = new HashMap();

    @Override
    public EmailDO getEmailById(String id) {
        return emailIDMap.get(id);
    }

    @Override
    public List<EmailDO> getEmailsByAddressAndType(String emailAddress, EmailType emailType) {
        return emailAddressMap.getOrDefault(emailAddress, new HashMap<>()).getOrDefault(emailType, new ArrayList<>());
    }

    @Override
    public EmailDO update(EmailDO emailDO) {
        String emailId = emailDO.getId();
        emailIDMap.put(emailId, emailDO);
        String emailAddress = emailDO.getEmailAddress();
        EmailType emailType = emailDO.getEmailType();
        Map<EmailType, List<EmailDO>> emailTypeListMap = emailAddressMap.getOrDefault(emailAddress, new HashMap<>());
        List<EmailDO> emailDOs = emailTypeListMap.getOrDefault(emailType,new ArrayList<>());
        EmailDO oldOne = emailDOs.stream().filter(t -> t.getId().equals(emailId)).findFirst().orElseThrow();
        emailDOs.remove(oldOne);
        emailDOs.add(emailDO);
        LOG.warn("Email with id: {} was updated", emailDO.getId());
        return emailDO;
    }

    @Override
    public boolean delete(EmailDO emailDO) {
        if (!isFound(emailDO)) {
            LOG.warn("Email with id: {} can not be found", emailDO.getId());
            return false;
        }
        String id = emailDO.getId();
        String emailAddress = emailDO.getEmailAddress();
        EmailType type = emailDO.getEmailType();
        return emailIDMap.remove(id) != null && emailAddressMap.get(emailAddress).get(type).remove(emailDO);
    }


    @Override
    public List<String> save(List<EmailDO> emailDOs) {
        List<String> res = new ArrayList();
        emailDOs.forEach(emailDO -> res.add(save(emailDO)));
        return res;
    }

    @Override
    public String save(EmailDO emailDO) {
        emailDO.setId(IDGenerator.generateID());
        String emailId = emailDO.getId();
        String emailAddress = emailDO.getEmailAddress();
        EmailType emailType = emailDO.getEmailType();
        emailAddressMap.computeIfAbsent(emailAddress, k -> new HashMap<>()).computeIfAbsent(emailType,
                k -> new ArrayList<>()).add(emailDO);
        if (!emailDO.getEmailType().equals(EmailType.RECEIVED)) {
            emailIDMap.put(emailId, emailDO);
        }

        LOG.info("Saved email for emailAddress: {} as {} successfully", emailAddress, emailDO.getRecipientType());

        return emailId;
    }

    private boolean isFound(EmailDO emailDO) {
        String id = emailDO.getId();
        String emailAddress = emailDO.getEmailAddress();
        EmailType type = emailDO.getEmailType();
        return emailIDMap.containsKey(id) && emailAddressMap.containsKey(emailAddress)
                && emailAddressMap.get(emailAddress).containsKey(type)
                && emailAddressMap.get(emailAddress).get(type).stream().filter(t -> t.getId().equals(id)).findFirst().isPresent();
    }
}
