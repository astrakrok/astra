import axios from "axios";
import {env} from "../../constant/env";

export const client = axios.create({
    baseURL: env.baseApiUrl,
    headers: {
        "Content-Type": "application/json"
    }
});
