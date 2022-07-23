const adminSpecializations = "/admin/specializations";
const adminSubjects = "/admin/subjects";
const adminTests = "/admin/tests";
const adminExams = "/admin/exams";

export const page = {
    home: "/",
    login: "/login",
    register: "/register",
    team: "/team",
    joinTeam: "/join-team",
    exams: "/exams",
    feedback: "/feedback",
    profile: "/profile",
    settings: "/settings",
    logout: "/logout",
    
    admin: {
        login: "/admin/login",
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
        }
    },
};

const setParamOrPlaceholder = (param, paramName) => {
    return param ? param : ":" + paramName;
};
