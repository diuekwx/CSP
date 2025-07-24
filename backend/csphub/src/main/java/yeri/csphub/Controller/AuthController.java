package yeri.csphub.Controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yeri.csphub.DTO.UserDto;
import yeri.csphub.Entities.ERole;
import yeri.csphub.Entities.Role;
import yeri.csphub.Entities.Users;
import yeri.csphub.Payload.Request.LoginRequest;
import yeri.csphub.Payload.Request.SignupRequest;
import yeri.csphub.Payload.Response.JwtResponse;
import yeri.csphub.Payload.Response.MessageResponse;
import yeri.csphub.Repository.RoleRepo;
import yeri.csphub.Repository.UsersRepo;
import yeri.csphub.Security.Jwt.JwtUtils;
import yeri.csphub.Security.UserDetails.UserDetailsImpl;
import yeri.csphub.Service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://127.0.0.1:5173")
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final AuthenticationManager authenticationManager;

    private final UsersRepo userRepo;

    private final UserService userService;

    private final JwtUtils jwtUtils;



    public AuthController(AuthenticationManager authenticationManager, UsersRepo userRepo, UserService userService, JwtUtils jwtUtils){
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.userService = userService;
        this.jwtUtils = jwtUtils;

    }
    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal UserDetailsImpl user) {
        return new UserDto(user.getId(), user.getUsername());
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            logger.debug("Authentication request received");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            System.out.println("AuthManager class: " + authenticationManager.getClass());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (Exception e) {
            logger.error("Error during authentication", e);  // Logs the stack trace
            throw e;  // Rethrow the exception
        }
    }

    // TODO: seperate with oauth google signup/in
    // maybe throw first two if in ser vice
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.createUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
