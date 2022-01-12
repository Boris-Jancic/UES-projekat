import React, {useEffect, useState} from 'react';
import {Nav, Navbar} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import {TokenService} from "../../service/TokenService";
import {AuthenticationService} from "../../service/clients/AuthenticationService";
import Button from "@material-ui/core/Button";
import logo from '../../static/images/logo.svg'
import {UserService} from "../../service/UserService";

function NavbarComponent() {
    const [browseUrl, setBrowseUrl] = useState('')
    const [commentsUrl, setCommentsUrl] = useState('')
    const [searchArticlesUrl, setSearchArticlesUrl] = useState('')
    const [searchCommentsUrl, setSearchCommentsUrl] = useState('')
    const [hasError, setError] = useState()

    useEffect(() => {
        fetchUser()
            .catch(err => setError(err));
    }, [])

    async function fetchUser() {
        const username = TokenService.decodeToken(TokenService.getToken()).sub
        const user = await UserService.getUser(username)
        setBrowseUrl("/browse/" + user.data.id)
        setCommentsUrl("/seller/" + user.data.username)
        localStorage.setItem('sellerId', user.data.id)
    }

    return (
        <>
            <Navbar collapseOnSelect expand="lg" bg="success" variant="light">
                <Navbar.Brand className="navbar-brand mb-0 h1" href="home" expand="lg">
                    <img
                        src={logo}
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                    />
                    Bamboo
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        {AuthenticationService.getRole() === "ROLE_BUYER" && (
                            <Button href="/sellers">Sellers</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_BUYER" && (
                            <Button href="/orders">Orders</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_BUYER" && (
                            <div className="form-inline my-2 my-lg-0" >
                                <input className="form-control mr-sm-1" placeholder="Search bicycles"
                                       onChange={e => setSearchArticlesUrl("/search/?name=" + e.target.value)} />
                                <Button href={searchArticlesUrl}>Go !</Button>
                            </div>
                        )}
                        {AuthenticationService.getRole() === "ROLE_SELLER" && (
                            <Button href={browseUrl}>My articles</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_SELLER" && (
                            <Button href="/addArticle">Add article</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_SELLER" && (
                            <Button href={commentsUrl}>Comments</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_SELLER" && (
                            <Button href={'/discounts'}>Add discount</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_SELLER" && (
                            <Button href={'/discounts/management'}>Discount management</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_ADMIN" && (
                            <Button href="/users">User managment</Button>
                        )}
                        {AuthenticationService.getRole() === "ROLE_ADMIN" && (
                            <div className="form-inline my-2 my-lg-0" >
                                <input className="form-control mr-sm-1" placeholder="Search comments"
                                       onChange={e => setSearchCommentsUrl("/search/comments/?comment=" + e.target.value)} />
                                <Button href={searchCommentsUrl}>Go !</Button>
                            </div>
                        )}
                    </Nav>
                    <Nav>
                        {TokenService.getToken() ? (
                            <>
                                <Button href="/profile">Profile</Button>
                                <Button onClick={() => AuthenticationService.logout()}>Log out</Button>
                            </>
                        ) : (
                            <>
                                <Button href="/login">Log in</Button>
                            </>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        </>
    );
};

export default NavbarComponent;
