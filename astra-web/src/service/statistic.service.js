import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getExaminationsStatistic = async () => {
    const url = route.statistic + "/statistic";
    const response = await client.get(url);
    return response.data;
}

export const getStepsStatistic = async () => {
    const url = route.statistic + "/steps";
    const response = await client.get(url);
    return response.data;
}
