export const mapToGroups = testings => {
    const result = {};
    for (const testing of testings) {
        const exam = testing.exam;
        const specialization = testing.specialization;
        const step = specialization.step;
        if (!result[exam.id]) {
            result[exam.id] = {
                ...exam,
                steps: {}
            };
        }
        if (!result[exam.id].steps[step.id]) {
            result[exam.id].steps[step.id] = {
                ...step,
                specializations: []
            }
        }
        result[exam.id].steps[step.id].specializations.push({
            ...specialization,
            status: testing.status,
            testingId: testing.id,
            step: null
        });
    }
    return Object.values(result).map(item => ({
        ...item,
        steps: Object.values(item.steps)
    }));
}
