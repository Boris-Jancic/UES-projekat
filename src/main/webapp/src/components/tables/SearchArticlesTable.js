import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {classes} from "istanbul-lib-coverage";
import React, {useEffect, useState} from "react";
import {ArticleService} from "../../service/ArticleService";

export default function ArticlesElasticTable() {
    const [articles, setArticles] = useState([])
    let params = new URLSearchParams(document.location.search);
    const searchParams = params.get("name");

    useEffect(() => {
        fetchElasticArticles()
            .catch(err => console.log(err))
    }, [])

    const fetchElasticArticles = async () => {
        ArticleService.getElasticArticles(searchParams)
            .then((response) => response.data)
            .then(data => {
                setArticles(data)
            })
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