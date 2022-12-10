import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getAll = async () => {
    const response = await client.get(route.steps);
    return response.data;
}

export const create = async step => {
    const response = await client.post(route.admin.steps, step);
    return response.data;
}
