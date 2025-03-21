//✅ PASO 3.4 - Crear JwtAuthenticationFilter


package only.get.infrastructure.security.core.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import only.get.infrastructure.security.details.UserDetailsServiceImpl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import org.springframework.lang.NonNull;




@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * ✅ Este método se ejecuta en cada solicitud HTTP.
     * - Extrae el token de la cabecera.
     * - Valida el token.
     * - Autentica al usuario si el token es válido.
     */
    @Override
    protected void doFilterInternal(  @NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        // ✅ 1. Extraer el token desde la cabecera HTTP
        String token = obtenerTokenDesdeRequest(request);
        
        if (token != null) {
            String username = jwtProvider.getUsuarioDesdeToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // ✅ 2. Validar el token con el usuario autenticado
            if (jwtProvider.validarToken(token, userDetails)) {  
                // ✅ 3. Crear objeto de autenticación para Spring Security
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ✅ 4. Registrar autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * ✅ Extrae el token de la cabecera Authorization en formato "Bearer <token>"
     */
    private String obtenerTokenDesdeRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Quitar el prefijo "Bearer "
        }
        return null;
    }
}

