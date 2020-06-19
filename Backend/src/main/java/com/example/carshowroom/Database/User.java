package com.example.carshowroom.Database;

import com.example.carshowroom.Data.AuthProvider;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
public class User implements Serializable, UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(length = 25)
    private String name;

    @Column(length = 25)
    private String surname;

    @Column(length = 30, unique = true)
    private String email;

    private long PESEL;

    @Column(length = 100)
    private String password;

    @Column(length = 20, unique = true)
    private String username;

    @Column(length = 100)
    private String address;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    private boolean isEnabled;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    public User()
    {
    }

    public User(String username, String email)
    {
        this.username = username;
        this.email = email;
    }

    public User(Role role)
    {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public long getPESEL()
    {
        return PESEL;
    }

    public void setPESEL(long PESEL)
    {
        this.PESEL = PESEL;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String login)
    {
        this.username = login;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

    public Set<Invoice> getInvoices()
    {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices)
    {
        this.invoices = invoices;
    }

    public AuthProvider getProvider()
    {
        return provider;
    }

    public void setProvider(AuthProvider provider)
    {
        this.provider = provider;
    }

}
