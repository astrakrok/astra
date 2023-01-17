import {client} from "../shared/js/axios";
import {route} from "../constant/app.route";

export const importFromFile = async (title, file) => {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("file", file);
    const response = await client.post(route.admin.transfer.fileImport, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
    return response.data;
}

export const importFromWeb = async (title, url) => {
    const response = await client.post(route.admin.transfer.webImport, {title, url})
        .catch(error => ({
            data: {
                errors: error.response.data
            }
        }));
    return response.data;
}

export const getStats = async (filter, pageable) => {
    const response = await client.post(route.admin.transfer.importStatsFilter + `?pageNumber=${pageable.pageNumber}&pageSize=${pageable.pageSize}`, filter);
    return response.data;
}

export const exportToFile = async exportData => {
    const response = await client.post(route.admin.transfer.export, exportData, {
        responseType: "blob"
    });
    return response.data;
}
