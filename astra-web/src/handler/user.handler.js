import { userRole } from "../constant/user.role";

export const getUser = () => {
    return {
        role: userRole.admin,
        email: "example@email.com",
        pictureUrl: "/images/avatar-1.png"
    };
}

export const isGuest = () => {
    return getUser().role === userRole.guest;
}

export const isAdmin = () => {
    return getUser().role === userRole.admin;
}
