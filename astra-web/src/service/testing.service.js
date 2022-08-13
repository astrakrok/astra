import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getExamTestings = async examId => {
    const url = route.admin.testings.exams + "/" + examId;
    const response = await client.get(url);
    return response.data;
}

export const create = async data => {
    const url = route.admin.testings.all;
    const response = await client.post(url, data);
    return response.data;
}
