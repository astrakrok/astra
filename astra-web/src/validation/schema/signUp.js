import {regex} from "../../constant/regex";

export const signUpSchema = {
    name: {
        regex: regex.text
    },
    surname: {
        regex: regex.text
    },
    course: {
        regexOrNull: regex.oneToSixDigits
    },
    school: {
        regexOrNull: regex.textWithSpaces(5),
        trimmedLength: ["Навчальний заклад", "5"]
    },
    email: "email",
    password: {
        regex: regex.password
    }
}
