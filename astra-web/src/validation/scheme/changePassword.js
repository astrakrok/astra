import {regex} from "../../constant/regex";

export const changePasswordSchema = {
    oldPassword: {
        regex: regex.password
    },
    newPassword: {
        regex: regex.password
    }
}
