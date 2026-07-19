package com.galleryvote.auth;
import com.galleryvote.user.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.time.*; import org.springframework.http.HttpStatus; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.oauth2.jose.jws.MacAlgorithm; import org.springframework.security.oauth2.jwt.*; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder passwords; private final JwtEncoder jwt;
 public AuthController(UserRepository u,PasswordEncoder p,JwtEncoder j){users=u;passwords=p;jwt=j;}
 public record RegisterRequest(@NotBlank @Size(min=3,max=50) String username,@Email @NotBlank String email,@Size(min=10,max=128) String password){}
 public record LoginRequest(@NotBlank String username,@NotBlank String password){} public record TokenResponse(String token,long expiresIn){}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) TokenResponse register(@Valid @RequestBody RegisterRequest r){if(users.existsByUsernameIgnoreCaseOrEmailIgnoreCase(r.username(),r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Username or email already exists");return token(users.save(new User(r.username().trim(),r.email().trim().toLowerCase(),passwords.encode(r.password()))));}
 @PostMapping("/login") TokenResponse login(@Valid @RequestBody LoginRequest r){User u=users.findByUsernameIgnoreCase(r.username()).filter(x->passwords.matches(r.password(),x.getPasswordHash())).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials"));return token(u);}
 private TokenResponse token(User u){Instant now=Instant.now(),end=now.plus(Duration.ofHours(2));JwtClaimsSet claims=JwtClaimsSet.builder().issuer("galleryvote").issuedAt(now).expiresAt(end).subject(u.getUsername()).claim("uid",u.getId().toString()).claim("role",u.getRole().name()).build();String value=jwt.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(),claims)).getTokenValue();return new TokenResponse(value,Duration.between(now,end).toSeconds());}
}
