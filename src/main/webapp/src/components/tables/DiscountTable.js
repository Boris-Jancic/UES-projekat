import React, {Component} from "react";
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

export class DiscountTable extends Component {

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
            discount: {},
            discounts: [],
            open: false,
            fullWidth: true,
            anonymous: false,
            start: 'start',
            end: 'end'
        };
    }

    componentDidMount() {
        console.log(localStorage.getItem('sellerId'))
        DiscountService.getDiscounts(localStorage.getItem('sellerId'))
            .then((response) => response.data)
            .then(data => {
                this.setState({discounts: data});
            });
    }

    handleClose = () => {
        this.setState({open: false})
    };

    handleClickOpen = (discount) => {
        this.setState({open: true})
        this.setState({discount: discount})
    };

    checkValid(fromDate, tillDate, description, discount) {
        if (fromDate !== '' && tillDate !== '' && description !== '' && discount !== '')
            return true
        else
            alert("Enter a percent between 10 and 100 !")
        return false
    }

    checkDatesValid(fromDate, tillDate) {
        return fromDate < tillDate;
    }

    handleAction = async () => {

        if (this.checkValid(this.state.discount.fromDate, this.state.discount.tillDate,
            this.state.discount.description, this.state.discount.discountPercent)) {
            if (this.checkDatesValid(new Date(this.state.discount.fromDate), new Date(this.state.discount.tillDate))) {
                await DiscountService.updateDiscount(this.state.discount)
                this.setState({open: false})
            } else
                alert("From date must be smaller than till !")
        } else {
            alert("Please input the correct values")
        }
    }

    handleDelete(id) {
        DiscountService.deleteDiscount(id).then(res => console.log(res))
    }

    changeInputHandler = (event, prop) => {
        const disc = {...this.state.discount};
        disc[prop] = event.target.value;
        this.setState({discount: disc})
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
                                <TableCell align={"center"}>Description</TableCell>
                                <TableCell align={"center"}>From</TableCell>
                                <TableCell align={"center"}>Till</TableCell>
                                <TableCell align={"center"}>% Off</TableCell>
                                <TableCell align={"center"}>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>

                            {this.state.discounts.map(row => (
                                <TableRow key={row.name}>
                                    <TableCell align={"center"}>{row.id}</TableCell>
                                    <TableCell align={"center"}>{row.description}</TableCell>
                                    <TableCell align={"center"}>{row.fromDate}</TableCell>
                                    <TableCell align={"center"}>{row.tillDate}</TableCell>
                                    <TableCell align={"center"}>{row.discountPercent}</TableCell>
                                    <TableCell>
                                        <Button fullWidth={true} variant="contained" color="primary"
                                                onClick={() => this.handleClickOpen(row)}>
                                            Edit
                                        </Button>
                                        <hr/>
                                        <Button fullWidth={true} variant="contained" color="primary"
                                                onClick={() => this.handleDelete(row.id)}>
                                            Delete
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
                            id="fromDate"
                            label="From"
                            type="date"
                            className={this.useStyles.textField}
                            InputLabelProps={{
                                shrink: true,
                            }}
                            value={this.state.discount.fromDate}
                            onChange={(event) => this.changeInputHandler(event, 'fromDate')}/>
                        <TextField
                            fullWidth={this.state.fullWidth}
                            id="tillDate"
                            label="Till"
                            type="date"
                            className={this.useStyles.textField}
                            InputLabelProps={{
                                shrink: true,
                            }}
                            value={this.state.discount.tillDate}
                            onChange={(event) => this.changeInputHandler(event, 'tillDate')}/>
                        <TextField
                            fullWidth={this.state.fullWidth}
                            autoFocus
                            margin="dense"
                            id="description"
                            label="Description"
                            type="text"
                            value={this.state.discount.description}
                            onChange={(event) => this.changeInputHandler(event, 'description')}/>
                        <TextField
                            fullWidth={this.state.fullWidth}
                            autoFocus
                            margin="dense"
                            id="discountPercent"
                            label="Discount %"
                            type="number"
                            InputProps={{
                                inputProps: {
                                    max: 100, min: 1, maxlength: 3
                                }
                            }}
                            value={this.state.discount.discountPercent}
                            onChange={(event) => this.changeInputHandler(event, 'discountPercent')}/>
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