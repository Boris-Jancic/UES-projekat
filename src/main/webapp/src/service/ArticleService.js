import AxiosClient from "./clients/AxiosClient";

export const ArticleService = {
    addArticle,
    getArticle,
    getElasticArticles,
    getArticles,
    editArticle,
    deleteArticle,
    getSellerArticles,
    getArticlesMinMaxPrice,
    getArticlesMinMaxGrade,
    getArticlesMinMaxComments
};

async function addArticle(article) {
    return await AxiosClient.post("http://localhost:8080/articles/add", article);
}

async function getElasticArticles(name) {
    return await AxiosClient.get("http://localhost:8080/articles/elastic/" + name);
}

async function getArticle(id) {
    return await AxiosClient.get("http://localhost:8080/articles/" + id);
}

async function getArticles() {
    return await AxiosClient.get("http://localhost:8080/articles");
}

async function editArticle(article) {
    return await AxiosClient.put("http://localhost:8080/articles/update", article);
}

async function deleteArticle(id) {
    return await AxiosClient.delete('http://localhost:8080/articles/delete/' + id);
}

async function getSellerArticles(id) {
    return await AxiosClient.get("http://localhost:8080/articles/seller/" + id);
}

async function getArticlesMinMaxPrice(minPrice, maxPrice) {
    return await AxiosClient.get("http://localhost:8080/articles/price", {
        params: {
            min: minPrice,
            max: maxPrice,
        }
    });
}

async function getArticlesMinMaxGrade(minGrade, maxGrade) {
    return await AxiosClient.get("http://localhost:8080/articles/grade", {
        params: {
            min: minGrade,
            max: maxGrade,
        }
    });
}

async function getArticlesMinMaxComments(minComments, maxComments) {
    return await AxiosClient.get("http://localhost:8080/articles/comments", {
        params: {
            min: minComments,
            max: maxComments,
        }
    });
}
