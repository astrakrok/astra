import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

const trainingModeToUrl = {
    training: route.tests + "/training",
    examination: route.tests + "/examination"
};

export const getDetailedTests = async () => {
    const response = await client.get(route.admin.tests);
    return response.data;
}

export const getFullDetailedTest = async id => {
    const response = await client.get(`${route.admin.tests}/${id}`);
    return response.data;
}

export const getTesting = async options => {
    const url = trainingModeToUrl[options.mode];
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
            error: "Something went wrong..."
        };
    }
}
