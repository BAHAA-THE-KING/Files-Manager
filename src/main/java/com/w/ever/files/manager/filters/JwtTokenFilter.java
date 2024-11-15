package com.w.ever.files.manager.filters;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import com.w.ever.files.manager.services.CustomUserDetailsService;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header exists and starts with "Bearer"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove the "Bearer " prefix
            try {
                Integer userId = jwtTokenUtil.extractUserId(token); // Get user ID from the token
                // If a user ID is extracted and the user is not already authenticated
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Validate the token
                    if (jwtTokenUtil.isTokenValid(token, userId)) {
                        // Fetch user from the database
                        UserModel user = userRepository.findById(userId).orElse(null);

                        if (user != null) {
                            // Use UserDetailsService to load user details for authentication
                            var userDetails = userDetailsService.loadUserByUsername(user.getUsername());

                            // Create authentication token
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                            // Set request details for authentication
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            // Set the authentication context for the current request
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } else {
                        // Token is invalid or expired
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid or expired token");
                        response.getWriter().flush();  // Explicitly flush the response
                        return; // Stop further processing
                    }
                }
            } catch (Exception e) {
                // Handle any errors (e.g., token parsing issues)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token format or authentication failed");
                response.getWriter().flush();  // Explicitly flush the response
                return; // Stop further processing
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
