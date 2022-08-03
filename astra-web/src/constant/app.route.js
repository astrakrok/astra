import {env} from "./env";

export const route = {
    signUp: `${env.baseApiUrl}/api/v1/auth/signup`,
    token: `${env.baseApiUrl}/api/v1/auth/token`,
    specializations: `${env.baseApiUrl}/api/v1/specializations`,
    subjects: `${env.baseApiUrl}/api/v1/subjects`,
    exams: `${env.baseApiUrl}/api/v1/exams`,
    tests: `${env.baseApiUrl}/api/v1/tests`,
    examinations: `${env.baseApiUrl}/api/v1/examinations`
};
