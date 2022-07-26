import {env} from "./env";

export const route = {
    specializations: `${env.baseApiUrl}/specializations`,
    subjects: `${env.baseApiUrl}/subjects`,
    exams: `${env.baseApiUrl}/exams`,
    tests: `${env.baseApiUrl}/tests`
};
