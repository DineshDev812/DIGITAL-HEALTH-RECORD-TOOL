package com.digitalhealth.security;
import com.digitalhealth.model.Role;
public record AuthenticatedUser(Long id,String username,Role role,Long workerId) {}
