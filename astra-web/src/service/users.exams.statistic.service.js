import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getAllStatistic = async () => {
    const url = route.examinations + "/statistic";
    const response = await client.get(url);
    return response.data;
}
