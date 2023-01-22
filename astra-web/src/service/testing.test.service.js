import {route} from "../constant/app.route"
import {client} from "../shared/js/axios";

export const deleteById = async id => {
    const url = route.admin.testingsTests.id(id).this;
    const response = await client.delete(url);
    return response.data;
}

export const create = async data => {
    const url = route.admin.testingsTests.all;
    const response = await client.post(url, data)
        .catch(error => ({
            data: {
                error
            }
        }));
    return response.data;
}

export const getTestings = async testId => {
    const url = route.admin.testingsTests.testId(testId).testings;
    const response = await client.get(url);
    return response.data;
}

export const deleteByTestingIdAndTestId = async (testingId, testId) => {
    const url = route.admin.testingsTests.all;
    const response = await client.delete(url, {data: {testingId, testId}})
        .catch(error => ({
            data: error.response.data
        }));
    return response.data;
}

export const saveByDetails = async details => {
    const url = route.admin.testingsTests.details;
    const response = await client.post(url, details)
        .catch(error => ({
            data: error.response.data
        }));
    return response.data;
}
