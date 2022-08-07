import {route} from "../constant/app.route";
import {errorMessage} from "../error/message";
import {client} from "../shared/js/axios";

export const getSubjectsDetails = async () => {
    const response = await client.get(route.subjects);
    return response.data;
}

export const create = async subject => {
    const response = await client.post(route.subjects, subject)
        .catch(() => ({
            data: {
                error: errorMessage.UNEXPECTED_ERROR
            }
        }));
    return response.data;
}

export const update = async subject => {
    const response = await client.put(`${route.subjects}/${subject.id}`, subject)
        .catch(() => ({
            data: {
                error: errorMessage.UNEXPECTED_ERROR
            }
        }));
    return response.data;
}
