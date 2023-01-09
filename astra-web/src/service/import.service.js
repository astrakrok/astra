import {client} from "../shared/js/axios";
import {route} from "../constant/app.route";

export const importFromFile = async (title, file) => {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("file", file);
    const response = await client.post(route.admin.import.file, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
    return response.data;
}

export const importFromWeb = async (title, url) => {
    const response = await client.post(route.admin.import.web, {title, url})
        .catch(error => ({
            data: {
                errors: error.response.data
            }
        }));
    return response.data;
}

export const getStats = async (filter, pageable) => {
    const response = await client.post(route.admin.import.statsFilter + `?pageNumber=${pageable.pageNumber}&pageSize=${pageable.pageSize}`, filter);
    return response.data;
}
