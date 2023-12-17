package com.airnz.email.emailsample.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.airnz.email.emailsample.assembler.DataObjectAssembler;
import com.airnz.email.emailsample.assembler.DtoAssembler;
import com.airnz.email.emailsample.enums.EmailType;
import com.airnz.email.emailsample.model.command.EmailRequestCommand;
import com.airnz.email.emailsample.model.dataObject.EmailDO;
import com.airnz.email.emailsample.model.dto.EmailResponse;
import com.airnz.email.emailsample.model.dto.MultiResponseDto;
import com.airnz.email.emailsample.model.dto.ResponseDto;
import com.airnz.email.emailsample.model.dto.SingleResponseDto;
import com.airnz.email.emailsample.repository.IEmailRepository;
import com.airnz.email.emailsample.service.impl.EmailServiceImpl;

@SpringBootTest
public class EmailServiceImplTest {


    @Mock
    private IEmailRepository emailRepository;

    @Mock
    private DtoAssembler dtoAssembler;

    @Mock
    private DataObjectAssembler dataObjectAssembler;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInBoxEmails() {
        // Arrange
        EmailRequestCommand requestCommand = new EmailRequestCommand.Builder().withFomrAddress("test@example.com").build();

        List<EmailDO> mockEmailDOs = Collections.singletonList(new EmailDO());
        when(emailRepository.getEmailsByAddressAndType("test@example.com", EmailType.RECEIVED))
                .thenReturn(mockEmailDOs);

        when(dtoAssembler.dataObjectToDto(any(EmailDO.class))).thenReturn(new EmailResponse());

        // Act
        ResponseDto result = emailService.getInBoxEmails(requestCommand);

        // Assert
        assertTrue(result instanceof MultiResponseDto);
        assertNotNull(((MultiResponseDto) result).getData());
        verify(emailRepository, times(1)).getEmailsByAddressAndType("test@example.com", EmailType.RECEIVED);
        verify(dtoAssembler, times(1)).dataObjectToDto(any(EmailDO.class));
    }

    @Test
    void testGetEmailById() {
        // Arrange
        EmailRequestCommand requestCommand = new EmailRequestCommand.Builder().withEmailId("testId").build();
        requestCommand.setEmailId("testId");

        EmailDO mockEmailDO = new EmailDO();
        when(emailRepository.getEmailById("testId")).thenReturn(mockEmailDO);

        when(dtoAssembler.dataObjectToDto(any(EmailDO.class))).thenReturn(new EmailResponse());

        // Act
        ResponseDto result = emailService.getEmailById(requestCommand);

        // Assert
        assertTrue(result instanceof SingleResponseDto);
        assertNotNull(((SingleResponseDto) result).getData());
        verify(emailRepository, times(1)).getEmailById("testId");
        verify(dtoAssembler, times(1)).dataObjectToDto(any(EmailDO.class));
    }

    @Test
    void testSendEmail() {
        // Arrange
        EmailRequestCommand requestCommand = new EmailRequestCommand.Builder().build();
        when(dataObjectAssembler.toSentBoxDataObject(requestCommand)).thenReturn(new EmailDO());
        when(dataObjectAssembler.toInBoxDataObject(any(EmailDO.class))).thenReturn(Collections.singletonList(new EmailDO()));
        when(emailRepository.save(any(EmailDO.class))).thenReturn("mailId");

        // Act
        ResponseDto result = emailService.sendEmail(requestCommand);

        // Assert
        assertTrue(result instanceof SingleResponseDto);
        assertNotNull(((SingleResponseDto) result).getData());
        verify(emailRepository, times(1)).save(any(EmailDO.class));
    }

    @Test
    void testSaveDraftEmail() {
        // Arrange
        EmailRequestCommand requestCommand = new EmailRequestCommand.Builder().build();
        when(dataObjectAssembler.toDraftBoxDataObject(requestCommand)).thenReturn(new EmailDO());
        when(emailRepository.save(any(EmailDO.class))).thenReturn("mailId");

        // Act
        ResponseDto result = emailService.saveDraftEmail(requestCommand);

        // Assert
        assertTrue(result instanceof SingleResponseDto);
        assertNotNull(((SingleResponseDto) result).getData());
        verify(emailRepository, times(1)).save(any(EmailDO.class));
    }

    @Test
    void testUpdateDraftEmail() {
        // Arrange
        EmailRequestCommand requestCommand = new EmailRequestCommand.Builder().withEmailId("testId").build();

        EmailDO mockEmailDO = new EmailDO();
        when(emailRepository.getEmailById("testId")).thenReturn(mockEmailDO);
        when(dataObjectAssembler.toDraftBoxDataObject(requestCommand)).thenReturn(new EmailDO());
        when(emailRepository.update(any(EmailDO.class))).thenReturn(new EmailDO());
        when(dtoAssembler.dataObjectToDto(any(EmailDO.class))).thenReturn(new EmailResponse());
        // Act
        ResponseDto result = emailService.updateDraftEmail(requestCommand);

        // Assert
        assertTrue(result instanceof SingleResponseDto);
        assertNotNull(((SingleResponseDto) result).getData());
        verify(emailRepository, times(1)).getEmailById("testId");
        verify(emailRepository, times(1)).update(any(EmailDO.class));
    }
}