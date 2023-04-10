import {userRole} from "../constant/user.role";
import {getJson} from "./storage.handler";
import {isValidTokenData} from "./token.handler";
import {defaultUser} from "../data/default/user";
import {localStorageKey} from "../constant/local.storage.key";

export const getUser = () => {
    if (isValidTokenData(getJson(localStorageKey.tokenData))) {
        return getJson(localStorageKey.userData);
    } else {
        return defaultUser;
    }
}

export const isGuest = () => {
    return getJson(localStorageKey.userData) == null;
}

export const isAdmin = user => {
    return user && (
        user.roles.includes(userRole.admin) || user.roles.includes(userRole.superAdmin)
    );
}

export const isUser = user => {
    return user && user.roles.includes(userRole.user);
}

export const isSuperAdmin = user => {
    return user && user.roles.includes(userRole.superAdmin);
}
