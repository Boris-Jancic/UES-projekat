package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.web.dto.ArticleDto;
import org.apache.commons.io.FileUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ArticleToDto implements Converter<Article, ArticleDto> {
    private final String imageDirectory = System.getProperty("user.dir") + "/images/";

    public ArticleToDto() {
    }

    @Override
    public ArticleDto convert(Article article) {
        ArticleDto dto = new ArticleDto();

        File imgPath = new File(imageDirectory);
        byte[] fileContent = new byte[0];

        try {
            fileContent = FileUtils.readFileToByteArray(new File(imgPath + "/" + article.getImageName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        dto.setId(article.getId());
        dto.setName(article.getName());
        dto.setDescription(article.getDescription());
        dto.setPrice(article.getPrice());
        dto.setImageBytes(encodedString);

        return dto;
    }

    public List<ArticleDto> convert(List<Article> articles) throws IOException {

        List<ArticleDto> retVal = new ArrayList<>();

        for (Article a : articles) {
            ArticleDto dto = this.convert(a);
            retVal.add(dto);
        }

        return retVal;
    }
}
