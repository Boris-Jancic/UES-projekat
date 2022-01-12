import AxiosClient from "./clients/AxiosClient";


export const UserService = {
    registerBuyer,
    registerSeller,
    editUser,
    getUser,
    getUsers,
    getSellers,
    getSellerGrade,
    changePassword
};

async function registerBuyer(buyer) {
    return await AxiosClient.post("http://localhost:8080/users/buyer/register", buyer);
}

async function registerSeller(seller) {
    return await AxiosClient.post("http://localhost:8080/users/seller/register", seller);
}

async function getUser(username) {
    return await AxiosClient.get("http://localhost:8080/users/" + username);
}

async function getUsers() {
    return await AxiosClient.get("http://localhost:8080/users/all");
}

async function getSellers() {
    return await AxiosClient.get("http://localhost:8080/users/sellers");
}

async function getSellerGrade(seller) {
    return await AxiosClient.get("http://localhost:8080/orders/seller/grade/" + seller);
}

async function editUser(user) {
    return await AxiosClient.put("http://localhost:8080/users/edit", user);
}

async function changePassword(userPasswordChange) {
    return await AxiosClient.put("http://localhost:8080/users/changePass/" + userPasswordChange.username, userPasswordChange);
}