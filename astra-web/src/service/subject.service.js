import {subjects} from "../mock/data/subjects";
import {specializations} from "../mock/data/specializations";
import {subjectsSpecializations} from "../mock/data/subjects.specializations"

export const getSubjectsWithSpecializations = async () => {
    return subjects.map(item => ({
        specializations: getSpecializations(item),
        ...item
    }));
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
