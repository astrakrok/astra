import {regex} from "../../constant/regex";

export const loginSchema = {
    email: "email",
    password: {
        regex: regex.password
    }
};
