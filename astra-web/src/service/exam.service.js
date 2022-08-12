import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getAll = async () => {
    const response = await client.get(route.exams);
    return response.data;
}

export const create = async exam => {
    const response = await client.post(route.admin.exams, exam)
        .catch(() => ({
            data: {
                error: "Сталась несподівано помилка. Перевірте правильність введених даних або спробуйте пізніше"
            }
        }));
    return response.data;
}

export const deleteExam = async examId => {
    const response = await client.delete(`${route.admin.exams}/${examId}`);
    return response.data;
}

export const update = async exam => {
    const response = await client.put(`${route.admin.exams}/${exam.id}`, exam);
    return response.data;
}
