import React from 'react'
import logo from '../static/images/logo.svg'
import {TokenService} from "../service/TokenService";

const HomeLayout = () => {
    console.log(TokenService.getToken())

    const divStyle = {height: 879, backgroundSize: 'cover', backgroundAttachment: 'fixed'};
    return (
        <div style={divStyle}>
            <div className="home-header">
                <h1>Welcome to <i><u>Bamboo</u></i><img src={logo} width={50}/></h1>
                <h3> we sell the best bikes in the neighbourhood</h3>
            </div>
        </div>
    );
}
export default HomeLayout