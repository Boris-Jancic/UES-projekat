import React, {useEffect, useState} from "react";
import {useHistory, useParams} from "react-router-dom";
import {TextField} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import {ArticleService} from "../../service/ArticleService";

export default function EditArticle() {

    const [article, setArticle] = useState({
        id: '',
        name: '',
        description: '',
        price: '',
        imageName: '',
        sellerId: ''
    })
    const [hasError, setError] = useState()
    const history = useHistory();
    const divStyle = {height: 930, backgroundSize: 'cover'};

    const {id} = useParams();

    async function fetchArticle() {
        const res = await fetch('http://localhost:8080/articles/' + id);
        return res.json()
    }

    useEffect(() => {
        fetchArticle()
            .then(res => setArticle(res))
            .catch(err => setError(err));
    }, [])

    function editArticle() {
        const name = document.getElementById("name").value
        const description = document.getElementById("description").value
        const price = document.getElementById("price").value

        if (name !== "")
            article.name = name
        if (description !== "")
            article.description = description
        if (price !== "")
            article.price = Number(price)

        ArticleService.editArticle(article)
            .then(() => alert("Success"))
        history.push("../browse/" + article.sellerId)
    }

    const changeInputHandler = (event, prop) => {
        const articleChanged = {...article};
        articleChanged[prop] = event.target.value;
        setArticle(articleChanged)
    }

    console.log(article)

    return (
        <div style={divStyle}>
            <div className="form-size">
                <h1>Edit article</h1>
                <hr/>
                <TextField label="Name" id="name" type="text" className="input-margin" value={article.name}
                           variant="outlined"
                           onChange={(event) => changeInputHandler(event, 'name')}/>
                <TextField label="Description" id="description" type="text" className="input-margin"
                           value={article.description} variant="outlined"
                           onChange={(event) => changeInputHandler(event, 'description')}/>
                <TextField label="Price" id="price" type="number" className="input-margin" value={article.price}
                           variant="outlined"
                           onChange={(event) => changeInputHandler(event, 'price')}/>
                <hr/>

                <Button size="large" color="inherit" onClick={editArticle}>
                    Submit
                </Button>
            </div>
        </div>
    )
}