package com.airnz.email.emailsample.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.model.dataObject.EmailDO;
import com.airnz.email.emailsample.repository.impl.EmailRepositoryImpl;

public class EmailRepositoryImplTest {


    @InjectMocks
    private EmailRepositoryImpl emailRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmailById() {
        // Arrange
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress("test@example.com");
        emailDO.setEmailType(EmailType.SENT);
        String id = emailRepository.save(emailDO);

        // Act
        EmailDO result = emailRepository.getEmailById(id);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmailAddress());
    }

    @Test
    void testGetEmailsByAddressAndType() {
        // Arrange
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress("test@example.com");
        emailDO.setEmailType(EmailType.RECEIVED);
        emailRepository.save(emailDO);

        // Act
        List<EmailDO> result = emailRepository.getEmailsByAddressAndType("test@example.com", EmailType.RECEIVED);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("test@example.com", result.get(0).getEmailAddress());
        assertEquals(EmailType.RECEIVED, result.get(0).getEmailType());
    }

    @Test
    void testUpdate() {
        // Arrange
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress("test@example.com");
        emailDO.setEmailType(EmailType.RECEIVED);
        String id = emailRepository.save(emailDO);

        EmailDO updatedEmailDO = new EmailDO();
        updatedEmailDO.setEmailType(EmailType.RECEIVED);
        updatedEmailDO.setEmailAddress("test@example.com");
        updatedEmailDO.setId(id);
        updatedEmailDO.setSubject("Updated Subject");

        // Act
        EmailDO result = emailRepository.update(updatedEmailDO);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Subject", result.getSubject());
    }

    @Test
    void testDelete() {
        // Arrange
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress("test@example.com");
        emailDO.setEmailType(EmailType.SENT);
        emailRepository.save(emailDO);

        // Act
        boolean result = emailRepository.delete(emailDO);

        // Assert
        assertTrue(result);

    }

    @Test
    void testSave() {
        // Arrange
        EmailDO emailDO = new EmailDO();
        emailDO.setEmailAddress("test@example.com");
        emailDO.setEmailType(EmailType.SENT);

        // Act
        String result = emailRepository.save(emailDO);

        // Assert
        assertNotNull(result);
        assertEquals(emailDO.getId(), result);
        assertNotNull(emailRepository.getEmailById(emailDO.getId()));
    }
}
