import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getAll = async () => {
    const response = await client.get(route.exams);
    return response.data;
}

export const create = async exam => {
    const response = await client.post(route.exams, exam);
    return response.data;
}
