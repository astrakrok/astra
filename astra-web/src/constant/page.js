const adminSpecializations = "/admin/specializations";
const adminSubjects = "/admin/subjects";
const adminTests = "/admin/tests";
const adminTestings = "/admin/testings";
const adminExams = "/admin/exams";

export const page = {
    home: "/",
    login: "/login",
    register: "/register",
    resetPassword: "/password/reset",
    newTesting: "/new-testing",
    testing: "/testing",
    profile: "/profile",
    settings: "/settings",
    configuration: "/configuration",
    logout: "/logout",
    googleCallback: "/google/callback",

    admin: {
        exams: {
            all: adminExams
        },
        specializations: {
            all: adminSpecializations,
            id: id => {
                const path = adminSpecializations + "/" + setParamOrPlaceholder(id, "id");
                return {
                    edit: path + "/edit"
                }
            }
        },
        subjects: {
            all: adminSubjects,
            id: id => {
                const path = adminSubjects + "/" + setParamOrPlaceholder(id, "id");
                return {
                    edit: path + "/edit"
                }
            }
        },
        tests: {
            all: adminTests,
            create: adminTests + "/create",
            id: id => {
                const path = adminTests + "/" + setParamOrPlaceholder(id, "id");
                return {
                    edit: path + "/edit"
                };
            }
        },
        testings: {
            all: adminTestings,
            id: id => {
                const path = adminTestings + "/" + setParamOrPlaceholder(id, "id");
                return {
                    edit: path + "/edit"
                };
            }
        }
    },
};

const setParamOrPlaceholder = (param, paramName) => {
    return param ? param : ":" + paramName;
};
