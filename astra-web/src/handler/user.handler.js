import {userRole} from "../constant/user.role";

export const getUser = () => {
    return {
        roles: [userRole.admin],
        name: "Andrii",
        surname: "Bosyk",
        email: "example@email.com",
        pictureUrl: "/images/avatar-1.png"
    };
}

export const isGuest = () => {
    return getUser().roles.includes(userRole.guest);
}

export const isAdmin = () => {
    return getUser().roles.includes(userRole.admin);
}
