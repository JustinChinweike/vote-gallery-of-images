package com.galleryvote.contest;
import com.galleryvote.config.JwtSupport; import com.galleryvote.user.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.time.Instant; import org.springframework.data.domain.*; import org.springframework.data.web.PageableDefault; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/contests")
public class ContestController {
 private final ContestRepository contests; private final UserRepository users; public ContestController(ContestRepository c,UserRepository u){contests=c;users=u;}
 public record Request(@NotBlank @Size(max=120) String title,@NotBlank @Size(max=2000) String description,@FutureOrPresent Instant startsAt,@Future Instant endsAt){}
 public record View(Long id,String title,String description,String owner,Contest.Status status,Instant startsAt,Instant endsAt){static View of(Contest c){return new View(c.getId(),c.getTitle(),c.getDescription(),c.getOwner().getUsername(),c.getStatus(),c.getStartsAt(),c.getEndsAt());}}
 @GetMapping Page<View> list(@RequestParam(defaultValue="") String q,@PageableDefault(size=20,sort="createdAt",direction=Sort.Direction.DESC) Pageable p){return contests.findByTitleContainingIgnoreCase(q,p).map(View::of);}
 @GetMapping("/{id}") View get(@PathVariable Long id){return contests.findById(id).map(View::of).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Contest not found"));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) View create(@Valid @RequestBody Request r,Authentication a){if(!r.endsAt().isAfter(r.startsAt()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"End must be after start");User u=users.findById(JwtSupport.userId(a)).orElseThrow();return View.of(contests.save(new Contest(r.title(),r.description(),u,r.startsAt(),r.endsAt())));}
}
