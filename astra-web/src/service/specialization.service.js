import axios from "axios";
import { route } from "../constant/app.route";

export const getAll = async () => {
    const response = await axios.get(route.specializations);
    return response.data;
}

export const create = async specialization => {
    const response = await axios.post(route.specializations, specialization);
    return response.data;
}
