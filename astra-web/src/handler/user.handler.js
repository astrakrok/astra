import { userRole } from "../constant/user.role";

export const getUser = () => {
    return {
        role: userRole.guest,
        email: "example@email.com"
    };
}
