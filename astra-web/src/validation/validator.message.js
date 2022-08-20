import V from "max-validator";

export const initMessages = () => {
    V.setMessages({
        required: "Поле обов'язкове",
        email: "Некорректний email",
        trimmedLength: "Поле \":field\" повинно містити від :min до :max символів",
        regex: ":message",
        regexOrNull: ":message",
        notNull: "Поле :field не повинно бути порожнім",
        elementsCount: "Поле \":field\" повинно містити щонайменше :min значення"
    });
}
