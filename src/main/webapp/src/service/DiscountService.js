import AxiosClient from "./clients/AxiosClient";

export const DiscountService = {
    addDiscount,
    getDiscounts,
    updateDiscount,
    deleteDiscount
};

async function addDiscount(discount) {
    return await AxiosClient.post("http://localhost:8080/postDiscount", discount);
}

async function getDiscounts(id) {
    return await AxiosClient.get("http://localhost:8080/discounts/" + id);
}

async function updateDiscount(discount) {
    return await AxiosClient.put("http://localhost:8080/discounts/update", discount);
}

async function deleteDiscount(id) {
    return await AxiosClient.delete("http://localhost:8080/discounts/delete/" + id);
}
