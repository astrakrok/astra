import {matchPath} from "react-router";
import {permission} from "../constant/permission";
import {userRole} from "../constant/user.role";
import {get} from "./token.handler";

export const checkPermission = (url, user) => {
    const hasTokens = get() != null;
    const roles = (hasTokens && user) ? user.roles : [userRole.guest];
    for (const role of roles) {
        if (checkPermissionForRole(url, role)) {
            return true;
        }
    }
    return false;
}

const checkPermissionForRole = (url, role) => {
    if (typeof permission[url] !== "undefined") {
        return permission[url].includes(role);
    }
    return tryBruteForce(url, role);
}

const tryBruteForce = (requestUrl, role) => {
    const urls = Object.keys(permission);
    for (const url of urls) {
        const match = matchPath({
            path: url,
            exact: true,
            strict: false
        }, requestUrl);
        if (match != null) {
            return permission[url].includes(role);
        }
    }
    return false;
}
