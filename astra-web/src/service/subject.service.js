import {subjects} from "../data/mock/subjects";
import {specializations} from "../data/mock/specializations";
import {subjectsSpecializations} from "../data/mock/subjects.specializations"
import {getRandomSubArray} from "../handler/array.handler";

export const getSubjectsWithSpecializations = async () => {
    return subjects.map(item => ({
        specializations: getSpecializations(item),
        ...item
    }));
}

export const getSubjectsBySpecializationId = async (specializationId) => {
    return getRandomSubArray(subjects);
}

export const create = async (subject) => {
    return {
        id: 5
    }
}

const getSpecializations = subject => {
    const ids = subjectsSpecializations.filter(item => item.subjectId === subject.id).map(item => item.specializationId);
    return specializations.filter(item => ids.includes(item.id));
}
