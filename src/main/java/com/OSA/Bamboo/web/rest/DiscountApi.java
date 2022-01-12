package com.OSA.Bamboo.web.rest;

import com.OSA.Bamboo.model.Discount;
import com.OSA.Bamboo.web.dto.DiscountDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public interface DiscountApi {

    @PostMapping(value = "/postDiscount",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity postDiscount(@Valid @RequestBody DiscountDto dto);

    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @PutMapping(value = "/discounts/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity postDiscount(@Valid @RequestBody Discount discount);

    @GetMapping(value = "/discounts/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getCurrentDiscounts(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @DeleteMapping(value = "/discounts/delete/{id}")
    ResponseEntity<?> deleteDiscount(@PathVariable Long id);
}
