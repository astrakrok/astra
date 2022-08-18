import axios from "axios";
import {client} from "../shared/js/axios";
import {httpStatus} from "../constant/http.status";
import {route} from "../constant/app.route"
import {clear, save} from "../handler/token.handler";
import {errorMessage} from "../error/message";

export const login = async loginData => {
    clear();
    const response = await axios.post(route.auth, loginData, {
        headers: {
            Authorization: null
        }
    }).catch(error => ({
        data: {
            error
        }
    }));
    if (response.data.accessToken) {
        return await getUserData(response.data);
    }
    return response.data;
}

export const signUp = async signUpData => {
    clear();
    const response = await axios.post(route.signUp, signUpData, {
        headers: {
            Authorization: null
        }
    }).catch(error => {
        if (error.response.status === httpStatus.CONFLICT) {
            return formErrors({
                email: ["Користувач з таким email уже існує"]
            });
        }
        return formErrors({
            global: ["На жаль, сталась несподівано помилка. Спробуйте пізніше"]
        });
    });
    return response.data;
}

export const resetPassword = async email => {
    clear();
    const response = await axios.post(route.resetPassword, {email}, {
        headers: {
            Authorization: null
        }
    }).catch(() => ({
        data: {
            error: errorMessage.UNABLE_TO_RESET_PASSWORD
        }
    }));
    return response.data;
}

export const getLoginUrl = async providerName => {
    clear();
    const url = route.oauth2 + "/" + providerName;
    const response = await axios.get(url, {
        headers: {
            Authorization: null
        }
    });
    return response.data;
}

export const oauth2Login = async (providerName, data) => {
    clear();
    const url = route.oauth2 + "/" + providerName;
    const response = await axios.post(url, data, {
        header: {
            Authorization: null
        }
    });
    if (response.data.accessToken) {
        return await getUserData(response.data);
    }
    return response.data;
}

export const changeUserPassword = async changePasswordData => {
    const response = await client.put(route.password, changePasswordData)
        .catch(() => ({
            data: {
                error: errorMessage.UNABLE_TO_CHANGE_PASSWORD
            }
        }));
    return response.data;
}

export const logout = setUserData => {
    setUserData(null);
    clear();
}

const getUserInfo = async accessToken => {
    return await axios.get(`${route.users}/current`, {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    }).catch(() => ({
        data: {
            error: errorMessage.UNABLE_TO_GET_USER_INFO
        }
    }));
}

const formErrors = errors => {
    return {
        data: {
            errors
        }
    };
}

const getUserData = async tokens => {
    const userInfoResponse = await getUserInfo(tokens.accessToken);
    if (userInfoResponse.data.error) {
        return userInfoResponse.data;
    }
    save(tokens);
    return {
        user: userInfoResponse.data
    };
}
