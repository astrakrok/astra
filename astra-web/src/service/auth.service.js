import axios from "axios";
import {httpStatus} from "../constant/http.status";
import {route} from "../constant/app.route"
import {clear, save} from "../handler/token.handler";

export const login = async loginData => {
    clear();
    const response = await axios.post(route.token, loginData, {
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
        save(tokens);
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

const formErrors = errors => {
    return {
        data: {
            errors
        }
    };
}
