package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.MailsenderApplication;
import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import cucumber.api.java.Before;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

@SpringBootTest(classes = {MailsenderApplication.class, Config.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private GreenMail greenMail;

    @Autowired
    private AddressBookService addressBookService;

    @Before
    public void scenarioSetup() throws Exception {
        greenMail.purgeEmailFromAllMailboxes();

        new File(AddressBook.FILE_PATH).delete();
        addressBookService.add(new AddressItem("user1@gmail.com", "user1"));
        addressBookService.add(new AddressItem("user2@gmail.com", "user2"));
        addressBookService.add(new AddressItem("noname@gmail.com", ""));
    }

    @After
    public void scenarioTeardown() {
        File file = new File(AddressBook.FILE_PATH);
        boolean isDelete = file.delete();
        System.out.println("file delete result is " + isDelete);
    }

}
