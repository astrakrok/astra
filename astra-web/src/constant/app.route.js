import {env} from "./env";

const baseUrl = env.baseApiUrl;

const getTestingsRoutes = () => {
    const prefix = `${baseUrl}/api/v1/testings`;
    return {
        all: prefix,
        description: prefix + "/description",
        available: prefix + "/available",
        exams: {
            id: id => {
                return {
                    this: prefix + "/exams/" + id
                }
            }
        }
    }
}

const getAdminRoutes = () => {
    const prefix = `${baseUrl}/api/v1/admin`;
    return {
        exams: {
            all: prefix + "/exams",
            id: id => {
                const url = prefix + `/exams/${id}`;
                return {
                    specializations: url + "/specializations",
                    availableSpecializations: url + "/specializations/available"
                }
            }
        },
        testings: {
            all: prefix + "/testings",
            exams: {
                id: id => {
                    const url = prefix + `/testings/exams/${id}`;
                    return {
                        this: url
                    };
                }
            },
            id: id => {
                const url = prefix + `/testings/${id}`;
                return {
                    activation: url + "/activation",
                    info: url + "/info",
                    tests: {
                        selected: url + "/tests",
                        available: url + "/tests/available"
                    }
                }
            }
        },
        testingsTests: {
            all: prefix + "/testings-tests",
            id: id => {
                const url = prefix + `/testings-tests/${id}`;
                return {
                    this: url
                }
            }
        },
        stepSpecializations: {
            stepId: stepId => ({
                all: prefix + `/steps/${stepId}/specializations`
            })
        },
        properties: prefix + "/properties",
        subjects: {
            all: prefix + "/subjects",
            details: prefix + "/subjects/details",
        },
        specializations: prefix + "/specializations",
        steps: prefix + "/steps",
        tests: prefix + "/tests",
        import: {
            file: prefix + "/import/file",
            web: prefix + "/import/web",
            statsFilter: prefix + "/import/stats/filter"
        }
    };
}

export const route = {
    password: `${baseUrl}/api/v1/auth/password`,
    resetPassword: `${baseUrl}/api/v1/auth/password/reset`,
    signUp: `${baseUrl}/api/v1/auth/signup`,
    auth: `${baseUrl}/api/v1/auth`,
    oauth2: `${baseUrl}/api/v1/auth/oauth2`,
    stepSpecializations: {
        stepId: stepId => `${baseUrl}/api/v1/steps/${stepId}/specializations`
    },
    specializations: `${baseUrl}/api/v1/specializations/details`,
    specializationExams: {
        specializationId: specializationId => `${baseUrl}/api/v1/specializations/${specializationId}/exams`
    },
    steps: {
        all: `${baseUrl}/api/v1/steps`,
        id: id => ({
            specializations: `${baseUrl}/api/v1/steps/${id}/specializations`
        })
    },
    subjects: `${baseUrl}/api/v1/subjects`,
    exams: `${baseUrl}/api/v1/exams`,
    tests: `${baseUrl}/api/v1/tests`,
    examinations: `${baseUrl}/api/v1/examinations`,
    statistic: `${baseUrl}/api/v1/statistic`,
    users: `${baseUrl}/api/v1/users`,
    notifications: `${baseUrl}/api/v1/notifications`,
    testings: getTestingsRoutes(),
    admin: getAdminRoutes()
};
