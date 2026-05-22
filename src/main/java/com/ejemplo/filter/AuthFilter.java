package com.ejemplo.filter;

import com.ejemplo.model.Usuario;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        String uri = req.getRequestURI();
        
        // Excluir recursos estáticos
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".svg")) {
            chain.doFilter(request, response);
            return;
        }

        Usuario user = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;
        
        boolean isLogged = (user != null);
        boolean isAdmin = isLogged && "admin".equals(user.getRol());

        // Rutas públicas
        boolean isPublicRoute = uri.endsWith("login.html") || 
                                uri.endsWith("registro.html") || 
                                uri.endsWith("index.html") || 
                                uri.endsWith("perfil.html") || 
                                uri.endsWith("noticias.html") || 
                                uri.endsWith("partidos.html") || 
                                uri.endsWith("prestigio.html") || 
                                uri.endsWith("LoginServlet") || 
                                uri.endsWith("RegistroServlet") ||
                                uri.endsWith("/"); // raiz

        if (!isLogged) {
            // Si no está logueado y la ruta no es pública, redirigir a perfil.html
            // (admin.html, perfil-usuario.html, ajustes.html, crearPartido.html etc.)
            if (!isPublicRoute && !uri.endsWith("admin.html") && !uri.endsWith("perfil-usuario.html") && !uri.endsWith("ajustes.html") && !uri.endsWith("crearPartido.html")) {
                // si es ruta desconocida, dejamos pasar por ahora.
                chain.doFilter(request, response);
                return;
            } else if (!isPublicRoute) {
                res.sendRedirect(req.getContextPath() + "/perfil.html");
                return;
            }
        } else {
            // Usuario logueado
            if (isAdmin) {
                // Si es admin y no está en admin.html, LogoutServlet o AdminApiServlet, redirigir a admin.html
                if (!uri.endsWith("admin.html") && !uri.endsWith("LogoutServlet") && !uri.endsWith("AdminApiServlet")) {
                    res.sendRedirect(req.getContextPath() + "/admin.html");
                    return;
                }
            } else {
                // Si es usuario normal y trata de entrar a admin.html, redirigir a perfil-usuario.html
                if (uri.endsWith("admin.html")) {
                    res.sendRedirect(req.getContextPath() + "/perfil-usuario.html");
                    return;
                }
                
                // Si es usuario normal y trata de entrar a login o registro, redirigir a perfil-usuario
                if (uri.endsWith("login.html") || uri.endsWith("registro.html") || uri.endsWith("perfil.html")) {
                    res.sendRedirect(req.getContextPath() + "/perfil-usuario.html");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
