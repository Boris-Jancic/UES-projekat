package com.OSA.Bamboo.web.rest;

import com.OSA.Bamboo.model.User;
import com.OSA.Bamboo.web.dto.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public interface UserApi {

    @PostMapping(value = "/seller/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Void> registerSeller(@Valid @RequestBody SellerDto dto);

    @PostMapping(value = "/buyer/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Void> registerBuyer(@Valid @RequestBody BuyerDto dto);

    @PermitAll
    @PostMapping(value = "/auth",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<String> login(@RequestBody AuthDto dto);

    @GetMapping(value = "/{username}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<UserDto> getUser(@PathVariable("username") String username);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<UserDto> getUsers() throws IOException;

    @PreAuthorize("hasRole('BUYER')")
    @GetMapping(value = "/sellers",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<SellerDto>> getSellers() throws IOException;

    @PreAuthorize("hasAnyRole('BUYER', 'SELLER','ADMIN')")
    @PutMapping(value = "/changePass/{username}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Boolean> changePassword(@Valid @RequestBody UserPasswordChangeDto dto);

    @PreAuthorize("hasAnyRole('BUYER', 'SELLER','ADMIN')")
    @PutMapping(value = "/edit",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<User> updateUser(@Valid @RequestBody UserDto user);
}
