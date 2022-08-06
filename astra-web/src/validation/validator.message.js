import V from "max-validator";

export const initMessages = () => {
    V.setMessages({
        required: "Поле обов'язкове",
        email: "Некорректний email",
        trimmedLength: "Поле \":field\" повинно містити від :min до :max символів",
        regex: "Ваш пароль повинен містити щонайменше 8 символів, одну літеру верхнього регістру, одну літеру нижнього регістру та щонайменше одну цифру",
        elementsCount: "Поле \":field\" повинно містити щонайменше :min значення"
    });
}
