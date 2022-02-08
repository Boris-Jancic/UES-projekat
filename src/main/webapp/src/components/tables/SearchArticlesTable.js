import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {classes} from "istanbul-lib-coverage";
import React, {useEffect, useState} from "react";
import {ArticleService} from "../../service/ArticleService";

export default function ArticlesElasticTable() {
    const minPrice = localStorage.getItem('minPrice')
    const maxPrice = localStorage.getItem('maxPrice')
    const minGrade = localStorage.getItem('minGrade')
    const maxGrade = localStorage.getItem('maxGrade')
    const minComments = localStorage.getItem('minComments')
    const maxComments = localStorage.getItem('maxComments')

    const [articles, setArticles] = useState([])
    let params = new URLSearchParams(document.location.search);
    const searchParams = params.get("name");

    useEffect(() => {
        fetchElasticArticles()
            .catch(err => console.log(err))
    }, [])

    const fetchElasticArticles = async () => {
        if (minPrice || maxPrice) {
            ArticleService.getArticlesMinMaxPrice(minPrice, maxPrice)
                .then((response) => response.data)
                .then(data => {
                    setArticles(data)
                    console.log(data)
                })
                .then(() => {
                    localStorage.removeItem("minPrice")
                    localStorage.removeItem("maxPrice")
                })
        } else if (minGrade || maxGrade) {
            ArticleService.getArticlesMinMaxGrade(minGrade, minGrade)
                .then((response) => response.data)
                .then(data => {
                    setArticles(data)
                    console.log(data)
                })
                .then(() => {
                    localStorage.removeItem("minGrade")
                    localStorage.removeItem("maxGrade")
                })
        } else if (minComments || maxComments) {
            ArticleService.getArticlesMinMaxGrade(minComments, maxComments)
                .then((response) => response.data)
                .then(data => {
                    setArticles(data)
                    console.log(data)
                })
                .then(() => {
                    localStorage.removeItem("minComments")
                    localStorage.removeItem("maxComments")
                })

        } else {
            ArticleService.getElasticArticles(searchParams)
                .then((response) => response.data)
                .then(data => {
                    setArticles(data)
                })
        }
    }

    return (
        <>
            <div className="form-table">
                <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align={"center"}>Id</TableCell>
                                <TableCell align={"center"}>Name</TableCell>
                                <TableCell align={"center"}>Description</TableCell>
                                <TableCell align={"center"}>Price</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {articles.map(row => (
                                <TableRow key={row.name}>
                                    <TableCell align={"center"}>{row.id}</TableCell>
                                    <TableCell align={"center"}>{row.name}</TableCell>
                                    <TableCell align={"center"}>{row.description}</TableCell>
                                    <TableCell align={"center"}>{row.price} EUR</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>
    );
}