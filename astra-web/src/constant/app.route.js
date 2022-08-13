import {env} from "./env";

const baseUrl = env.baseApiUrl;

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
            exams: prefix + "/testings/exams"
        },
        subjects: prefix + "/subjects",
        specializations: prefix + "/specializations",
        tests: prefix + "/tests"
    };
}

export const route = {
    signUp: `${baseUrl}/api/v1/auth/signup`,
    auth: `${baseUrl}/api/v1/auth`,
    specializations: `${baseUrl}/api/v1/specializations`,
    subjects: `${baseUrl}/api/v1/subjects`,
    exams: `${baseUrl}/api/v1/exams`,
    tests: `${baseUrl}/api/v1/tests`,
    examinations: `${baseUrl}/api/v1/examinations`,
    users: `${baseUrl}/api/v1/users`,
    admin: getAdminRoutes()
};
