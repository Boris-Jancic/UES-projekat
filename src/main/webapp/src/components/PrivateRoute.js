import React from "react";
import {Redirect, Route} from "react-router-dom";
import {AuthenticationService} from "../service/clients/AuthenticationService";

export const PrivateRoute = ({component: Component, roles, ...rest}) => (
    // Instanciraj rutu sa svim njenim elementima (...rest) ali uz dodatnu proveru autorizacije

    <Route
        {...rest}
        render={(props) => {
            const role = AuthenticationService.getRole();
            if (!role) {
                // Korisnik nije ulogovan a pokušava da pristup zaštićenoj stranici - vrati ga na login
                return <Redirect to={{pathname: "/login"}}/>;
            }

            if (roles && !roles.includes(role)) {
                // Ako je korisnik ulogovan ali nema dozvolu pristupa zaštićenoj stranici - vrati ga na glavnu stranicu
                alert("You do not have permission for that page!")
                return <Redirect to={{pathname: "/browse"}}/>;
            }

            // Vrati stranicu koja se traži
            return <Component {...props} />;
        }}
    />
);
