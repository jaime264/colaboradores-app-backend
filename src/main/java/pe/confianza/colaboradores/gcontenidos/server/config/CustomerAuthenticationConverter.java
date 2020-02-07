package pe.confianza.colaboradores.gcontenidos.server.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

public class CustomerAuthenticationConverter implements UserAuthenticationConverter {
    
    private UserDetailsService userDetailsService;

	final String USERINFO = "user_info";
	
	/**
	 * Optional {@link UserDetailsService} to use when extracting an {@link Authentication} from the incoming map.
	 * 
	 * @param userDetailsService the userDetailsService to set
	 */
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		
		//response.put(USERNAME, getUsername(userDetails));
		
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		// ADDED para mostrar info de usuario en la consulta de check_token
		// TODO considerar JWT
		response.put(USERINFO, getUserInfo(userDetails));
		
		return response;
	}

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
	    if (map.containsKey(USERNAME)) {
			Object principal = map.get(USERNAME);
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
			if (userDetailsService != null) {
				UserDetails user = userDetailsService.loadUserByUsername((String) map.get(USERNAME));
				authorities = user.getAuthorities();
				principal = user;
			}
			return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
		}
		return null;
	}
	
	
	/**
	 * Obtiene la información del usuario y la añade a un mapa.
	 * Implementación para {@link CustomerDetails}
	 * 
	 * @param userDetails objeto con la información de usuario
	 * @return mapa con los valores extraídos
	 */
	private Map<String, Object> getUserInfo(UserDetails userDetails) {
		Map<String, Object> userInfo = new LinkedHashMap<String, Object>();
		return userInfo;
	}
	
	/**
	 * Obtenido de {@link DefaultUserAuthenticationConverter}
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		Object authorities = map.get(AUTHORITIES);
		
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection XXXXXXXXXXXXXXX " + authorities);
	}

}
