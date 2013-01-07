package org.mifosplatform.portfolio.client.serialization;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.portfolio.client.command.ClientCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Implementation of {@link FromApiJsonDeserializer} for {@link ClientCommand} 
 * 's.
 */
@Component
public final class ClientCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<ClientCommand> {

    /**
     * The parameters supported for this command.
     */
    private final Set<String> supportedParameters = new HashSet<String>(Arrays.asList("id", "externalId", "firstname", "lastname",
            "clientOrBusinessName", "officeId", "joinedDate", "locale", "dateFormat"));

    private final FromJsonHelper fromApiJsonHelper;

    @Autowired
    public ClientCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromApiJsonHelper = fromApiJsonHelper;
    }

    @Override
    public ClientCommand commandFromApiJson(final String json) {

        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

        final JsonElement element = fromApiJsonHelper.parse(json);
        final Long officeId = fromApiJsonHelper.extractLongNamed("officeId", element);
        final String externalId = fromApiJsonHelper.extractStringNamed("externalId", element);
        final String firstname = fromApiJsonHelper.extractStringNamed("firstname", element);
        final String lastname = fromApiJsonHelper.extractStringNamed("lastname", element);
        final String clientOrBusinessName = fromApiJsonHelper.extractStringNamed("clientOrBusinessName", element);
        final LocalDate joiningDate = fromApiJsonHelper.extractLocalDateNamed("joinedDate", element);

        return new ClientCommand(externalId, firstname, lastname, clientOrBusinessName, officeId, joiningDate);
    }
}