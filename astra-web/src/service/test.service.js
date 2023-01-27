import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getDetailedTests = async (filter, pageable) => {
    const response = await client.post(route.admin.tests + `/filter?pageSize=${pageable.pageSize}&pageNumber=${pageable.pageNumber}`, filter);
    return response.data;
}

export const getFullDetailedTest = async id => {
    const response = await client.get(`${route.admin.tests}/${id}`);
    return response.data;
}

export const getTraining = async options => {
    const url = route.tests + "/training";
    const response = await client.get(url, {
        params: options
    });
    return response.data;
}

export const getAdaptive = async options => {
    const url = route.tests + "/adaptive";
    const response = await client.get(url, {
        params: options
    });
    return response.data;
}

export const saveTest = async test => {
    try {
        const response = await client.post(route.admin.tests, test);
        return response.data;
    } catch (error) {
        return {
            data: error.response.data
        };
    }
}

export const saveTestDraft = async test => {
    try {
        const response = await client.post(route.admin.tests + "/draft", test);
        return response.data;
    } catch (error) {
        return {
            data: error.response.data
        };
    }
}

export const updateTest = async test => {
    const url = route.admin.tests + "/" + test.id;
    const response = await client.put(url, test)
        .catch(error => ({
            data: error.response.data
        }));
    return response.data;
}

export const updateTestDraft = async test => {
    const url = route.admin.tests + "/" + test.id + "/draft";
    const response = await client.put(url, test)
        .catch(error => ({
            data: error.response.data
        }));
    return response.data;
}

export const deleteTest = async testId => {
    const url = route.admin.tests + "/" + testId;
    const response = await client.delete(url)
        .catch(error => ({
            data: {
                errors: error.response.data
            }
        }));
    return response.data;
}
