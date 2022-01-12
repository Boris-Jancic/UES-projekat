package com.OSA.Bamboo.service;

import com.OSA.Bamboo.model.Buyer;
import com.OSA.Bamboo.model.Seller;
import com.OSA.Bamboo.model.User;
import com.OSA.Bamboo.web.dto.UserPasswordChangeDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> one(Long id);

    Page<User> all(int pageNo);

    List<Seller> sellers();

    User save(User user);

    void delete(String username);

    User findByUsername(String username);

    String changePassword(UserPasswordChangeDto userPasswordChangeDto);

    boolean registerSeller(Seller seller);

    boolean registerBuyer(Buyer buyer);
}
