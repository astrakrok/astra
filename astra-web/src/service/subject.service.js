import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getSubjectsDetails = async () => {
    const response = await client.get(route.subjects);
    return response.data;
}

export const create = async (subject) => {
    const response = await client.post(route.subjects, subject);
    return response.data;
}
