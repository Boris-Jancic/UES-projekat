import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import {classes} from "istanbul-lib-coverage";
import Button from "@material-ui/core/Button";
import React, {useEffect, useState} from "react";
import {ArticleService} from "../../service/ArticleService";
import {OrderService} from "../../service/OrderService";
import {TokenService} from "../../service/TokenService";

export default function CartItemsTable() {
    const cartItems = JSON.parse(localStorage.getItem('cartItems'))
    const [articles, setArticles] = useState([])
    const [orderArticles, setOrderArticles] = useState([])
    const [finalPrice, setFinalPrice] = useState(0)
    const username = TokenService.decodeToken(TokenService.getToken()).sub

    useEffect(() => {
        fetchCartArticles()
    }, [])

    const fetchCartArticles = async () => {
        let articlesData = []
        let orderArticlesData = []
        let finPrice = 0

        for (const key of Object.keys(cartItems)) {
            const article = await ArticleService.getArticle(cartItems[key].articleId)
            console.log(cartItems[key].quanity)
            const cartItemPrice = article.data.price * cartItems[key].quanity
            const cartItem = {
                "name": article.data.name,
                "description": article.data.description,
                "quanity": cartItems[key].quanity,
                "price": cartItemPrice,
            }
            const OrderItem = {
                "quanity": cartItems[key].quanity,
                "articleId": article.data.id,
            }
            finPrice += cartItemPrice
            orderArticlesData.push(OrderItem)
            articlesData.push(cartItem)
        }
        setOrderArticles(orderArticlesData)
        setFinalPrice(finPrice)
        setArticles(articlesData)
    }

    async function order() {
        const order = {
            'delivered': false,
            'grade': 0,
            'comment': "awaiting grade",
            'hourlyRate': "",
            'username': username,
            'anonymousComment': false,
            'archivedComment': false
        }

        console.log(order)
        console.log(orderArticles)

        await OrderService.addOrder(order)
        for (const key of Object.keys(orderArticles)) {
            await OrderService.addCartItem(orderArticles[key])
        }

        localStorage.removeItem("cartItems")
        window.location.assign("/sellers");
    }

    return (
        <>
            <div className="form-table">
                <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align={"center"}>Name</TableCell>
                                <TableCell align={"center"}>Description</TableCell>
                                <TableCell align={"center"}>Quanity</TableCell>
                                <TableCell align={"center"}>Price</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>

                            {articles.map(row => (
                                <TableRow key={row.name}>
                                    <TableCell align={"center"}>{row.name}</TableCell>
                                    <TableCell align={"center"}>{row.description}</TableCell>
                                    <TableCell align={"center"}>{row.quanity}</TableCell>
                                    <TableCell align={"center"}>{row.price} EUR</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                    <b>FINAL PRICE : <u>{finalPrice}</u></b>
                    <Button onClick={() => order()} fullWidth>Order above mentioned articles</Button>
                </TableContainer>
            </div>
        </>
    );
}