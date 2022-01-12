import axios from "axios";
import {TokenService} from "../TokenService";
import {AuthenticationService} from "./AuthenticationService";

// API klijent se kreira ka specifičnom endpoint-u, uz sve ono što je uvek neophodno slati
const AxiosClient = axios.create();

// Dodaj token na svaki zahtev ka Article backendu, ako je korisnik ulogovan
AxiosClient.interceptors.request.use(function success(config) {
    const token = TokenService.getToken();
    if (token) {
        if (TokenService.didTokenExpire()) {
            alert("Token je istekao");
            AuthenticationService.logout();
            return false;
        }
        config.headers["Authorization"] = "Bearer " + token;
    }
    return config;
});

// U slučaju da se sa Article backenda vrati forbidden, token je istekao te izloguj korisnika
AxiosClient.interceptors.response.use(
    function success(response) {
        return response;
    },
    function failure(error) {
        const token = TokenService.getToken();
        if (token) {
            if (error.response && error.response.status === 403) {
                AuthenticationService.logout();
            }
        }
        throw error;
    }
);

export default AxiosClient;
