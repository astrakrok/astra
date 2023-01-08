import {route} from "../constant/app.route";
import {errorMessage} from "../error/message";
import {client} from "../shared/js/axios";

export const getSubjectsDetailsPage = async (filter, pageable) => {
    const response = await client.post(route.admin.subjects.details + `?pageSize=${pageable.pageSize}&pageNumber=${pageable.pageNumber}`, filter);
    return response.data;
}

export const create = async subject => {
    const response = await client.post(route.admin.subjects.all, subject)
        .catch(() => ({
            data: {
                error: errorMessage.UNEXPECTED_ERROR
            }
        }));
    return response.data;
}

export const update = async subject => {
    const response = await client.put(`${route.admin.subjects.all}/${subject.id}`, subject)
        .catch(() => ({
            data: {
                error: errorMessage.UNEXPECTED_ERROR
            }
        }));
    return response.data;
}
