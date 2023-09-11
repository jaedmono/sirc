package co.gov.movilidadbogota.web.spring;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity_;
import co.gov.movilidadbogota.core.security.LdapFacadeImpl;
import co.gov.movilidadbogota.core.util.SecurityUtils;
import co.gov.movilidadbogota.web.util.TipoUsuarioEnum;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private ParametroSimurDAO parametroDAO;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		Authentication a = null;
		try {
			// ACCESO A LA BBDD
			UsuarioEntity u = usuarioDAO.findOneByAttribute(UsuarioEntity_.loginUsuario, name);

			if (u != null && u.getLoginUsuario().equals("admin")) {
				if (SecurityUtils.validatePasswordAndSalt(password, u.getClaveUsuario())) {
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(u.getPerfil().getDescripcionPerfil()));
					a = new UsernamePasswordAuthenticationToken(name, password, authorities);
				}
			} else if (u != null && u.isIdEstado()
					&& u.getIdTipoUsuario().getId() == TipoUsuarioEnum.EXTERNO.getCode()) {
				if (SecurityUtils.validatePasswordAndSalt(password, u.getClaveUsuario())) {
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority(u.getPerfil().getDescripcionPerfil()));
					a = new UsernamePasswordAuthenticationToken(name, password, authorities);
				}
			} else if (u != null && u.isIdEstado()
					&& u.getIdTipoUsuario().getId() == TipoUsuarioEnum.INTERNO.getCode()) {

				LdapFacadeImpl facadeLdap = new LdapFacadeImpl();

				String ldapResult = facadeLdap.validateLDAPUser(name.split("@")[0], password,
						parametroDAO.getLdapParameters());
                                //String ldapResult = "True";

				if (ldapResult.equals("True")) {
					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority("ROLE_SDM"));
					a = new UsernamePasswordAuthenticationToken(name, password, authorities);
				}

			}
		} catch (Exception e) {
			LOGGER.error("ERROR:", e);
		}
		return a;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
