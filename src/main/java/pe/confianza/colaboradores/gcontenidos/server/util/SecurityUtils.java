package pe.confianza.colaboradores.gcontenidos.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import pe.confianza.colaboradores.gcontenidos.server.config.CustomerDetails;

public class SecurityUtils {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
    public static final String UNAUTHORIZED = "NO_AUTORIZADO";

    private static CustomerDetails getCurrentCustomer() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        CustomerDetails customerUser = null;
        if (authentication != null && authentication.getPrincipal() instanceof CustomerDetails) {
            customerUser = (CustomerDetails)authentication.getPrincipal();
        }
        return customerUser;
    }

    public static void validateUserSession(String user) {
        String userSesion = getCurrentCustomer().getUuid();
        logger.info("Usuario consulta: {}", user);
        logger.info("Usuario sesión: {}", userSesion);
        if (!user.equals(userSesion)) {
            throw new UnauthorizedUserException(UNAUTHORIZED);
        }
    }
}
