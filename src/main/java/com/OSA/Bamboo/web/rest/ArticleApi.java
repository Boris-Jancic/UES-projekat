package com.OSA.Bamboo.web.rest;

import com.OSA.Bamboo.web.dtoElastic.ArticleEditDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("/articles")
public interface ArticleApi {

    @PreAuthorize("hasRole('SELLER')")
    @RequestMapping(value = "/add",
            produces = {MediaType.IMAGE_PNG_VALUE, "application/json"})
    ResponseEntity<?> addArticle(@RequestParam("base64Image") String base64Image,
                                 @RequestParam("imgName") String imgName,
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("price") String price,
                                 @RequestParam("sellerId") Long sellerId);

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAllArticles() throws IOException;

    @GetMapping(value = "/elastic/{name}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getElasticArticles(@PathVariable("name") String name) throws IOException;

    @GetMapping(value = "/seller/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getSellerArticles(@PathVariable("id") Long id) throws IOException;

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getArticle(@PathVariable("id") Long id);

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping(value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity updateArticle(@Valid @RequestBody ArticleEditDto article) throws ParseException;

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity<?> deleteArticle(@PathVariable("id") Long id);

}
