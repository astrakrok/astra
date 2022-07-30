import {localStorageKey} from "../constant/local.storage.key";
import {getJson, removeItem, saveJson} from "./storage.handler";

export const isValidTokenData = tokenData => {
    return tokenData != null;
}

export const get = () => {
    return getJson(localStorageKey.tokenData);
}

export const clear = () => {
    removeItem(localStorageKey.tokenData);
}

export const save = tokenData => {
    saveJson(localStorageKey.tokenData, tokenData);
}
