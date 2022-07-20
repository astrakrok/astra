import {matchPath} from "react-router";
import {page} from "../constant/page"
import {userRole} from "../constant/user.role";
import {getUser} from "./user.handler";

const permissions = {
    [page.home]: [
        userRole.guest,
        userRole.user,
        userRole.admin
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
    ],
    [page.exams]: [
        userRole.user
    ],
    [page.adminLogin]: [
        userRole.guest
    ],
    [page.admin]: [
        userRole.admin
    ],
    [page.admin.specializations.all]: [
        userRole.admin
    ],
    [page.admin.specializations.create]: [
        userRole.admin
    ],
    [page.admin.subjects.all]: [
        userRole.admin
    ],
    [page.admin.tests.all]: [
        userRole.admin
    ],
    [page.admin.tests.create]: [
        userRole.admin
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
    return tryBruteForce(url, role);
}

const tryBruteForce = (requestUrl, role) => {
    const urls = Object.keys(permissions);
    for (const url of urls) {
        const match = matchPath({
            path: url,
            exact: true,
            strict: false
        }, requestUrl);
        if (match != null) {
            return permissions[url].includes(role);
        }
    }
    return false;
}
