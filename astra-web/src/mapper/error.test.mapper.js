export const mapToTestErrors = errors => {
    const messages = {};
    for (const error of errors) {
        if (error.type === "REDUNDANT_TESTINGS") {
            messages.redundantTestings = error.details.items;
        }
    }
    return messages;
}
