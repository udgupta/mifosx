package org.mifosplatform.organisation.office.service;

import java.util.Map;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.security.exception.NoAuthorizationException;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.organisation.monetary.domain.ApplicationCurrency;
import org.mifosplatform.organisation.monetary.domain.ApplicationCurrencyRepository;
import org.mifosplatform.organisation.monetary.domain.MonetaryCurrency;
import org.mifosplatform.organisation.monetary.domain.Money;
import org.mifosplatform.organisation.monetary.exception.CurrencyNotFoundException;
import org.mifosplatform.organisation.office.command.BranchMoneyTransferCommand;
import org.mifosplatform.organisation.office.command.OfficeCommand;
import org.mifosplatform.organisation.office.domain.Office;
import org.mifosplatform.organisation.office.domain.OfficeRepository;
import org.mifosplatform.organisation.office.domain.OfficeTransaction;
import org.mifosplatform.organisation.office.domain.OfficeTransactionRepository;
import org.mifosplatform.organisation.office.exception.OfficeNotFoundException;
import org.mifosplatform.organisation.office.serialization.BranchMoneyTransferCommandFromApiJsonDeserializer;
import org.mifosplatform.organisation.office.serialization.OfficeCommandFromApiJsonDeserializer;
import org.mifosplatform.useradministration.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OfficeWritePlatformServiceJpaRepositoryImpl implements OfficeWritePlatformService {

    private final static Logger logger = LoggerFactory.getLogger(OfficeWritePlatformServiceJpaRepositoryImpl.class);

    private final PlatformSecurityContext context;
    private final OfficeCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    private final BranchMoneyTransferCommandFromApiJsonDeserializer moneyTransferCommandFromApiJsonDeserializer;
    private final OfficeRepository officeRepository;
    private final OfficeTransactionRepository officeMonetaryTransferRepository;
    private final ApplicationCurrencyRepository applicationCurrencyRepository;

    @Autowired
    public OfficeWritePlatformServiceJpaRepositoryImpl(final PlatformSecurityContext context,
            final OfficeCommandFromApiJsonDeserializer fromApiJsonDeserializer,
            final BranchMoneyTransferCommandFromApiJsonDeserializer moneyTransferCommandFromApiJsonDeserializer,
            final OfficeRepository officeRepository, final OfficeTransactionRepository officeMonetaryTransferRepository,
            final ApplicationCurrencyRepository applicationCurrencyRepository) {
        this.context = context;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
        this.moneyTransferCommandFromApiJsonDeserializer = moneyTransferCommandFromApiJsonDeserializer;
        this.officeRepository = officeRepository;
        this.officeMonetaryTransferRepository = officeMonetaryTransferRepository;
        this.applicationCurrencyRepository = applicationCurrencyRepository;
    }

    @Transactional
    @Override
    public CommandProcessingResult createOffice(final JsonCommand command) {

        try {
            final AppUser currentUser = context.authenticatedUser();

            final OfficeCommand officeCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
            officeCommand.validateForCreate();

            final Office parent = validateUserPriviledgeOnOfficeAndRetrieve(currentUser, officeCommand.getParentId());

            final Office office = Office.fromJson(parent, command);

            // pre save to generate id for use in office hierarchy
            this.officeRepository.save(office);

            office.generateHierarchy();

            this.officeRepository.save(office);

            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(office.getId())
                    .withOfficeId(office.getId()).build();
        } catch (DataIntegrityViolationException dve) {
            handleOfficeDataIntegrityIssues(command, dve);
            return CommandProcessingResult.empty();
        }
    }

    @Transactional
    @Override
    public CommandProcessingResult updateOffice(final Long officeId, final JsonCommand command) {

        try {
            final AppUser currentUser = context.authenticatedUser();

            final OfficeCommand officeCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
            officeCommand.validateForUpdate();

            final Office office = validateUserPriviledgeOnOfficeAndRetrieve(currentUser, officeId);

            final Map<String, Object> changes = office.update(command);

            if (changes.containsKey("parentId")) {
                final Office parent = validateUserPriviledgeOnOfficeAndRetrieve(currentUser, officeCommand.getParentId());
                office.update(parent);
            }

            if (!changes.isEmpty()) {
                this.officeRepository.saveAndFlush(office);
            }

            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(office.getId())
                    .withOfficeId(office.getId()).with(changes).build();
        } catch (DataIntegrityViolationException dve) {
            handleOfficeDataIntegrityIssues(command, dve);
            return CommandProcessingResult.empty();
        }
    }

    @Transactional
    @Override
    public CommandProcessingResult externalBranchMoneyTransfer(final JsonCommand command) {

        context.authenticatedUser();

        final BranchMoneyTransferCommand moneyTransferCommand = this.moneyTransferCommandFromApiJsonDeserializer.commandFromApiJson(command
                .json());
        moneyTransferCommand.validateBranchTransfer();

        Long officeId = null;
        Office fromOffice = null;
        if (moneyTransferCommand.getFromOfficeId() != null) {
            fromOffice = this.officeRepository.findOne(moneyTransferCommand.getFromOfficeId());
            officeId = fromOffice.getId();
        }
        Office toOffice = null;
        if (moneyTransferCommand.getToOfficeId() != null) {
            toOffice = this.officeRepository.findOne(moneyTransferCommand.getToOfficeId());
            officeId = toOffice.getId();
        }

        if (fromOffice == null && toOffice == null) { throw new OfficeNotFoundException(moneyTransferCommand.getToOfficeId()); }

        final String currencyCode = moneyTransferCommand.getCurrencyCode();
        final ApplicationCurrency appCurrency = this.applicationCurrencyRepository.findOneByCode(currencyCode);
        if (appCurrency == null) { throw new CurrencyNotFoundException(currencyCode); }

        final MonetaryCurrency currency = new MonetaryCurrency(appCurrency.getCode(), appCurrency.getDecimalPlaces());
        final Money amount = Money.of(currency, moneyTransferCommand.getTransactionAmount());

        final OfficeTransaction entity = OfficeTransaction.fromJson(fromOffice, toOffice, amount, command);

        this.officeMonetaryTransferRepository.save(entity);

        return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(entity.getId()).withOfficeId(officeId)
                .build();
    }

    /*
     * Guaranteed to throw an exception no matter what the data integrity issue
     * is.
     */
    private void handleOfficeDataIntegrityIssues(final JsonCommand command, DataIntegrityViolationException dve) {

        Throwable realCause = dve.getMostSpecificCause();
        if (realCause.getMessage().contains("externalid_org")) {
            final String externalId = command.stringValueOfParameterNamed("externalId");
            throw new PlatformDataIntegrityException("error.msg.office.duplicate.externalId", "Office with externalId `" + externalId
                    + "` already exists", "externalId", externalId);
        } else if (realCause.getMessage().contains("name_org")) {
            final String name = command.stringValueOfParameterNamed("name");
            throw new PlatformDataIntegrityException("error.msg.office.duplicate.name", "Office with name `" + name + "` already exists",
                    "name", name);
        }

        logger.error(dve.getMessage(), dve);
        throw new PlatformDataIntegrityException("error.msg.office.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }

    /*
     * used to restrict modifying operations to office that are either the users
     * office or lower (child) in the office hierarchy
     */
    private Office validateUserPriviledgeOnOfficeAndRetrieve(final AppUser currentUser, final Long officeId) {

        final Long userOfficeId = currentUser.getOffice().getId();
        final Office userOffice = this.officeRepository.findOne(userOfficeId);
        if (userOffice == null) { throw new OfficeNotFoundException(userOfficeId); }

        if (userOffice.doesNotHaveAnOfficeInHierarchyWithId(officeId)) { throw new NoAuthorizationException(
                "User does not have sufficient priviledges to act on the provided office."); }

        Office officeToReturn = userOffice;
        if (!userOffice.identifiedBy(officeId)) {
            officeToReturn = this.officeRepository.findOne(officeId);
            if (officeToReturn == null) { throw new OfficeNotFoundException(officeId); }
        }

        return officeToReturn;
    }
}