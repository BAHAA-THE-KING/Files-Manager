package com.w.ever.files.manager.filters;

import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.repositories.UserRepository;
import com.w.ever.files.manager.utiles.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository; // Inject the UserRepository

    @Autowired
    private UserDetailsService userDetailsService; // This can still be used if needed

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Step 1: Extract the Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Step 2: Extract the token from the header
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            Integer userId = jwtTokenUtil.extractUserId(token); // Get user ID from token

            // Step 3: Check if the user is not already authenticated
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Step 4: Validate the token using the new isTokenValid method
                if (jwtTokenUtil.isTokenValid(token, userId)) {
                    // Step 5: Fetch user from the database using userId
                    Optional<UserModel> userOpt = userRepository.findById(userId);
                    if (userOpt.isPresent()) {
                        // Step 6: Create the Authentication token with the User's details
                        UserModel user = userOpt.get();
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                user.getUsername(), null, userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities()
                        );
                        // Step 7: Set the details (this can be customized based on request)
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // Step 8: Set authentication in SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        }
        // Step 9: Continue filter chain
        chain.doFilter(request, response);
    }
}
