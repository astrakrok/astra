import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getDetailedTests = async () => {
    const response = await client.get(route.admin.tests);
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

export const updateTest = async test => {
    const url = route.admin.tests + "/" + test.id;
    const response = await client.put(url, test)
        .catch(() => ({
            data: {
                error: "Something went wrong..."
            }
        }));
    return response.data;
}
