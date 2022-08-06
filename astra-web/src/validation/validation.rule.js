import V from "max-validator";

export const initRules = () => {
    V.extend(
        "regex",
        (value, regexExpression) => {
            return regexExpression.test(value) ? true : {
                value: value
            };
        },
        "Default Error Message: :name must be a valid password"
    );

    V.extend(
        "trimmedLength",
        (value, field, min = 1, max = 255) => {
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
