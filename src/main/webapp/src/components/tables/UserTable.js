import React, {Component} from 'react';
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import {classes} from "istanbul-lib-coverage";
import {UserService} from "../../service/UserService";
import {TokenService} from "../../service/TokenService";

export class UserTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            open: false,
            fullWidth: true,
            anonymous: false,
            start: 'start',
            end: 'end'
        };
    }


    async componentDidMount() {
        console.log(TokenService.getToken())
        UserService.getUsers()
            .then((response) => response.data)
            .then(data => {
                this.setState({users: data})
            })
    }

    handleAction(user) {
        let btn = document.getElementById('btn' + user.id)

        if (user.blocked === false) {
            btn.innerText = "Unblock"
            user.blocked = true
        } else {
            btn.innerText = "Block"
            user.blocked = false
        }

        UserService.editUser(user)
    }


    render() {
        console.log(this.state.users)
        return (
            <div className="form-table">
                <TableContainer component={Paper}>
                    <Table className={classes.table} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align={"center"}>ID</TableCell>
                                <TableCell align={"center"}>Name</TableCell>
                                <TableCell align={"center"}>Last name</TableCell>
                                <TableCell align={"center"}>Username</TableCell>
                                <TableCell align={"center"}>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>

                            {this.state.users.map(row => (
                                <TableRow key={row.name}>
                                    <TableCell align={"center"}>{row.id}</TableCell>
                                    <TableCell align={"center"}>{row.name}</TableCell>
                                    <TableCell align={"center"}>{row.lastName}</TableCell>
                                    <TableCell align={"center"}>{row.username}</TableCell>
                                    <TableCell align={"center"}>

                                        {(row.blocked === false) ? (
                                            <Button id={"btn" + row.id} variant="contained" color="primary"
                                                    onClick={() => this.handleAction(row)}>
                                                Block
                                            </Button>
                                        ) : (
                                            <Button id={"btn" + row.id} variant="contained" color="primary"
                                                    onClick={() => this.handleAction(row)}>
                                                Unblock
                                            </Button>
                                        )}

                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        );
    }
}
