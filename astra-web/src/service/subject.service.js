import {subjects} from "../data/mock/subjects";
import {getRandomSubArray} from "../handler/array.handler";
import {route} from "../constant/app.route";
import axios from "axios";

export const getSubjectsDetails = async () => {
    const response = await axios.get(route.subjects);
    return response.data;
}

export const getSubjectsBySpecializationId = async (specializationId) => {
    return getRandomSubArray(subjects);
}

export const create = async (subject) => {
    const response = await axios.post(route.subjects, subject);
    return response.data;
}
