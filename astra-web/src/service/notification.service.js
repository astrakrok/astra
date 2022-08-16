import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const sendNotification = async data => {
    const formData = new FormData();
    formData.append("title", data.title);
    formData.append("text", data.text);
    if (data.file) {
        formData.append("file", data.file);
    }
    const response = await client.post(route.notifications, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    }).catch(() => ({
        data: {
            error: "Нам не вдалося відправити Ваше повідомлення. Спробуйте пізніше"
        }
    }));
    return {
        ...response.data,
        message: "Ваше повідомлення було успішно відправлене. Дякуємо за допомогу!"
    };
}
