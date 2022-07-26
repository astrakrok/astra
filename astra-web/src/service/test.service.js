import {route} from "../constant/app.route";
import axios from "axios";

export const getDetailedTests = async () => {
    const response = await axios.get(route.tests);
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
