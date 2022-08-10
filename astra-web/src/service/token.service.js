import axios from "axios";
import {route} from "../constant/app.route";
import {get, save} from "../handler/token.handler"

export const refreshTokens = async () => {
    const tokenData = get();
    const response = await axios.put(route.auth, {
        refreshToken: tokenData.refreshToken
    });
    const tokens = response.data;
    save(tokens);
    return tokens;
}
