import {env} from "./env";

const baseUrl = env.baseApiUrl;

export const route = {
    signUp: `${baseUrl}/api/v1/auth/signup`,
    auth: `${baseUrl}/api/v1/auth`,
    specializations: `${baseUrl}/api/v1/specializations`,
    subjects: `${baseUrl}/api/v1/subjects`,
    exams: `${baseUrl}/api/v1/exams`,
    tests: `${baseUrl}/api/v1/tests`,
    examinations: `${baseUrl}/api/v1/examinations`,
    users: `${baseUrl}/api/v1/users`
};
