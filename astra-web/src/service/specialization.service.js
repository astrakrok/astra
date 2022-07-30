import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getAll = async () => {
    const response = await client.get(route.specializations);
    return response.data;
}

export const create = async specialization => {
    const response = await client.post(route.specializations, specialization);
    return response.data;
}
