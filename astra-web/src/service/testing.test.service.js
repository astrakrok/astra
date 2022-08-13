import {route} from "../constant/app.route"
import {client} from "../shared/js/axios";

export const deleteById = async id => {
    const url = route.admin.testingsTests.id(id).this;
    const response = await client.delete(url);
    return response.data;
}
