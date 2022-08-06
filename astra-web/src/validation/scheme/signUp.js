import {regex} from "../../constant/regex";

export const signUpSchema = {
    name: "trimmedLength:Ім'я,2",
    surname: "trimmedLength:Прізвище,2",
    email: "email",
    password: {
        regex: regex.password
    }
}
