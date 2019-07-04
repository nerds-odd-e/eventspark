package com.odde.mailsender.form;

import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MailSendForm {


    @NotEmpty
    @Pattern(regexp = "^([_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + "(?:;" + "[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + ")*)?$", message = "{error.invalid.email}")
    private String address;
    @NotEmpty
    private String subject;
    @NotEmpty
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

    private String renderSubjectTemplate(AddressItem addressItem) {
        return StringUtils.replace(getSubject(), "$name", addressItem.getName());
    }

    private String renderBodyTemplate(AddressItem addressItem) {
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

    public MailInfo createRenderedMail(AddressItem addressItem) {
        return new MailInfo("eventspark@gmx.com", addressItem.getMailAddress(), renderSubjectTemplate(addressItem), renderBodyTemplate(addressItem));
    }

    private MailInfo createNormalMail(String address) {
        return new MailInfo("eventspark@gmx.com", address, getSubject(), getBody());
    }

    public List<MailInfo> getMailInfoList(AddressBookService addressBookService) throws Exception {
        List<MailInfo> mailInfoList;
        if (isTemplate()) {
            mailInfoList = getTemplateMailInfoList(addressBookService);
        } else {
            mailInfoList = getNormalMailInfoList();
        }
        return mailInfoList;
    }

    private List<MailInfo> getTemplateMailInfoList(AddressBookService addressBookService) throws Exception {
        List<MailInfo> mailInfoList = new ArrayList<>();
        for (String address : getAddresses()) {
            AddressItem addressItem = addressBookService.findByAddress(address);
            if (addressItem == null || StringUtils.isEmpty(addressItem.getName()))
                throw new Exception("When you use template, choose email from contract list that has a name");

            mailInfoList.add(createRenderedMail(addressBookService.findByAddress(address)));
        }
        return mailInfoList;
    }

    private List<MailInfo> getNormalMailInfoList() {
        return Arrays.stream(getAddresses()).map(this::createNormalMail).collect(Collectors.toList());
    }
}
