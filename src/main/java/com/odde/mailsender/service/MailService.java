package com.odde.mailsender.service;

import java.util.List;

public interface MailService {
    void send(MailInfo mailInfo) throws Exception;
    void sendMultiple(List<MailInfo> mailInfoList) throws Exception;
}
