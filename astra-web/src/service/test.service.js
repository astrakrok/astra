import {route} from "../constant/app.route";
import axios from "axios";

const trainingModeToUrl = {
    training: route.tests + "/training",
    examination: route.tests + "/examination"
};

export const getDetailedTests = async () => {
    const response = await axios.get(route.tests);
    return response.data;
}

export const getTestsForTesting = async options => {
    const url = trainingModeToUrl[options.mode];
    const response = await axios.get(url, {
        params: options
    });
    return response.data;
}

export const saveTest = async test => {
    try {
        const response = await axios.post(route.tests, test);
        return response.data;
    } catch (error) {
        return {
            error: "Something went wrong..."
        };
    }
}
