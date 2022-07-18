const adminSpecializations = "/admin/specializations";
const adminSubjects = "/admin/subjects";

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
    
    adminLogin: "/admin/login",
    admin: {
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
            all: "/admin/subjects",
            id: id => {
                const path = adminSubjects + "/" + setParamOrPlaceholder(id, "id");
                return {
                    edit: path + "/edit"
                }
            }
        },
        tests: {
            all: "/admin/tests"
        }
    },
};

const setParamOrPlaceholder = (param, paramName) => {
    return param ? param : ":" + paramName;
};
