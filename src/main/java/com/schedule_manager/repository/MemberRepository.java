package com.schedule_manager.repository;

import com.schedule_manager.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    List<Member> findByNickname(String nickname);

    List<Member> findByNicknameContaining(String nickname);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
