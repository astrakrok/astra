import {route} from "../constant/app.route"
import {client} from "../shared/js/axios"

export const exportToFile = async exportData => {
    const response = await client.post(route.admin.export, exportData, {
        responseType: "blob"
    });
    return response.data;
}
