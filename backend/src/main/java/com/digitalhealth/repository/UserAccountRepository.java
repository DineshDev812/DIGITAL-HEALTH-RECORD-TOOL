package com.digitalhealth.repository;
import com.digitalhealth.model.UserAccount; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface UserAccountRepository extends JpaRepository<UserAccount,Long>{ Optional<UserAccount> findByUsernameIgnoreCase(String username); Optional<UserAccount> findByEmailIgnoreCase(String email); Optional<UserAccount> findByWorkerId(Long workerId); long countByRole(com.digitalhealth.model.Role role); }
