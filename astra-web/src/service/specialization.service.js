import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

export const getAll = async () => {
    const response = await client.get(route.specializations);
    return response.data;
}

export const getAllByStepId = async stepId => {
    const response = await client.get(route.stepSpecializations.stepId(stepId));
    return response.data;
}

export const create = async specialization => {
    const response = await client.post(route.admin.specializations.all, specialization);
    return response.data;
}

export const createByStepId = async (stepId, specialization) => {
    const response = await client.post(route.admin.stepSpecializations.stepId(stepId).all, specialization);
    return response.data;
}

export const filter = async filter => {
    const response = await client.post(route.admin.specializations.filter, filter);
    return response.data;
}
