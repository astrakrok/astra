export const compareFullSubjects = (first, second) => {
    if (first.specialization.step.title.toLowerCase() === second.specialization.step.title.toLowerCase()) {
        if (first.specialization.title.toLowerCase() === second.specialization.title.toLowerCase()) {
            return first.title.toLowerCase() > second.title.toLowerCase() ? 1 : -1;
        }
        return first.specialization.title.toLowerCase() > second.specialization.title.toLowerCase() ? 1 : -1;
    }
    return first.specialization.step.title.toLowerCase() > second.specialization.step.title.toLowerCase() ? 1 : -1;
}

export const compareTitles = (first, second) => {
    return first.title.toLowerCase() > second.title.toLowerCase() ? -1 : 1;
}
