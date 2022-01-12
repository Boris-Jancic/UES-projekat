import AxiosClient from "./AxiosClient";
import {TokenService} from "../TokenService";

export const AuthenticationService = {
    login,
    logout,
    getRole,
};

async function login(userCredentials) {
    console.log(userCredentials)
    try {
        await AxiosClient.post("http://localhost:8080/users/auth", userCredentials)
            .then(function (response) {
                const decoded_token = TokenService.decodeToken(response.data);
                if (decoded_token) {
                    TokenService.setToken(response.data);
                    window.location.assign("/home");
                } else {
                    console.error("Invalid token");
                }
            })
            .catch(function (error) {
                if (error.response.status === 403)
                    alert("You are blocked from using bamboo")
                if (error.response.status === 404)
                    alert("Wrong password or username")
        })

    } catch (error) {
        console.error(error);
    }
}

function logout() {
    TokenService.removeToken();
    window.location.assign("/login");
}

function getRole() {
    const token = TokenService.getToken();
    const decoded_token = token ? TokenService.decodeToken(token) : null;
    if (decoded_token) {
        return decoded_token.role.authority;
    } else {
        return null;
    }
}
