import axios from "axios";
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
        const tokens = response.data;
        const userInfoResponse = await getUserInfo(response.data.accessToken);
        if (userInfoResponse.data.error) {
            return userInfoResponse.data;
        }
        save(tokens);
        return {
            user: userInfoResponse.data
        };
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
