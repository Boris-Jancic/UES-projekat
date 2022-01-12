import AxiosClient from "./clients/AxiosClient";


export const OrderService = {
    getOrders,
    getSellerComments,
    getElasticOrders,
    addOrder,
    addCartItem,
    putOrder,
};

async function getOrders(username) {
    return await AxiosClient.get("http://localhost:8080/orders/buyer/" + username);
}

async function getSellerComments(username) {
    return await AxiosClient.get("http://localhost:8080/orders/seller/comments/" + username);
}

async function getElasticOrders(comment) {
    return await AxiosClient.get("http://localhost:8080/orders/elastic/comments/" + comment);
}

async function addOrder(order) {
    return await AxiosClient.post("http://localhost:8080/orders/add", order);
}

async function addCartItem(cartItem) {
    return await AxiosClient.post("http://localhost:8080/orders/add/article", cartItem);
}

async function putOrder(order) {
    return await AxiosClient.put("http://localhost:8080/orders/update", order);
}
