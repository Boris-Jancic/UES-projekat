import React, {useEffect, useState} from "react";
import {UserService} from "../../service/UserService";
import {useParams} from "react-router-dom";
import {OrderService} from "../../service/OrderService";
import {classes} from "istanbul-lib-coverage";
import {Card, CardActions, CardContent, Checkbox, FormControlLabel, Grid, Typography} from "@material-ui/core";
import {AuthenticationService} from "../../service/clients/AuthenticationService";

export default function SellerProfile() {
    const [seller, setSeller] = useState({})
    const [comments, setComments] = useState([])
    const [hasError, setError] = useState()

    const {username} = useParams();

    useEffect(() => {
        fetchSeller()
            .catch(err => setError(err))
        fetchComments()
            .catch(err => setError(err));
    }, [])

    const fetchSeller = async () => {
        const sellerData = await UserService.getUser(username)
        setSeller(sellerData.data)
    }

    const fetchComments = async () => {
        const commentsData = (await OrderService.getSellerComments(username)).data
        if (AuthenticationService.getRole() === "ROLE_BUYER") {
            for (let i = 0; i < commentsData.length; i++) {
                if (commentsData[i].archivedComment === true) {
                    commentsData.splice(i, 1)
                }
            }
        }
        setComments(commentsData)
    }

    console.log(seller)
    console.log(comments)

    const handleSwitchChange = async (e, comment) => {
        comment.archivedComment = e.target.checked
        await OrderService.putOrder(comment)
    }

    return (
        <div className={classes.root} className="card-view">
            <h2> Seller grade: {localStorage.getItem("GRADE")} </h2>
            <Grid
                container
                spacing={3}
                direction="row"
                justify="flex-start"
                alignItems="flex-start"
            >
                {comments.map(elem => (
                    <Grid item xs={12} sm={12} md={12} key={comments.indexOf(elem)}>
                        <Card>
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="h1">
                                    {elem.anonymousComment === true && (
                                        "Anonymous user"
                                    )}
                                    {elem.anonymousComment === false && (
                                        elem.username
                                    )}
                                </Typography>
                                <Typography variant="body2" color="textSecondary" component="h2">
                                    <hr/>
                                    {elem.comment}
                                    <hr/>
                                    <h4> Grade: <u> {elem.grade} </u></h4>
                                </Typography>
                                <hr/>
                                <Typography variant="body2" color="textSecondary" component="p">
                                    Date of delivery : {elem.hourlyRate}
                                </Typography>
                            </CardContent>

                            <CardActions>
                                {AuthenticationService.getRole() === "ROLE_SELLER" && (
                                    <FormControlLabel
                                        id="anonymous"
                                        control={<Checkbox color="success" defaultChecked={elem.archivedComment}/>}
                                        onChange={e => handleSwitchChange(e, elem)}
                                        label="Archived"
                                        labelPlacement="end"
                                    />
                                )}
                            </CardActions>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
}