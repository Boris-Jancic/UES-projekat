import React, {useEffect, useState} from 'react'
import {makeStyles} from '@material-ui/core/styles'
import {Card, CardContent, CardHeader, Grid, Typography} from '@material-ui/core/'
import Button from "@material-ui/core/Button";
import {CardActions, CardMedia, Dialog, DialogActions, DialogContent, TextField} from "@material-ui/core";
import {AuthenticationService} from "../service/clients/AuthenticationService";
import {ArticleService} from "../service/ArticleService";
import {useParams} from "react-router-dom";

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
        maxWidth: 300,
        padding: theme.spacing(2)
    },
    media: {
        height: 150,
    },
}))

export default function BrowseLayout() {
    const classes = useStyles()
    const [article, setArticle] = useState({})
    const [articles, setArticles] = useState([])
    const [cartArticles, setCartArticles] = useState([])
    const [hasError, setError] = useState(false)
    const [open, setOpen] = React.useState(false);

    useEffect(() => {
        fetchArticles().catch(err => setError(err))
    }, [])

    const {id} = useParams();

    const fetchArticles = async () => {
        try {
            if (AuthenticationService.getRole() === "SELLER") {
                const response = await ArticleService.getSellerArticles(id)
                setArticles(response.data);
            } else {
                const response = await ArticleService.getSellerArticles(id)
                setArticles(response.data);
            }
        } catch (error) {
            console.error(`Error while fetching articles: ${error}`);
        }
    }

    const handleDelete = async (id) => {    // deletes chosen article
        await ArticleService.deleteArticle(id);
        setArticles((articles) => articles.filter((article) => article.id !== id));
    }

    const handleClickOpen = (articleId) => {    // opens quanity dialog
        setOpen(true);
        setArticle(articleId)
        console.log("Article id " + articleId)
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleAddToCart = () => {  // Adds article to cart
        let quanity = document.getElementById('quanity').value
        cartArticles.push({
            "quanity": quanity,
            "articleId": article
        })
        console.log("Article id " + article + " quanity: " + quanity)
        localStorage.setItem('cartItems', JSON.stringify(cartArticles))
        console.log(JSON.parse(localStorage.getItem('cartItems')))
        setOpen(false);
    }

    function handleCartView() {
        const cartItems = localStorage.getItem("cartItems")
        if (cartItems != null)
            window.location.assign("/cart")
        else
            alert("Please add something to your cart")
    }

    console.log(articles)

    return (
        <div className={classes.root} class="card-view">

            {AuthenticationService.getRole() === "ROLE_BUYER" && (
                <Button fullWidth={true} style={{marginBottom: "20px"}} type="submit" color="inherit"
                        onClick={() => handleCartView()}>
                    CART
                </Button>
            )}
            <Grid
                container
                spacing={3}
                direction="row"
                justify="flex-start"
                alignItems="flex-start"
            >
                {articles.map(elem => (

                    <Grid item xs={12} sm={6} md={3} key={articles.indexOf(elem)}>
                        <Card variant="outlined">
                            <CardMedia
                                className={classes.media}
                                image={"data:image/png;base64," + elem.imageBytes}
                                title="Contemplative Reptile"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="h1">
                                    {elem.name}
                                </Typography>
                                <Typography variant="body2" color="textSecondary" component="h2">
                                    {elem.onDiscount === true && (
                                        <h6 style={{color: "red"}}>DISCOUNT</h6>
                                    )}
                                    <b> {elem.price} €</b>
                                </Typography>
                                <Typography variant="body2" color="textSecondary" component="p">
                                    {elem.description}
                                </Typography>
                            </CardContent>

                            <CardActions style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                            }}>
                                {AuthenticationService.getRole() === "ROLE_SELLER" && (
                                    <>
                                        <Button size="small" color="primary" href={"/editArticle/" + elem.id}>
                                            Edit
                                        </Button>
                                        <Button size="small" color="primary" onClick={() => handleDelete(elem.id)}>
                                            Delete
                                        </Button>
                                    </>
                                )}
                                {AuthenticationService.getRole() === "ROLE_BUYER" && (
                                    <Button size="small" type="submit" color="primary"
                                            onClick={() => handleClickOpen(elem.id)}>
                                        Add to cart
                                    </Button>
                                )}
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="quanity"
                        label="Quanity"
                        type="number"
                        variant="outlined"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={() => handleAddToCart()} color="primary">
                        Continue
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}
