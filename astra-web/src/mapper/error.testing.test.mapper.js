export const mapToErrors = errors => {
    const result = {};
    for (const error of errors) {
        if (error.type === "INVALID_STATUS") {
            if (!result.status) {
                result.status = [];
            }
            result.status.push(mapToInvalidStatusMessage(error.details.item, error.details.shouldBe));
        } else if (error.type === "CONFLICT") {
            if (!result.conflict) {
                result.conflict = [];
            }
            result.conflict.push("Даний тест вже знаходиться у вибраному іспиті");
        } else {
            if (!result.unknown) {
                result.unknown = ["Невідома помилка"];
            }
        }
    }
    return result;
}

const mapToInvalidStatusMessage = (item, neededStatus) => {
    const entityName = item === "testing" ? "Іспит" : "Тест";
    const status = neededStatus === "DRAFT" ? "'Чернетка'" : "'Активний'";
    return `${entityName} не перебуває в статусі ${status}`;
}
