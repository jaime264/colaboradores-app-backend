package pe.confianza.colaboradores.gcontenidos.server.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerAuthenticationConverter implements UserAuthenticationConverter {
    public CustomerAuthenticationConverter() {
    }

    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        return null;
    }

    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey("user_info")) {
            Map<String, Object> userInfo = (HashMap)map.get("user_info");
            CustomerDetails userDetails = (new CustomerDetails.CustomerDetailsBuilder((String)map.get("user_name"))).documentNumber((String)userInfo.get("documentNumber")).documentType((String)userInfo.get("documentType")).country((String)userInfo.get("country")).build();
            Collection<? extends GrantedAuthority> authorities = this.getAuthorities(map);
            return new UsernamePasswordAuthenticationToken(userDetails, "N/A", authorities);
        } else {
            return null;
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get("authorities");
        if (authorities == null) {
            authorities = AuthoritiesConstants.USER;
        }
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String)authorities);
        } else if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection)authorities));
        } else {
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }
    }
}
