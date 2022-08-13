import {route} from "../constant/app.route"
import {client} from "../shared/js/axios";

export const deleteById = async id => {
    const url = route.admin.testingsTests.id(id).this;
    const response = await client.delete(url);
    return response.data;
}

export const create = async data => {
    const url = route.admin.testingsTests.all;
    const response = await client.post(url, data)
        .catch(error => ({
            data: {
                error
            }
        }));
    return response.data;
}
