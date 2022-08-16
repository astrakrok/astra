import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getExamTestings = async examId => {
    const url = route.testings.exams.id(examId).this;
    const response = await client.get(url);
    return response.data;
}

export const create = async data => {
    const url = route.admin.testings.all;
    const response = await client.post(url, data)
        .catch(error => ({
            data: {
                error
            }
        }));
    return response.data;
}

export const getTestingInfo = async id => {
    const url = route.admin.testings.id(id).info;
    const response = await client.get(url);
    return response.data;
}

export const getTests = async id => {
    const url = route.admin.testings.id(id).tests.selected;
    const response = await client.get(url);
    return response.data;
}

export const getAvailableTests = async id => {
    const url = route.admin.testings.id(id).tests.available;
    const response = await client.get(url);
    return response.data;
}

export const getDescriptions = async () => {
    const url = route.testings.description;
    const response = await client.get(url);
    return response.data;
}
