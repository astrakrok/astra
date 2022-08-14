import {route} from "../constant/app.route";
import {errorMessage} from "../error/message";
import {client} from "../shared/js/axios";

export const update = async user => {
    const response = await client.put(route.users, user)
        .catch(() => ({
            data: {
                error: errorMessage.UNABLE_TO_UPDATE_USER_INFO
            }
        }));
    return response.data;
}
