import {env} from "./env";

const baseUrl = env.baseApiUrl;

export const route = {
    signUp: `${baseUrl}/api/v1/auth/signup`,
    token: `${baseUrl}/api/v1/auth/token`,
    specializations: `${baseUrl}/api/v1/specializations`,
    subjects: `${baseUrl}/api/v1/subjects`,
    exams: `${baseUrl}/api/v1/exams`,
    tests: `${baseUrl}/api/v1/tests`,
    examinations: `${baseUrl}/api/v1/examinations`,
    users: `${baseUrl}/api/v1/users`
};
