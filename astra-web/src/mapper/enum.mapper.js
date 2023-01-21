export const mapTestingStatusToTest = status => {
    switch (status) {
        case "DRAFT":
            return "Чернетка";
        case "ACTIVE":
            return "Активний";
        default:
            return "Unknown";
    }
}
