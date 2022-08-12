import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

const mapDateToSeconds = timestamp => {
    const milliseconds = new Date(timestamp + "Z").getTime();
    return Math.round(milliseconds / 1000);
}

export const start = async data => {
    const response = await client.post(route.examinations, data);
    const result = response.data;
    return {
        ...result,
        finishedAt: mapDateToSeconds(result.finishedAt)
    };
}

export const getResult = async id => {
    const url = route.examinations + `/${id}/result`;
    const response = await client.put(url);
    return response.data;
}

export const updateAnswer = async (id, data) => {
    const url = route.examinations + "/" + id;
    const response = await client.put(url, data)
        .catch(error => ({
            data: {
                error
            }
        }));
    
    return response.data;
}
