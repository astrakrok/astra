const adminSpecializations = "/admin/specializations";

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
                    edit: path + "/edit",
                    subjects: {
                        all: path + "/subjects"
                    }
                }
            }
        },
        subjects: {
            all: "/admin/subjects"
        },
        tests: {
            all: "/admin/tests"
        }
    },
};

const setParamOrPlaceholder = (param, paramName) => {
    return param == null ? ":" + paramName : param;
};
