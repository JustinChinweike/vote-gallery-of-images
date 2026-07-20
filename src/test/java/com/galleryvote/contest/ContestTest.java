package com.galleryvote.contest;
import static org.assertj.core.api.Assertions.assertThat; import com.galleryvote.user.User; import java.time.Instant; import org.junit.jupiter.api.Test;
class ContestTest { @Test void votingIsOnlyOpenInsideWindow(){Instant now=Instant.parse("2026-07-19T12:00:00Z");Contest c=new Contest("Summer","Photos",new User("alice","a@example.com","hash"),now.minusSeconds(1),now.plusSeconds(1));assertThat(c.isVotingOpen(now)).isTrue();assertThat(c.isVotingOpen(now.plusSeconds(2))).isFalse();} }
