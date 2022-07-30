import {userRole} from "../constant/user.role";
import {getJson} from "./storage.handler";
import {isValidTokenData} from "./token.handler";
import {defaultUser} from "../data/default/user";
import {localStorageKey} from "../constant/local.storage.key";

export const getUser = () => {
    if (isValidTokenData(getJson(localStorageKey.tokenData))) {
        return getJson(localStorageKey.user);
    } else {
        return defaultUser;
    }
}

export const isGuest = () => {
    return getJson(localStorageKey.localData) == null;
}

export const isAdmin = user => {
    return user && user.roles.includes(userRole.admin);
}
