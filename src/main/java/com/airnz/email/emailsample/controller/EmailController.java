package com.airnz.email.emailsample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airnz.email.emailsample.annotation.UserToken;
import com.airnz.email.emailsample.model.command.EmailRequestCommand;
import com.airnz.email.emailsample.model.dto.ResponseDto;
import com.airnz.email.emailsample.model.req.EmailRequest;
import com.airnz.email.emailsample.service.IEmailService;
import com.airnz.email.emailsample.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

/**
 * Email Controller
 */
@Tag(name = "Email Rest")
@RestController
public class EmailController {

    private static Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private IEmailService emailService;

    @Operation(summary = "Retrieve the contents of the user's inbox.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "503", description = "Internal Server")
    @UserToken
    @GetMapping("/mails/inbox")
    public ResponseEntity<ResponseDto> getInBoxEmails(@RequestHeader(value = "authorization") @Parameter(hidden = true) String token) {
        String fromAddress = Utils.getToken(token);
        EmailRequestCommand command = new EmailRequestCommand.Builder().withFomrAddress(fromAddress).build();
        ResponseDto dto = emailService.getInBoxEmails(command);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Retrieve the contents of a single email.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "503", description = "Internal Server")
    @GetMapping("/mails/{id}")
    public ResponseEntity<ResponseDto> getEmailById(@PathVariable(value = "id") String emailId) {
        EmailRequestCommand command = new EmailRequestCommand.Builder().withEmailId(emailId).build();
        ResponseDto dto = emailService.getEmailById(command);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Send an email.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "503", description = "Internal Server")
    @UserToken
    @PostMapping("/mails/send")
    public ResponseEntity<ResponseDto> sendEmail(@RequestHeader(value = "authorization") @Parameter(hidden = true) String token,
                                                 @RequestParam(value = "draftId", required = false) String draftId,
                                                 @RequestBody @Validated EmailRequest emailRequest) {
        String fromAddress = Utils.getToken(token);
        EmailRequestCommand command =
                new EmailRequestCommand.Builder().withFomrAddress(fromAddress).withEmailId(draftId).withEmailRequest(emailRequest).build();
        ResponseDto dto = emailService.sendEmail(command);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Write a draft email and save it for later.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "503", description = "Internal Server")
    @UserToken
    @PostMapping("/mails/draft")
    public ResponseEntity<ResponseDto> saveDraftEmail(@RequestHeader(value = "authorization")  @Parameter(hidden = true) String token,
                                                      @RequestBody @Validated EmailRequest emailRequest) {
        String fromAddress = Utils.getToken(token);
        EmailRequestCommand command =
                new EmailRequestCommand.Builder().withFomrAddress(fromAddress).withEmailRequest(emailRequest).build();

        ResponseDto dto = emailService.saveDraftEmail(command);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Update one or more properties of draft email e.g., recipients.")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "503", description = "Internal Server")
    @UserToken
    @PutMapping("/mails/draft/{id}")
    public ResponseEntity<ResponseDto> updateDraftEmail(@RequestHeader(value = "authorization")  @Parameter(hidden = true) String token,
                                                        @PathVariable("id") @NotNull String id,
                                                        @RequestBody @Validated EmailRequest emailRequest) {
        String fromAddress = Utils.getToken(token);
        EmailRequestCommand command =
                new EmailRequestCommand.Builder().withFomrAddress(fromAddress).withEmailId(id).withEmailRequest(emailRequest).build();
        ResponseDto dto = emailService.updateDraftEmail(command);
        return ResponseEntity.ok(dto);
    }
}
