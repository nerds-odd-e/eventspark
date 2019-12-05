package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.MailsenderApplication;
import com.odde.mailsender.data.Address;
import com.odde.mailsender.service.AddressRepository;
import cucumber.api.java.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {MailsenderApplication.class, Config.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "e2e")
public class SpringIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private GreenMail greenMail;

    @Autowired
    private AddressRepository addressRepository;

    @Before
    public void scenarioSetup() throws Exception {
        greenMail.purgeEmailFromAllMailboxes();

        addressRepository.deleteAll();
        addressRepository.save(new Address("user1","user1@gmail.com"));
        addressRepository.save(new Address("user2","user2@gmail.com"));
        addressRepository.save(new Address("user3","user3@gmail.com"));
        addressRepository.save(new Address( "", "noname@gmail.com"));
    }

    @After
    public void scenarioTeardown() {
        addressRepository.deleteAll();
    }

}
