package org.mifosplatform.organisation.office.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name = "m_organisation_currency")
public class OrganisationCurrency extends AbstractAuditableCustom<AppUser, Long> {

    @SuppressWarnings("unused")
    @Column(name = "code", nullable = false, length=3)
    private final String  code;

    @SuppressWarnings("unused")
    @Column(name = "decimal_places", nullable = false)
    private final Integer decimalPlaces;
    
    @SuppressWarnings("unused")
    @Column(name = "name", nullable = false, length=50)
    private final String  name;
    
    @SuppressWarnings("unused")
    @Column(name = "internationalized_name_code", nullable = false, length=50)
    private final String  nameCode;
    
    @SuppressWarnings("unused")
    @Column(name = "display_symbol", nullable = true, length=10)
    private final String  displaySymbol;

    protected OrganisationCurrency() {
        this.code = null;
        this.name = null;
        this.decimalPlaces = null;
        this.nameCode = null;
        this.displaySymbol = null;
    }

    public OrganisationCurrency(final String code, final String name, final int decimalPlaces, final String nameCode, final String displaySymbol) {
        this.code = code;
        this.name = name;
        this.decimalPlaces = decimalPlaces;
		this.nameCode = nameCode;
		this.displaySymbol = displaySymbol;
    }
}