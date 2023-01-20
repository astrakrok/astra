import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getExamTestings = async examId => {
    const url = route.admin.testings.exams.id(examId).this;
    const response = await client.get(url);
    return response.data;
}

export const changeStatus = async (testingId, status) => {
    const url = route.admin.testings.id(testingId).status;
    const response = await client.put(url, {status})
        .catch(error => ({
            data: {
                errors: error.response.data
            }
        }));
    return response.data;
}

export const get = async (examId, specializationId) => {
    const url = route.testings.all + `?examId=${examId}&specializationId=${specializationId}`;
    const response = await client.get(url);
    return response.data;
}

export const getAvailableTestings = async () => {
    const response = await client.get(route.testings.available);
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

export const getTests = async (id, filter, pageable) => {
    const url = route.admin.testings.id(id).tests.selected + `?pageSize=${pageable.pageSize}&pageNumber=${pageable.pageNumber}`;
    const response = await client.post(url, filter);
    return response.data;
}

export const getAvailableTests = async (id, filter, pageable) => {
    const url = route.admin.testings.id(id).tests.available + `?pageNumber=${pageable.pageNumber}&pageSize=${pageable.pageSize}`;
    const response = await client.post(url, filter);
    return response.data;
}

export const getDescriptions = async () => {
    const url = route.testings.description;
    const response = await client.get(url);
    return response.data;
}
