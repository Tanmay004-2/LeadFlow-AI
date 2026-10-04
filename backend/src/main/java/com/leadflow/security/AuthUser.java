package com.leadflow.security;

     import com.leadflow.user.entity.Role;
     import lombok.Builder;
     import lombok.Getter;
     import org.springframework.security.core.GrantedAuthority;
     import org.springframework.security.core.authority.SimpleGrantedAuthority;
     import org.springframework.security.core.userdetails.UserDetails;

     import java.util.Collection;
     import java.util.Collections;
     import java.util.UUID;

     @Getter
     @Builder
     public class AuthUser implements UserDetails {
         private UUID id;
         private UUID accountId;
         private UUID businessId;
         private String email;
         private Role role;
         private String password;

           @Override
           public Collection<? extends GrantedAuthority> getAuthorities() {
               return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
           }

           @Override
           public String getPassword() {
               return password;
           }

           @Override
           public String getUsername() {
               return email != null ? email : id.toString();
           }

           @Override
           public boolean isAccountNonExpired() { return true; }

           @Override
           public boolean isAccountNonLocked() { return true; }

           @Override
           public boolean isCredentialsNonExpired() { return true; }






           @Override
           public boolean isEnabled() { return true; }
     }
