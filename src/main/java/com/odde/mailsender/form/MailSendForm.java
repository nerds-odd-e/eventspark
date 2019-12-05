package com.odde.mailsender.form;

import com.odde.mailsender.data.Address;
import com.odde.mailsender.service.MailInfo;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MailSendForm {


    @NotEmpty(message = "{0} may not be empty")
    @Pattern(regexp = Address.MAIL_ADDRESS_PATTERN, message = "{error.invalid.email}")
    private String address;
    @NotEmpty(message = "{0} may not be empty")
    private String subject;
    @NotEmpty(message = "{0} may not be empty")
    private String body;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String[] getAddresses() {
        return getAddress().split("\\s*;\\s*");
    }

    private String renderSubjectTemplate(Address addressItem) {
        return StringUtils.replace(getSubject(), "$name", addressItem.getName());
    }

    private String renderBodyTemplate(Address addressItem) {
        return StringUtils.replace(getBody(), "$name", addressItem.getName());
    }

    public static MailSendForm create(String[] addresses) {
        MailSendForm mailSendForm = new MailSendForm();
        mailSendForm.setAddress(joinMailAddress(addresses));

        return mailSendForm;
    }

    private static  String joinMailAddress(String[] mailAddress) {
        return mailAddress == null ? "" : String.join(";", mailAddress);
    }

    public boolean isTemplate() {
        return StringUtils.contains(getSubject(), "$name") || StringUtils.contains(getBody(), "$name");
    }

    public MailInfo createRenderedMail(Address addressItem) {
        return new MailInfo("eventspark@gmx.com", addressItem.getMailAddress(), renderSubjectTemplate(addressItem), renderBodyTemplate(addressItem));
    }

    private MailInfo createNormalMail(String address) {
        return new MailInfo("eventspark@gmx.com", address, getSubject(), getBody());
    }

    public List<MailInfo> getMailInfoList(List<Address> addressItems) throws Exception {
        List<MailInfo> mailInfoList;
        if (isTemplate()) {
            mailInfoList = getTemplateMailInfoList(addressItems);
        } else {
            mailInfoList = getNormalMailInfoList();
        }
        return mailInfoList;
    }

    private List<MailInfo> getTemplateMailInfoList(List<Address> items) throws Exception {
        verifyEmpty(items);

        return renderMail(items);
    }

    private void verifyEmpty(List<Address> items) throws Exception {
        for (Address addressItem  : items) {
            if (addressItem == null || StringUtils.isEmpty(addressItem.getName()))
                throw new Exception("When you use template, choose email from contract list that has a name");
        }
    }

    private List<MailInfo> renderMail(List<Address> items) {
        List<MailInfo> mailInfoList = new ArrayList<>();
        for (Address addressItem  : items) {
            mailInfoList.add(createRenderedMail(addressItem));
        }
        return mailInfoList;
    }

    private List<MailInfo> getNormalMailInfoList() {
        return Arrays.stream(getAddresses()).map(this::createNormalMail).collect(Collectors.toList());
    }
}
