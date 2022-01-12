import React, {Component} from "react";
import {ArticleService} from "../../service/ArticleService";
import {
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    makeStyles,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@material-ui/core";
import {classes} from "istanbul-lib-coverage";
import Button from "@material-ui/core/Button";
import {DiscountService} from "../../service/DiscountService";

export class DiscountComponent extends Component {

    useStyles = makeStyles((theme) => ({
        container: {
            display: 'flex',
            flexWrap: 'wrap',
        },
        textField: {
            marginLeft: theme.spacing(1),
            marginRight: theme.spacing(1),
            width: 200,
        },
    }));

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            articles: [],
            open: false,
            fullWidth: true,
            anonymous: false,
            start: 'start',
            end: 'end'
        };
    }

    componentDidMount() {
        console.log(localStorage.getItem('sellerId'))
        ArticleService.getSellerArticles(localStorage.getItem('sellerId'))
            .then((response) => response.data)
            .then(articlesData => {
                this.setState({articles: articlesData});
            });
    }

    handleClose = () => {
        this.setState({open: false})
    };

    handleClickOpen = (article) => {
        this.setState({open: true})
        this.setState({article: article})
    };

    handleAction = async () => {
        let fromDate = document.getElementById('startDate').value
        let tillDate = document.getElementById('endDate').value
        let description = document.getElementById('description').value
        let discount = document.getElementById('discount').value

        const dto = {
            fromDate: fromDate,
            tillDate: tillDate,
            description: description,
            discountPercent: discount,
            articleId: this.state.article,
            sellerId: localStorage.getItem('sellerId')
        }

        if (this.checkValid(fromDate, tillDate, description, discount)) {
            console.log(dto)
            if (this.checkDatesValid(new Date(fromDate), new Date(tillDate))) {
                await DiscountService.addDiscount(dto)
                this.setState({open: false})
            } else
                alert("From date must be smaller than till !")
        } else {
            alert("Please input the correct values")
        }
    }

    checkValid(fromDate, tillDate, description, discount) {
        if (fromDate !== '' && tillDate !== '' && description !== '' && discount !== '')
            if (discount > 9 && discount < 101)
                return true
            else
                alert("Enter a percent between 10 and 100 !")
        return false
    }

    checkDatesValid(fromDate, tillDate) {
        if (fromDate < tillDate)
            return true
        return false
    }

    render() {

        console.log(this.state.articles)
        return (
            <div className="form-table">
                <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align={"center"}>ID</TableCell>
                                <TableCell align={"center"}>Name</TableCell>
                                <TableCell align={"center"}>Description</TableCell>
                                <TableCell align={"center"}>Price</TableCell>
                                <TableCell align={"center"}>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>

                            {this.state.articles.map(row => (
                                <TableRow key={row.name}>
                                    <TableCell align={"center"}>{row.id}</TableCell>
                                    <TableCell align={"center"}>{row.name}</TableCell>
                                    <TableCell align={"center"}>{row.description}</TableCell>
                                    <TableCell align={"center"}>{row.price}</TableCell>
                                    <TableCell>
                                        <Button fullWidth={true} variant="contained" color="primary"
                                                onClick={() => this.handleClickOpen(row.id)}>
                                            Add discount
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

                <Dialog open={this.state.open}
                        fullWidth={this.state.fullWidth} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">Add discount</DialogTitle>
                    <DialogContent>
                        <TextField
                            fullWidth={this.state.fullWidth}
                            id="startDate"
                            label="From"
                            type="date"
                            className={this.useStyles.textField}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                        <TextField
                            fullWidth={this.state.fullWidth}
                            id="endDate"
                            label="Till"
                            type="date"
                            className={this.useStyles.textField}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                        <TextField
                            fullWidth={this.state.fullWidth}
                            autoFocus
                            margin="dense"
                            id="description"
                            label="Description"
                            type="text"
                        />
                        <TextField
                            fullWidth={this.state.fullWidth}
                            autoFocus
                            margin="dense"
                            id="discount"
                            label="Discount %"
                            type="number"
                            InputProps={{
                                inputProps: {
                                    max: 100, min: 1, maxlength: 3
                                }
                            }}

                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => this.handleClose()} color="primary">
                            Cancel
                        </Button>
                        <Button onClick={() => this.handleAction()} color="primary">
                            Continue
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}