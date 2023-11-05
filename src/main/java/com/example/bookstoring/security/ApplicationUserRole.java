package com.example.bookstoring.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;
import static com.example.bookstoring.security.ApplicationUserPermission.*;

@RequiredArgsConstructor
@Getter
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(STUDENT_CURRENTLY_READ, STUDENT_VIEW_ALL_BOOKS)),
    AUTHOR(Sets.newHashSet(AUTHOR_CREATE_BOOKS, AUTHOR_DELETE_BOOKS));

    private final Set<ApplicationUserPermission> permission;

    public Set<ApplicationUserPermission> getPermissions() {
        return permission;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
       Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
       permissions.add(new SimpleGrantedAuthority("Role_" + this.name()));
       return permissions;
    }
}
