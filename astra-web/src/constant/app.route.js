import {env} from "./env";

const baseUrl = env.baseApiUrl;

const getTestingsRoutes = () => {
    const prefix = `${baseUrl}/api/v1/testings`;
    return {
        description: prefix + "/description",
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
            id: id => {
                const url = prefix + `/testings/${id}`;
                return {
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
        properties: prefix + "/properties",
        subjects: prefix + "/subjects",
        specializations: prefix + "/specializations",
        tests: prefix + "/tests"
    };
}

export const route = {
    password: `${baseUrl}/api/v1/auth/password`,
    resetPassword: `${baseUrl}/api/v1/auth/password/reset`,
    signUp: `${baseUrl}/api/v1/auth/signup`,
    auth: `${baseUrl}/api/v1/auth`,
    googleOauth2: `${baseUrl}/api/v1/auth/oauth2/google`,
    specializations: `${baseUrl}/api/v1/specializations`,
    subjects: `${baseUrl}/api/v1/subjects`,
    exams: `${baseUrl}/api/v1/exams`,
    tests: `${baseUrl}/api/v1/tests`,
    examinations: `${baseUrl}/api/v1/examinations`,
    users: `${baseUrl}/api/v1/users`,
    notifications: `${baseUrl}/api/v1/notifications`,
    testings: getTestingsRoutes(),
    admin: getAdminRoutes()
};
