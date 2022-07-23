import axios from "axios"
import {route} from "../constant/app.route"

export const getAll = async () => {
    const response = await axios.get(route.exams);
    return response.data;
}

export const create = async exam => {
    const response = await axios.post(route.exams, exam);
    return response.data;
}
