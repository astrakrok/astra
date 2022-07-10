import axios from "axios";
import { route } from "../constant/app.route";

export const getAll = async () => {
    try {
        return await axios.get(route.specializations);
    } catch (error) {
        return [];
    }
}
