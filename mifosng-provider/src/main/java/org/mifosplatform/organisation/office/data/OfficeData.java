package org.mifosplatform.organisation.office.data;

import java.util.List;

import org.joda.time.LocalDate;

/**
 * Immutable data object for office data.
 */
public class OfficeData {

    private final Long id;
    private final String name;
    private final String nameDecorated;
    private final String externalId;
    private final LocalDate openingDate;
    private final String hierarchy;
    private final Long parentId;
    private final String parentName;

    @SuppressWarnings("unused")
    private final List<OfficeLookup> allowedParents;

    public static OfficeData template(final List<OfficeLookup> parentLookups, final LocalDate defaultOpeningDate) {
        return new OfficeData(null, null, null, null, defaultOpeningDate, null, null, null, parentLookups);
    }

    public static OfficeData appendedTemplate(final OfficeData office, final List<OfficeLookup> allowedParents) {
        return new OfficeData(office.id, office.name, office.nameDecorated, office.externalId, office.openingDate,
                office.hierarchy, office.parentId, office.parentName, allowedParents);
    }

    public OfficeData(final Long id, final String name, final String nameDecorated, final String externalId, final LocalDate openingDate,
            String hierarchy, final Long parentId, final String parentName, final List<OfficeLookup> allowedParents) {
        this.id = id;
        this.name = name;
        this.nameDecorated = nameDecorated;
        this.externalId = externalId;
        this.openingDate = openingDate;
        this.hierarchy = hierarchy;
        this.parentName = parentName;
        this.parentId = parentId;
        this.allowedParents = allowedParents;
    }
}