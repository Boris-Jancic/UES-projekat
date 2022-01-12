package com.OSA.Bamboo.web.rest.impl;

import com.OSA.Bamboo.model.Buyer;
import com.OSA.Bamboo.model.Seller;
import com.OSA.Bamboo.model.User;
import com.OSA.Bamboo.repository.UserRepo;
import com.OSA.Bamboo.security.TokenUtils;
import com.OSA.Bamboo.service.UserService;
import com.OSA.Bamboo.web.converter.*;
import com.OSA.Bamboo.web.dto.*;
import com.OSA.Bamboo.web.rest.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class UserApiImpl implements UserApi {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private DtoToBuyer buyerToEntity;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DtoToUser userToEntity;

    @Autowired
    private UserToDto userToDto;

    @Autowired
    private DtoToSeller sellerToEntity;

    @Autowired
    private SellerToDto sellerToDto;

    @Override
    public ResponseEntity<Void> registerSeller(@Valid SellerDto dto) {
        System.out.println(dto);
        if (dto != null) {
            Seller seller = this.sellerToEntity.convert(dto);
            return new ResponseEntity(userService.registerSeller(seller), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> registerBuyer(@Valid BuyerDto dto) {
        System.out.println(dto);
        if (dto != null) {
            Buyer buyer = this.buyerToEntity.convert(dto);
            return new ResponseEntity(userService.registerBuyer(buyer), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> login(@RequestBody AuthDto dto) {
        User user = userRepo.findByUsername(dto.getUsername());
        if (user != null) {
            if (!user.isBlocked()) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
                Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                try {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(dto.getUsername());
                    return ResponseEntity.ok(this.tokenUtils.generateToken(userDetails));
                } catch (Exception var5) {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserDto> getUser(String username) {
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));
        if (user.isPresent()) {
            UserDto dto = userToDto.convert(user.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<UserDto> getUsers() throws IOException {
        List<UserDto> users = userToDto.convert(userRepo.getAllUsersExeptAdmins());
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getSellers() {
        List<Seller> sellers = userService.sellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity changePassword(UserPasswordChangeDto dto) {
        return new ResponseEntity<>(userService.changePassword(dto), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<User> updateUser(@Valid UserDto dto) {
        User user = userService.findByUsername(dto.getUsername());
        if (user != null) {
            user.setBlocked(dto.isBlocked());
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
