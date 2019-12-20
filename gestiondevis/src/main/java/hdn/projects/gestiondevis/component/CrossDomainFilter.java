package hdn.projects.gestiondevis.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Gandalf
 * 
 * le client et le serveur peuvent être hébergés sur deux serveurs distants, 
 * il faut penser aux problèmes réseau qui peuvent entraver la communication. 
 * Il faut indiquer au serveur quels sont les types d'en-têtes HTTP à prendre en considération.
 *
 */
@Component
public class CrossDomainFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*"); //toutes les URI sont autorisées
           httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
           httpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-req");           
           filterChain.doFilter(httpServletRequest, httpServletResponse);        
    }
}