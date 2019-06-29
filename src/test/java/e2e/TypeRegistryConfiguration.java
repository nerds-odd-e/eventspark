package e2e;

import com.odde.mailsender.service.MailInfo;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

import java.util.Locale;

// For more info, see https://cucumber.io/docs/cucumber/configuration
public class TypeRegistryConfiguration implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
      registry.defineDataTableType(DataTableType.entry(MailInfo.class));
    }
}
