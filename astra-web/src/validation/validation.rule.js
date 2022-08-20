import V from "max-validator";

const validateByRegex = (value, regex) => {
    return regex.value.test(value) ? true : {
        value: value,
        message: regex.message
    };
}

export const initRules = () => {
    V.extend(
        "notNull",
        (value, field) => {
            return (value != null) && (value !== "") ? true : {
                field: field
            };
        },
        "Value cannot be null"
    );

    V.extend(
        "regex",
        (value, regex) => {
            return validateByRegex(value, regex);
        },
        "Default Error Message: value is not valid for passed regex"
    );

    V.extend(
        "regexOrNull",
        (value, regex) => {
            if (value == null || value === "") {
                return true;
            }
            return validateByRegex(value, regex);
        },
        "Default Error Message: value is not valid for passed regex"
    )

    V.extend(
        "trimmedLength",
        (value, field, min = 1, max = 255) => {
            if (value == null || value === "") {
                return true;
            }
            const trimmed = value.trim();
            const length = trimmed.length;
            if (length >= min && length <= max) {
                return true;
            }
            return {
                value, field, min, max
            };
        }
    );

    V.extend(
        "elementsCount",
        (value, field, min = 1, max = 255) => {
            if (value && value.length >= min && value.length <= max) {
                return true;
            }
            return {
                value, field, min, max
            };
        }
    );
}
