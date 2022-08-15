import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";
import {errorMessage} from "../error/message";

export const getValues = async names => {
    const url = route.admin.properties + "?names=" + names.join(",");
    const response = await client.get(url);
    return response.data;
}

export const update = async property => {
    const url = route.admin.properties + "/" + property.name;
    const response = await client.put(url, property)
        .catch(() => ({
            data: {
                error: errorMessage.UNABLE_TO_UPDATE_PROPERTY
            }
        }));
    return response.data;
}
