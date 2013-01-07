package org.mifosplatform.organisation.monetary.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.organisation.monetary.data.CurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class CurrencyReadPlatformServiceImpl implements CurrencyReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;

    @Autowired
    public CurrencyReadPlatformServiceImpl(final PlatformSecurityContext context, final TenantAwareRoutingDataSource dataSource) {
        this.context = context;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CurrencyData> retrieveAllowedCurrencies() {

        context.authenticatedUser();

        String sql = "select c.code as code, c.name as name, c.decimal_places as decimalPlaces, c.display_symbol as displaySymbol, c.internationalized_name_code as nameCode from m_organisation_currency c order by c.name";

        RowMapper<CurrencyData> rm = new CurrencyMapper();

        return this.jdbcTemplate.query(sql, rm, new Object[] {});
    }

    @Override
    public List<CurrencyData> retrieveAllPlatformCurrencies() {

        String sql = "select c.code as code, c.name as name, c.decimal_places as decimalPlaces, c.display_symbol as displaySymbol, c.internationalized_name_code as nameCode from m_currency c order by c.name";

        RowMapper<CurrencyData> rm = new CurrencyMapper();

        return this.jdbcTemplate.query(sql, rm, new Object[] {});
    }

    private static final class CurrencyMapper implements RowMapper<CurrencyData> {

        @Override
        public CurrencyData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

            String code = rs.getString("code");
            String name = rs.getString("name");
            int decimalPlaces = JdbcSupport.getInteger(rs, "decimalPlaces");
            String displaySymbol = rs.getString("displaySymbol");
            String nameCode = rs.getString("nameCode");

            return new CurrencyData(code, name, decimalPlaces, displaySymbol, nameCode);
        }
    }
}