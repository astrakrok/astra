import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const update = async user => {
    const response = await client.put(route.users, user);
    return response.data;
}
