import { page } from "../constant/page"
import { userRole } from "../constant/user.role";
import { getUser } from "./user.handler";

const permissions = {
    [page.home]: [
        userRole.guest,
        userRole.user
    ],
    [page.login]: [
        userRole.guest
    ],
    [page.register]: [
        userRole.guest
    ],
    [page.team]: [
        userRole.guest,
        userRole.user
    ],
    [page.joinTeam]: [
        userRole.guest,
        userRole.user
    ]
};

export const checkPermission = url => {
    const user = getUser();
    const role = user == null ? "guest" : user.role;
    return checkPermissionForRole(url, role);
}

const checkPermissionForRole = (url, role) => {
    if (typeof permissions[url] !== "undefined") {
        return permissions[url].includes(role);
    }
    return false;
}
