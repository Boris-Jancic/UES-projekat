import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {classes} from "istanbul-lib-coverage";
import React, {useEffect, useState} from "react";
import {OrderService} from "../../service/OrderService";

export default function CommentsElasticTable() {
    const [comments, setComments] = useState([])
    let params = new URLSearchParams(document.location.search);
    const searchParams = params.get("comment");

    useEffect(() => {
        fetchElasticComments()
            .catch(err => console.log(err))
    }, [])

    const fetchElasticComments = async () => {
        OrderService.getElasticOrders(searchParams)
            .then((response) => response.data)
            .then(data => {
                setComments(data)
            })
    }

    return (
        <>
            <div className="form-table">
                <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align={"center"}>User</TableCell>
                                <TableCell align={"center"}>Date</TableCell>
                                <TableCell align={"center"}>Comment</TableCell>
                                <TableCell align={"center"}>Grade</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>

                            {comments.map(row => (
                                <TableRow key={row.name}>
                                    <TableCell align={"center"}>{row.user}</TableCell>
                                    <TableCell align={"center"}>{row.hourlyRate}</TableCell>
                                    <TableCell align={"center"}>{row.comment}</TableCell>
                                    <TableCell align={"center"}>{row.grade}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>
    );
}